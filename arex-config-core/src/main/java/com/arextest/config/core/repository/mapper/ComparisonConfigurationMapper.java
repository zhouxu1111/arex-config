package com.arextest.config.core.repository.mapper;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.model.replay.ComparisonConfiguration;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jmo
 * @since 2022/1/31
 */
@Repository
public interface ComparisonConfigurationMapper extends RepositoryProvider<ComparisonConfiguration> {

    @Select("SELECT " +
            "id ,app_Id,operation_id,category_type,dataChange_lastTime " +
            "FROM arex_comparison_config " +
            "WHERE app_id = #{appId} OR app_id IS NULL")
    @Results(id = "comparison_config_result", value = {
            @Result(column = "dataChange_lastTime", property = "modifiedTime")
            , @Result(column = "id", property = "id")
            , @Result(column = "category_type", property = "categoryType")
            , @Result(column = "operation_id", property = "operationId")
            , @Result(column = "id", property = "detailsList", many = @Many(select =
            "com.arextest.config.core.repository.mapper.ComparisonDetailsConfigurationMapper" +
                    ".listBy"))
    })
    List<ComparisonConfiguration> listBy(@Param("appId") String appId);

    @Select("SELECT * FROM arex_comparison_config")
    @ResultMap(value = "comparison_config_result")
    List<ComparisonConfiguration> list();

    @Insert("INSERT INTO arex_comparison_config (" +
            "  app_id" +
            " ,operation_id" +
            " ,category_type" +
            ") VALUES (" +
            "  #{appId}" +
            " ,#{operationId}" +
            " ,#{categoryType}" +
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    boolean insert(ComparisonConfiguration configuration);

    @Delete(value = "DELETE FROM arex_comparison_config WHERE id=#{id}")
    boolean remove(ComparisonConfiguration configuration);
}
