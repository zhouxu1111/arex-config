package com.arextest.config.core.repository.mapper;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.handler.StringToArrayListHandler;
import com.arextest.config.model.replay.ComparisonDetailsConfiguration;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jmo
 * @since 2022/1/31
 */
@Repository
public interface ComparisonDetailsConfigurationMapper extends RepositoryProvider<ComparisonDetailsConfiguration> {

    @Select("SELECT " +
            "  id" +
            " ,path_name" +
            " ,path_value" +
            " ,expiration_type" +
            " ,expiration_date" +
            " ,decode_type" +
            " ,decode_prefix " +
            " ,decode_secret_key" +
            " ,comparison_id" +
            " ,dataChange_lastTime" +
            " FROM arex_comparison_details_config " +
            " WHERE comparison_id = #{comparisonId}")
    @Results(id = "comparison_details_result", value = {
            @Result(column = "path_name", property = "pathName")
            , @Result(column = "path_value", property = "pathValue", typeHandler = StringToArrayListHandler.class)
            , @Result(column = "expiration_type", property = "expirationType")
            , @Result(column = "decode_type", property = "decodeType")
            , @Result(column = "decode_prefix", property = "decodePrefix")
            , @Result(column = "decode_secret_key", property = "decodeSecretKey")
            , @Result(column = "comparison_id", property = "comparisonId")
            , @Result(column = "dataChange_lastTime", property = "modifiedTime")
    })
    List<ComparisonDetailsConfiguration> listBy(@Param("comparisonId") String comparisonId);

    @Select("SELECT * FROM arex_comparison_details_config")
    @ResultMap(value = "comparison_details_result")
    List<ComparisonDetailsConfiguration> list();

    @Override
    @Insert("INSERT INTO arex_comparison_details_config (" +
            " path_name" +
            ",path_value" +
            ",expiration_type" +
            ",expiration_date" +
            ",decode_type" +
            ",decode_prefix" +
            ",decode_secret_key" +
            ",comparison_id" +
            " ) VALUES (" +
            " #{pathName}" +
            ",#{pathValue,jdbcType=OTHER,typeHandler=StringToArrayListHandler}" +
            ",#{expirationType}" +
            ",#{expirationDate}" +
            ",#{decodeType}" +
            ",#{decodePrefix}" +
            ",#{decodeSecretKey}" +
            ",#{comparisonId}" +
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    boolean insert(ComparisonDetailsConfiguration configuration);

    @Override
    @Update("UPDATE arex_comparison_details_config SET " +
            " path_name=#{pathName}" +
            ",path_value=#{pathValue,jdbcType=OTHER,typeHandler=StringToArrayListHandler}" +
            ",expiration_type=#{expirationType}" +
            ",expiration_date=#{expirationDate}" +
            ",decode_type=#{decodeType}" +
            ",decode_prefix=#{decodePrefix}" +
            ",decode_secret_key=#{decodeSecretKey}" +
            " WHERE id=#{id}")
    boolean update(ComparisonDetailsConfiguration configuration);

    @Override
    @Delete(value = "DELETE FROM arex_comparison_details_config WHERE id=#{id}")
    boolean remove(ComparisonDetailsConfiguration configuration);
}
