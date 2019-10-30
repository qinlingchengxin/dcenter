package net.ys.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.ys.bean.DownStock;
import net.ys.bean.Stock;
import net.ys.bean.SyncTable;
import net.ys.component.SysConfig;
import net.ys.service.CommonService;
import net.ys.utils.KeyUtil;
import net.ys.utils.LogUtil;
import net.ys.utils.RSAUtil;
import net.ys.utils.req.HttpClient;
import net.ys.utils.req.HttpResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 定时任务
 */
@Service
public class ScheduleService {

    private static final int THREAD_NUM = 15;//线程数量

    private static final int SEMAPHORE_NUM = 5;//信号量数量

    private static final int PAGE_SIZE = 1000;//每页打包数量

    private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";

    private static final String ENCODING = "UTF-8";

    @Resource
    private BuService buService;

    @Resource
    private CommonService commonService;

    private static void sleep() {
        try {
            Thread.sleep(Math.round(Math.random() * 100));
        } catch (InterruptedException e) {
        }
    }

    /**
     * 定时上传待传输数据
     */
    public void syncUpWaitData() {
        System.out.println("syncUpWaitData----->" + System.currentTimeMillis());
        threadExec(0);
    }

    /**
     * 定时上传传输失败数据
     */
    public void syncUpFailedData() {
        System.out.println("syncUpFailedData----->" + System.currentTimeMillis());
        threadExec(3);
    }

    private void threadExec(int stockStatus) {

        long now = System.currentTimeMillis() - 1000;//当前时间,往前推一秒
        List<Stock> dataList = buService.queryStocks(stockStatus, now);

        if (dataList.size() == 0) {
            return;
        }

        String privateKey = KeyUtil.getPrivateKey();//获取私钥
        if ("".equals(privateKey)) {
            return;
        }

        int dataCount = dataList.size();
        int threadNum = dataCount < THREAD_NUM ? dataCount : THREAD_NUM;
        int semNum = dataCount < SEMAPHORE_NUM ? dataCount : SEMAPHORE_NUM;

        ExecutorService list = Executors.newFixedThreadPool(threadNum);
        Semaphore semaphore = new Semaphore(semNum);
        for (Stock stock : dataList) {
            sleep();
            list.submit(new SyncDataThread(semaphore, stock, SysConfig.decenterUrl, privateKey));
        }
        list.shutdown();
        semaphore.acquireUninterruptibly(semNum);
        semaphore.release(semNum);
    }

