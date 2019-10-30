package net.ys.dao;

import net.ys.bean.BusAtt;
import net.ys.bean.SegFile;
import net.ys.mapper.BusAttMapper;
import net.ys.mapper.SegFileMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class FileDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void saveFiles(List<String> files) {
        String[] sql = new String[files.size()];
        files.toArray(sql);
        jdbcTemplate.batchUpdate(sql);
    }

    public List<BusAtt> queryBusAtt() {
        String sql = "SELECT ID, FILE_NAME, ATT_NAME, TABLE_NAME, FIELD_NAME, DATA_PRI_FIELD, DATA_ID, PLATFORM_CODE, FILE_PATH, UP_STATUS FROM BUS_ATTACHMENT WHERE UP_STATUS = 0";
        return jdbcTemplate.query(sql, new BusAttMapper());
    }

    public List<SegFile> queryBusAttTmp(String attName) {
        String sql = "SELECT ID, FILE_INDEX, START_POINT, ATT_NAME, FILE_NAME, PATH, FILE_LEN, UP_STATUS FROM BUS_ATTACHMENT_TMP WHERE ATT_NAME = ? AND UP_STATUS = 0";
        return jdbcTemplate.query(sql, new SegFileMapper(), attName);
    }

    public void chgUpStatus(List<String> upSql) {
        String[] sql = new String[upSql.size()];
        upSql.toArray(sql);
        jdbcTemplate.batchUpdate(sql);

    }
}
