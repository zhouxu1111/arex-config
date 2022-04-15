package io.arex.config.core.repository.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author jmo
 * @since 2021/12/21
 */
public class StringToHashSetHandler extends BaseTypeHandler<Collection<String>> {
    private static final String COMMA_DELIMITER = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Collection<String> parameter, JdbcType jdbcType)
            throws SQLException {
        if (CollectionUtils.isEmpty(parameter)) {
            ps.setString(i, StringUtils.EMPTY);
            return;
        }
        ps.setString(i, String.join(COMMA_DELIMITER, parameter));
    }

    @Override
    public Collection<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String source = rs.getString(columnName);
        return spiltToCollection(source);
    }

    @Override
    public Collection<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String source = rs.getString(columnIndex);
        return spiltToCollection(source);
    }

    @Override
    public Collection<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String source = cs.getString(columnIndex);
        return spiltToCollection(source);
    }

    private Collection<String> spiltToCollection(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        int fromIndex = 0;
        int next;
        Collection<String> list = newCollectionInstance();
        while ((next = source.indexOf(COMMA_DELIMITER, fromIndex)) != -1) {
            list.add(source.substring(fromIndex, next));
            fromIndex = next + 1;
        }
        list.add(source.substring(fromIndex));
        return list;
    }

    Collection<String> newCollectionInstance() {
        return new HashSet<>();
    }
}