    /**
     * 定时同步待传输数据,分页传输
     */
    public void syncUpDataStep(Stock stock, String deCenterUrl, String privateKey) {
        System.out.println("syncUpDataStep---->" + System.currentTimeMillis());
        try {

            String data = stock.getContent();
            String encryptData = RSAUtil.encrypt(data, privateKey, false);

            URL url = new URL(deCenterUrl + "/api/syncUps.do?table_name=" + stock.getTableName() + "&platform_code=" + stock.getPlatformCode());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", ENCODING);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
            contentBody.append("\r\n").append("Content-Disposition: form-data; name=\"").append("data\"").append("\r\n\r\n").append(encryptData).append("\r\n--").append(BOUNDARY);
            String boundaryMessage1 = contentBody.toString();
            OutputStream out = connection.getOutputStream();
            out.write(boundaryMessage1.getBytes(ENCODING));
            out.write((BOUNDARY + "--\r\n").getBytes(ENCODING));
            String endBoundary = "\r\n--" + BOUNDARY + "--\r\n";
            out.write(endBoundary.getBytes(ENCODING));
            out.flush();
            out.close();
            InputStream in = connection.getInputStream();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                String strLine;
                StringBuffer strResponse = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
                while ((strLine = reader.readLine()) != null) {
                    strResponse.append(strLine);
                }

                String resp = strResponse.toString();
                JSONObject jsonObject = JSONObject.parseObject(resp);
                int resultCode = jsonObject.getInteger("code");
                if (resultCode == 1000) {
                    boolean flag = buService.chgStock(stock.getId(), 2);//更新状态
                    if (flag) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("data trans failed---->" + e.getMessage());
        }
        buService.chgStock(stock.getId(), 3);//更新状态
    }

    /**
     * 定时打包数据
     */
    public void packData() {
        System.out.println("syncUpDataStep---->" + System.currentTimeMillis());
        List<SyncTable> tables = buService.queryAllTables();
        if (tables.size() == 0) {
            return;
        }

        long now = System.currentTimeMillis() - 1000;//当前时间,往前推一秒

        int tableNum = tables.size();
        int threadNum = tableNum < THREAD_NUM ? tableNum : THREAD_NUM;
        int semNum = tableNum < SEMAPHORE_NUM ? tableNum : SEMAPHORE_NUM;

        ExecutorService list = Executors.newFixedThreadPool(threadNum);
        Semaphore semaphore = new Semaphore(semNum);
        for (SyncTable table : tables) {
            sleep();
            list.submit(new PackThread(semaphore, table, SysConfig.platformCode, now));
        }
        list.shutdown();
        semaphore.acquireUninterruptibly(semNum);
        semaphore.release(semNum);
    }

    /**
     * 分页打包数据
     */
    public void packDataByPage(String platformCode, SyncTable table, long time, long page) {
        String fieldStr = buService.queryFields(table.getTableName());

        for (int i = 1; i <= page; i++) {
            List<Map<String, Object>> data = buService.queryPackDataByPage(platformCode, table, time, fieldStr, i, PAGE_SIZE);
            if (data.size() == 0) {
                return;
            }
            JSONArray success = JSONArray.parseArray(JSONArray.toJSONString(data, SerializerFeature.WriteMapNullValue));
            buService.addStock(platformCode, table.getTableName(), success);
        }

        buService.chgStockPackStatus(table, time);
    }

    public void syncDownWaitData() {
        System.out.println("syncDownWaitData----->" + System.currentTimeMillis());
        syncDownData(0);
    }

    public void syncDownFailedData() {
        System.out.println("syncDownFailedData----->" + System.currentTimeMillis());
        syncDownData(3);
    }

    /**
     * 定时下载数据
     */
    public void syncDownData(int status) {

        System.out.println("syncDownData----->" + System.currentTimeMillis());

        long now = System.currentTimeMillis() - 1000;//当前时间,往前推一秒

        List<DownStock> stocks = buService.queryDownStocks(status, now);
        if (stocks.size() == 0) {
            return;
        }

        String privateKey = KeyUtil.getPrivateKey();//私钥
        if ("".equals(privateKey)) {
            return;
        }

        int dataCount = stocks.size();
        int threadNum = dataCount < THREAD_NUM ? dataCount : THREAD_NUM;
        int semNum = dataCount < SEMAPHORE_NUM ? dataCount : SEMAPHORE_NUM;

        ExecutorService list = Executors.newFixedThreadPool(threadNum);
        Semaphore semaphore = new Semaphore(semNum);
        for (DownStock downStock : stocks) {
            sleep();
            Map<String, String> fieldMap = commonService.queryFieldMap(downStock.getTableName(), false);
            list.submit(new SyncDownDataThread(semaphore, downStock, fieldMap, SysConfig.platformCode, SysConfig.decenterUrl, privateKey));
        }
        list.shutdown();
        semaphore.acquireUninterruptibly(semNum);
        semaphore.release(semNum);
    }

    /**
     * 定时下载数据,分页传输
     */
    public void syncDownDataStep(DownStock downStock, Map<String, String> fieldMap, String platformCode, String privateKey) {
        System.out.println("syncDownDataStep---->" + Thread.currentThread().getName());

        try {
            String url = SysConfig.decenterUrl + "/api/syncDown.do";
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("table_name", downStock.getTableName());
            map.put("platform_code", platformCode);
            map.put("start_time", String.valueOf(downStock.getStartTime()));
            map.put("end_time", String.valueOf(downStock.getEndTime()));
            map.put("page", String.valueOf(downStock.getPage()));
            map.put("page_size", String.valueOf(downStock.getPageSize()));
            HttpResponse response = HttpClient.doPost(url, map);
            if (response.getCode() == 200) {
                String resp = response.getValue();
                JSONObject jsonObject = JSONObject.parseObject(resp);
                if (jsonObject.getInteger("code") == 1000) {
                    String result = jsonObject.getString("data");
                    String decryptData = RSAUtil.decrypt(result, privateKey, false);
                    if ("".equals(decryptData)) {
                        return;
                    }

                    JSONArray data = JSONArray.parseArray(decryptData);

                    if (data.size() > 0) {
                        List<String> ids = commonService.addDataList(data, downStock.getRealTabName(), platformCode, false, fieldMap, true);
                        if (ids.size() == data.size()) {
                            buService.updateDownStockStatus(downStock, 2);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }

        buService.updateDownStockStatus(downStock, 3);
    }

    class PackThread extends Thread {
        private Semaphore semaphore;
        private SyncTable table;
        private long time;
        private String platformCode;

        public PackThread(Semaphore semaphore, SyncTable table, String platformCode, long time) {
            this.semaphore = semaphore;
            this.table = table;
            this.platformCode = platformCode;
            this.time = time;
        }

        public void run() {
            try {
                semaphore.acquire();

                //查询时间段内的数据总条数
                long dataCount = buService.queryDataPackCount(platformCode, table.getRealTabName(), table.getLastPackTime(), time);
                if (dataCount == 0) {
                    return;
                }

                long page = dataCount / PAGE_SIZE + (dataCount % PAGE_SIZE == 0 ? 0 : 1);//计算页数

                packDataByPage(platformCode, table, time, page);

                semaphore.release();
            } catch (Exception e) {
                LogUtil.error(e);
            }
        }
    }

    class SyncDataThread extends Thread {
        private Semaphore semaphore;
        private Stock stock;
        private String deCenterUrl;
        private String privateKey;

        public SyncDataThread(Semaphore semaphore, Stock stock, String deCenterUrl, String privateKey) {
            this.semaphore = semaphore;
            this.stock = stock;
            this.deCenterUrl = deCenterUrl;
            this.privateKey = privateKey;
        }

        public void run() {
            try {
                semaphore.acquire();
                syncUpDataStep(stock, deCenterUrl, privateKey);
                semaphore.release();
            } catch (Exception e) {
                LogUtil.error(e);
            }
        }
    }

    class SyncDownDataThread extends Thread {
        private Semaphore semaphore;
        private DownStock downStock;
        private Map<String, String> fieldMap;
        private String platformCode;
        private String deCenterUrl;
        private String privateKey;

        public SyncDownDataThread(Semaphore semaphore, DownStock downStock, Map<String, String> fieldMap, String platformCode, String deCenterUrl, String privateKey) {
            this.semaphore = semaphore;
            this.downStock = downStock;
            this.fieldMap = fieldMap;
            this.platformCode = platformCode;
            this.deCenterUrl = deCenterUrl;
            this.privateKey = privateKey;
        }

        public void run() {
            try {
                semaphore.acquire();
                syncDownDataStep(downStock, fieldMap, platformCode, privateKey);
                semaphore.release();
            } catch (Exception e) {
                LogUtil.error(e);
            }
        }
    }
}
