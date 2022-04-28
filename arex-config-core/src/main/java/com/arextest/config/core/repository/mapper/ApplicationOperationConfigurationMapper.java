package com.arextest.config.core.repository.mapper;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.model.application.ApplicationOperationConfiguration;
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
public interface ApplicationOperationConfigurationMapper extends RepositoryProvider<ApplicationOperationConfiguration> {

    @Results(id = "operation_config_result", value = {
            @Result(column = "id", property = "id")
            , @Result(column = "app_id", property = "appId")
            , @Result(column = "service_id", property = "serviceId")
            , @Result(column = "status", property = "status")
            , @Result(column = "operation_name", property = "operationName")
            , @Result(column = "operation_type", property = "operationType")
            , @Result(column = "recorded_case_count", property = "recordedCaseCount")
            , @Result(column = "dataChange_lastTime", property = "modifiedTime")
    })
    @Select("SELECT " +
            "  id" +
            " ,service_id" +
            " ,app_id" +
            " ,operation_name" +
            " ,status" +
            " ,operation_type" +
            " ,recorded_case_count" +
            " ,dataChange_lastTime" +
            " FROM arex_service_operation" +
            " WHERE app_id = #{appId}")
    @Override
    List<ApplicationOperationConfiguration> listBy(@Param("appId") String appId);

    @Select("SELECT " +
            "  id" +
            " ,service_id" +
            " ,app_id" +
            " ,operation_name" +
            " ,status" +
            " ,operation_type" +
            " ,recorded_case_count" +
            " ,dataChange_lastTime" +
            " FROM arex_service_operation" +
            " WHERE service_id = #{serviceId}")
    @ResultMap(value = "operation_config_result")
    List<ApplicationOperationConfiguration> operationList(@Param("serviceId") long serviceId);

    @Select("SELECT " +
            "  id" +
            " ,service_id" +
            " ,app_id" +
            " ,operation_name" +
            " ,status" +
            " ,operation_type" +
            " ,recorded_case_count" +
            " ,dataChange_lastTime" +
            " FROM arex_service_operation")
    @ResultMap(value = "operation_config_result")
    @Override
    List<ApplicationOperationConfiguration> list();

    @Insert("INSERT INTO arex_service_operation (" +
            "  service_id" +
            " ,app_id" +
            " ,operation_name" +
            " ,status" +
            " ,operation_type" +
            " ,recorded_case_count" +
            " ) VALUES ( " +
            "  #{serviceId}" +
            " ,#{appId}" +
            " ,#{operationName}" +
            " ,#{status}" +
            " ,#{operationType}" +
            " ,#{recordedCaseCount}" +
            " )")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    @Override
    boolean insert(ApplicationOperationConfiguration configuration);

    @Update("UPDATE arex_service_operation SET " +
            "  status=#{status}" +
            " ,recorded_case_count#{recordedCaseCount}" +
            " WHERE id=#{id}"
    )
    @Override
    boolean update(ApplicationOperationConfiguration configuration);

    @Delete("DELETE FROM arex_service_operation WHERE id=#{id}")
    @Override
    boolean remove(ApplicationOperationConfiguration configuration);
}
