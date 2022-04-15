package io.arex.config.core.repository.mapper;

import io.arex.config.core.repository.RepositoryProvider;
import io.arex.config.model.record.DynamicClassConfiguration;
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
public interface DynamicClassConfigurationMapper extends RepositoryProvider<DynamicClassConfiguration> {

    @Select("SELECT * FROM arex_dynamic_class WHERE app_id = #{appId}")
    @Results(id = "dynamicClass_config_result", value = {
            @Result(column = "dataChange_lastTime", property = "modifiedTime")
    })
    @Override
    List<DynamicClassConfiguration> listBy(@Param("appId") String appId);

    @Override
    @Select("SELECT * FROM arex_dynamic_class")
    @ResultMap(value = "dynamicClass_config_result")
    List<DynamicClassConfiguration> list();

    @Override
    @Insert("INSERT INTO arex_dynamic_class(" +
            "app_id,full_class_name,method_name,parameter_types,key_formula,config_type" +
            ") VALUES (" +
            "#{appId},#{fullClassName},#{methodName},#{parameterTypes},#{keyFormula},#{configType}" +
            ")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    boolean insert(DynamicClassConfiguration configuration);

    @Override
    @Delete(value = "DELETE FROM arex_dynamic_class WHERE id=#{id}")
    boolean remove(DynamicClassConfiguration configuration);

    @Override
    @Update(value = "UPDATE arex_dynamic_class SET " +
            " full_class_name=#{fullClassName}" +
            ",method_name=#{methodName}" +
            ",parameter_types=#{parameterTypes}" +
            ",key_formula=#{keyFormula}" +
            " WHERE id=#{id}")
    boolean update(DynamicClassConfiguration configuration);
}
