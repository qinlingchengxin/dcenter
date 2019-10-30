package net.ys.schedule;

import com.alibaba.fastjson.JSONArray;
import net.ys.bean.BusCustomEntity;
import net.ys.component.SysConfig;
import net.ys.service.CommonService;
import net.ys.service.EntityService;
import net.ys.utils.LogUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
@Service
public class FrontDataService {

    @Resource
    private EntityService entityService;

    @Resource
    private CommonService commonService;

    @Resource
    private BuService buService;

    /**
     * 已共享文件的方式上传数据
     */
    public void upFileData() {
        try {

            if (!"1".equals(SysConfig.transType)) {//非共享目录
                return;
            }

            System.out.println("upFileData----->" + System.currentTimeMillis());

            File file = new File(SysConfig.dataPath);

            File[] files = file.listFiles();
            for (File f : files) {
                readFile(f, SysConfig.platformCode);
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public void readFile(File file, String platformCode) {
        String fileName = file.getName();
        if (!fileName.endsWith(".json")) {
            file.renameTo(new File(SysConfig.dataPathFailed + "file_invalid@" + fileName));
            return;
        }

        String tableName = fileName.substring(0, fileName.lastIndexOf(".")).split("@")[0];//tableName@timestamp.json
        BusCustomEntity entity = entityService.queryEntityFront(tableName);
        if (entity == null) {
            file.renameTo(new File(SysConfig.dataPathFailed + "not_exist_or_release@" + fileName));
            return;
        }
        FileLock fileLock = null;
        try {
            RandomAccessFile fis = new RandomAccessFile(file, "rw");
            FileChannel fileChannel = fis.getChannel();
            fileLock = fileChannel.tryLock();
            int len;
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((len = fis.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            String content = new String(outputStream.toByteArray());
            outputStream.close();
            JSONArray array;
            try {
                array = JSONArray.parseArray(content);
            } catch (Exception e) {
                file.renameTo(new File(SysConfig.dataPathFailed + "json_format_error@" + fileName));
                return;
            }

            if (array.size() < 1) {
                file.renameTo(new File(SysConfig.dataPathFailed + "no_data@" + fileName));
                return;
            }

            Map<String, JSONArray> dealData = commonService.validateData(tableName, array);//数据处理校验
            JSONArray success = dealData.get("success");
            JSONArray failed = dealData.get("failed");

            if (failed.size() > 0) {
                FileWriter fileWriter = new FileWriter(SysConfig.dataPathFailed + "data_invalid@" + fileName);
                fileWriter.write(failed.toString());
                fileWriter.close();
            }

            if (success.size() == 0) {
                return;
            }

            Map<String, String> fieldMap = commonService.queryFieldMap(tableName, false);
            List<String> ids = commonService.addDataList(success, entity.getRealTabName(), platformCode, true, fieldMap, true);

            if (ids.size() > 0) {
                buService.addStock(platformCode, entity.getTableName(), success);
                FileWriter fileWriter = new FileWriter(SysConfig.dataPathSuccess + fileName);
                fileWriter.write(success.toString());
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(SysConfig.dataPathSuccess + "ins_error@" + fileName);
                fileWriter.write(success.toString());
                fileWriter.close();
            }
            fileChannel.close();
            fis.close();
            file.delete();
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (IOException e) {
                }
            }
        }
    }
}
