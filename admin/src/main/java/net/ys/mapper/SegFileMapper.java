package net.ys.mapper;

import net.ys.bean.SegFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SegFileMapper implements RowMapper<SegFile> {
    @Override
    public SegFile mapRow(ResultSet resultSet, int i) throws SQLException {
        SegFile segFile = new SegFile();
        segFile.setId(resultSet.getLong("ID"));
        segFile.setIndex(resultSet.getInt("FILE_INDEX"));
        segFile.setStartPoint(resultSet.getLong("START_POINT"));
        segFile.setFileNameTmp(resultSet.getString("ATT_NAME"));
        segFile.setFileName(resultSet.getString("FILE_NAME"));
        segFile.setPath(resultSet.getInt("PATH"));
        segFile.setFileLen(resultSet.getLong("FILE_LEN"));
        segFile.setUpStatus(resultSet.getInt("UP_STATUS"));
        return segFile;
    }
}