package net.ys.schedule;

import net.sf.json.JSONObject;
import net.ys.bean.BusAtt;
import net.ys.bean.SegFile;
import net.ys.component.SysConfig;
import net.ys.dao.FileDao;
import net.ys.utils.LogUtil;
import net.ys.utils.TimeUtil;
import net.ys.utils.Tools;
import net.ys.utils.req.HttpClient;
import net.ys.utils.req.HttpResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
@Service
public class FileService {

    private static final int PER_LEN = 1024 * 1024 * 20;//20M 每个文件大小

    @Resource
    private FileDao fileDao;

    public void execute() {
        try {
            System.out.println("split file----->" + System.currentTimeMillis());

            File file = new File(SysConfig.attachmentPath);

            File[] files = file.listFiles();

            String[] names;//tableName@fieldName@dataPriField@dataId@fileName.xxx
            for (File f : files) {
                names = f.getName().split("@");
                if (names.length != 5) {
                    continue;
                }
                String path = TimeUtil.toYm(System.currentTimeMillis());//日期路径
                List<SegFile> segFiles = splitFile(f, path);
                saveFiles(segFiles, SysConfig.platformCode, names, path);
            }
        } catch (IOException e) {
            LogUtil.error(e);
        }
    }

    private void saveFiles(List<SegFile> segFiles, String platformCode, String[] names, String path) {
        List<String> files = new ArrayList<String>();
        SegFile segFile = segFiles.get(0);
        files.add("INSERT INTO BUS_ATTACHMENT (FILE_NAME, ATT_NAME, PLATFORM_CODE, TABLE_NAME, FIELD_NAME, DATA_PRI_FIELD, DATA_ID, FILE_PATH) VALUES ('" + segFile.getFileName() + "','" + segFile.getFileNameTmp() + "','" + platformCode + "','" + names[0] + "','" + names[1] + "','" + names[2] + "','" + names[3] + "','" + path + "')");
        for (SegFile file : segFiles) {
            files.add("INSERT INTO BUS_ATTACHMENT_TMP ( FILE_INDEX, START_POINT, ATT_NAME, FILE_NAME, PATH, FILE_LEN ) VALUES (" + file.getIndex() + "," + file.getStartPoint() + ",'" + file.getFileNameTmp() + "','" + file.getFileName() + "','" + file.getPath() + "'," + file.getFileLen() + ")");
        }

        fileDao.saveFiles(files);
    }

    public List<SegFile> splitFile(File file, String path) throws IOException {
        long fileLen = file.length();
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String fileNameTmp = Tools.genFileName() + suffix;
        long fileNum = fileLen / PER_LEN + (fileLen % PER_LEN == 0 ? 0 : 1);//切分文件个数
        List<SegFile> segFiles = new ArrayList<SegFile>();
        int page;
        SegFile segFile;

        String[] names = fileName.split("@");
        String fileNameDb = names[names.length - 1];
        long startPoint;
        BigDecimal per = new BigDecimal(PER_LEN + "");
        for (page = 0; page < fileNum - 1; page++) {
            startPoint = per.multiply(new BigDecimal(page)).longValue();
            segFile = new SegFile(page, startPoint, fileNameDb, fileNameTmp, fileNum, path, fileLen);
            segFiles.add(segFile);
        }
        startPoint = per.multiply(new BigDecimal(page)).longValue();
        segFile = new SegFile(page, startPoint, fileNameDb, fileNameTmp, fileNum, path, fileLen);
        segFiles.add(segFile);

        FileOutputStream fos;

        RandomAccessFile fis = new RandomAccessFile(file, "rw");

        FileChannel fileChannel = fis.getChannel();
        FileLock flin;
        try {
            flin = fileChannel.tryLock();
        } catch (Exception e) {
            LogUtil.error(e);
            return new ArrayList<SegFile>();
        }

        long readSize;
        for (SegFile sFile : segFiles) {
            readSize = 0;
            fos = new FileOutputStream(SysConfig.attachmentTmp + sFile.getFileNameTmp() + ".tmp_" + sFile.getIndex());
            fis.seek(sFile.getStartPoint());
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) > 0) {
                fos.write(bytes, 0, len);
                readSize += len;
                if (readSize == PER_LEN) {
                    fos.flush();
                    break;
                }
            }
            fos.close();
        }

        flin.release();
        fileChannel.close();
        fis.close();

        file.renameTo(new File(SysConfig.attachmentRead + fileName));

        return segFiles;
    }

    public void upFile() throws IOException {

        System.out.println("upFile----->" + System.currentTimeMillis());

        List<BusAtt> busAttList = fileDao.queryBusAtt();//获取待上传的文件信息
        for (BusAtt busAtt : busAttList) {
            List<SegFile> segFiles = fileDao.queryBusAttTmp(busAtt.getAttName());
            List<String> upSql = uploadFileStep(segFiles);
            if (upSql.size() == segFiles.size()) {
                String result = upToCenter(busAtt);
                if (result != null) {
                    upSql.add("UPDATE BUS_ATTACHMENT SET UP_STATUS = 1 WHERE ID = " + busAtt.getId());
                    upSql.add("UPDATE TAB__" + busAtt.getTableName().toUpperCase() + " SET COL__" + busAtt.getFieldName().toUpperCase() + " = '" + result + "' WHERE COL__" + busAtt.getDataPriField().toUpperCase() + " = '" + busAtt.getDataId() + "'");
                }
            }
            fileDao.chgUpStatus(upSql);
        }
    }

    /**
     * 上传到中心
     */
    public String upToCenter(BusAtt busAtt) throws IOException {
        String url = SysConfig.decenterUrl + "/api/upload.do";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("platform_code", busAtt.getPlatformCode());
        params.put("table_name", busAtt.getTableName());
        params.put("field_name", busAtt.getFieldName());
        params.put("data_pri_field", busAtt.getDataPriField());
        params.put("data_id", busAtt.getDataId());
        params.put("file_name", URLEncoder.encode(busAtt.getFileName(), "UTF-8"));
        params.put("att_name", busAtt.getAttName());
        params.put("path", String.valueOf(busAtt.getPath()));
        HttpResponse response = HttpClient.doPost(url, params);
        String resp = response.getValue();
        if (resp.contains("1000")) {
            JSONObject object = JSONObject.fromObject(resp);
            return object.optString("data");
        }
        return null;
    }

    private List<String> uploadFileStep(List<SegFile> segFiles) throws IOException {
        FileInputStream fis;
        List<String> upSql = new ArrayList<String>();
        File file;
        String url = SysConfig.decenterUrl + "/api/upload/file.do";
        for (SegFile segFile : segFiles) {
            file = new File(SysConfig.attachmentTmp + segFile.getFileNameTmp() + ".tmp_" + segFile.getIndex());
            fis = new FileInputStream(file);
            Map<String, String> textMaps = new HashMap<String, String>();
            textMaps.put("date_path", segFile.getPath());
            textMaps.put("file_alias", segFile.getFileNameTmp());
            textMaps.put("file_len", String.valueOf(segFile.getFileLen()));
            textMaps.put("start_point", String.valueOf(segFile.getStartPoint()));

            Map<String, File> fileMaps = new HashMap<String, File>();
            fileMaps.put("file", file);
            HttpResponse response = HttpClient.doPostFormData(url, textMaps, fileMaps);
            fis.close();
            boolean flag = response.getValue().contains("1000");
            if (flag) {
                upSql.add("UPDATE BUS_ATTACHMENT_TMP SET UP_STATUS = 1 WHERE ID = " + segFile.getId());
                file.delete();
            }
        }
        return upSql;
    }
}
