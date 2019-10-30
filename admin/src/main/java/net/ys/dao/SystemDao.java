package net.ys.dao;

import net.ys.bean.BusConfig;
import net.ys.bean.BusEnum;
import net.ys.bean.BusEnumValue;
import net.ys.mapper.BusConfigMapper;
import net.ys.mapper.BusEnumMapper;
import net.ys.mapper.BusEnumValueMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SystemDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<BusConfig> queryBusConfigs(int page, int pageSize) {
        String sql = "SELECT ID, CFG_NAME, CODE, VI, VS FROM BUS_CONFIG LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusConfigMapper(), (page - 1) * pageSize, pageSize);
    }

    public boolean updateBusConfig(BusConfig busConfig) {
        String sql = "UPDATE BUS_CONFIG SET CFG_NAME = ?, VI = ?, VS = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, busConfig.getCfgName(), busConfig.getVi(), busConfig.getVs(), busConfig.getId()) >= 0;
    }

    public boolean addBusConfig(final BusConfig busConfig) {
        String sql = "INSERT INTO BUS_CONFIG (ID, CFG_NAME, CODE, VI, VS ) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql, busConfig.getId(), busConfig.getCfgName(), busConfig.getCode(), busConfig.getVi(), busConfig.getVs()) > 0;
    }

    public List<BusEnum> queryBusEnums(int page, int pageSize) {
        String sql = "SELECT ID, ENUM_NAME, CODE FROM BUS_ENUM LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusEnumMapper(), (page - 1) * pageSize, pageSize);
    }

    public boolean updateBusEnum(BusEnum busEnum) {
        String sql = "UPDATE BUS_ENUM SET ENUM_NAME = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, busEnum.getEnumName(), busEnum.getId()) >= 0;
    }

    public boolean addBusEnum(BusEnum busEnum) {
        String sql = "INSERT INTO BUS_ENUM (ID, ENUM_NAME, CODE) VALUES (?,?,?)";
        return jdbcTemplate.update(sql, busEnum.getId(), busEnum.getEnumName(), busEnum.getCode()) > 0;
    }

    public List<BusEnumValue> queryBusEnumValues(int code, int page, int pageSize) {
        String sql = "SELECT ID, ENUM_CODE, VI, VS FROM BUS_ENUM_VALUE WHERE ENUM_CODE = ? LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusEnumValueMapper(), code, (page - 1) * pageSize, pageSize);
    }

    public boolean updateBusEnumValue(BusEnumValue busEnumValue) {
        String sql = "UPDATE BUS_ENUM_VALUE SET VI = ?, VS = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, busEnumValue.getVi(), busEnumValue.getVs(), busEnumValue.getId()) >= 0;
    }

    public boolean addBusEnumValue(BusEnumValue busEnumValue) {
        String sql = "INSERT INTO BUS_ENUM_VALUE (ID, ENUM_CODE, VI, VS) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql, busEnumValue.getId(), busEnumValue.getEnumCode(), busEnumValue.getVi(), busEnumValue.getVs()) > 0;
    }

    public BusConfig queryBusConfig(int code) {
        String sql = "SELECT ID, CFG_NAME, CODE, VI, VS FROM BUS_CONFIG WHERE CODE = ?";
        List<BusConfig> list = jdbcTemplate.query(sql, new BusConfigMapper(), code);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public long queryBusConfigCount() {
        String sql = "SELECT COUNT(*) FROM BUS_CONFIG";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public int queryBusConfigCurrCode() {
        String sql = "SELECT MAX(CODE) FROM BUS_CONFIG";
        return jdbcTemplate.queryForObject(sql, Integer.class) + 1;
    }

    public long queryBusEnumCount() {
        String sql = "SELECT COUNT(*) FROM BUS_ENUM";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public BusEnum queryBusEnum(int code) {
        String sql = "SELECT ID, ENUM_NAME, CODE FROM BUS_ENUM WHERE CODE = ?";
        List<BusEnum> list = jdbcTemplate.query(sql, new BusEnumMapper(), code);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public int queryBusEnumCurrCode() {
        String sql = "SELECT MAX(CODE) FROM BUS_ENUM";
        return jdbcTemplate.queryForObject(sql, Integer.class) + 1;
    }

    public long queryBusEnumValueCount(int code) {
        String sql = "SELECT COUNT(*) FROM BUS_ENUM_VALUE WHERE ENUM_CODE = " + code;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public BusEnumValue queryBusEnumValue(String id) {
        String sql = "SELECT ID, ENUM_CODE, VI, VS FROM BUS_ENUM_VALUE WHERE ID = ?";
        List<BusEnumValue> list = jdbcTemplate.query(sql, new BusEnumValueMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
