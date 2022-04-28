package com.arextest.config.core.repository.mapper;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.model.application.ApplicationServiceConfiguration;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
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
public interface ApplicationServiceConfigurationMapper extends RepositoryProvider<ApplicationServiceConfiguration> {

    @Select("SELECT id ,app_id,service_key,service_name,status,dataChange_lastTime " +
            "FROM arex_service " +
            "WHERE " +
            "app_Id = #{appId}")
    @Results(id = "service_config_result", value = {
            @Result(column = "dataChange_lastTime", property = "modifiedTime")
            , @Result(column = "id", property = "id")
            , @Result(column = "app_id", property = "appId")
            , @Result(column = "service_key", property = "serviceKey")
            , @Result(column = "service_name", property = "serviceName")
            , @Result(column = "id", property = "operationList", many = @Many(select =
            "com.arextest.config.core.repository.mapper.ApplicationOperationConfigurationMapper" +
                    ".operationList"))
    })
    @Override
    List<ApplicationServiceConfiguration> listBy(@Param("appId") String appId);

    @Override
    @Select("SELECT SELECT id ,app_id,service_key,service_name,status,dataChange_lastTime " +
            "FROM arex_service")
    @ResultMap(value = "service_config_result")
    List<ApplicationServiceConfiguration> list();


    @Override
    @Select("SELECT count(*) FROM arex_service WHERE app_id=#{appId}")
    int count(String appId);

    @Override
    @Insert("INSERT INTO arex_service (" +
            " app_Id" +
            ",service_key" +
            ",service_name" +
            ",status" +
            " ) VALUES (" +
            " #{appId}" +
            ",#{serviceKey}" +
            ",#{serviceName}" +
            ",#{status}" +
            " )")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    boolean insert(ApplicationServiceConfiguration configuration);

    @Override
    @Delete(value = "DELETE FROM arex_service WHERE id=#{id}")
    boolean remove(ApplicationServiceConfiguration configuration);

    @Update("UPDATE arex_service SET status=#{status} WHERE id=#{id}")
    @Override
    boolean update(ApplicationServiceConfiguration configuration);
}
