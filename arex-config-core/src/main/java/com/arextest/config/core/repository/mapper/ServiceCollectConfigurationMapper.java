package com.arextest.config.core.repository.mapper;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.handler.StringToHashSetHandler;
import com.arextest.config.model.record.ServiceCollectConfiguration;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jmo
 * @since 2022/1/31
 */
@Repository
public interface ServiceCollectConfigurationMapper extends RepositoryProvider<ServiceCollectConfiguration> {

    @Select("SELECT " +
            "  app_id " +
            " , sample_rate " +
            " , exclude_dependent_operation" +
            " , exclude_dependent_service " +
            " , exclude_operation " +
            " , include_operation" +
            " , include_service" +
            " , allow_day_of_weeks" +
            " , allow_time_of_day_from" +
            " , allow_time_of_day_to" +
            " , dataChange_LastTime " +
            "FROM arex_record_service_config " +
            "WHERE app_Id = #{appId}")
    @Results(id = "app_collect_result", value = {
            @Result(column = "app_Id", property = "appId")
            , @Result(column = "sample_rate", property = "sampleRate")
            , @Result(column = "exclude_dependent_operation", property = "excludeOperationSet", typeHandler =
            StringToHashSetHandler.class)
            , @Result(column = "exclude_dependent_service", property = "excludeDependentServiceSet", typeHandler =
            StringToHashSetHandler.class)
            , @Result(column = "exclude_operation", property = "excludeDependentOperationSet", typeHandler =
            StringToHashSetHandler.class)
            , @Result(column = "include_operation", property = "includeOperationSet", typeHandler =
            StringToHashSetHandler.class)
            , @Result(column = "include_service", property = "includeServiceSet", typeHandler =
            StringToHashSetHandler.class)
            , @Result(column = "allow_day_of_weeks", property = "allowDayOfWeeks")
            , @Result(column = "allow_time_of_day_from", property = "allowTimeOfDayFrom")
            , @Result(column = "allow_time_of_day_to", property = "allowTimeOfDayTo")
            , @Result(column = "dataChange_lastTime", property = "modifiedTime")
    })
    List<ServiceCollectConfiguration> listBy(@Param("appId") String appId);

    @Override
    @Select("SELECT * FROM arex_record_service_config ")
    @ResultMap(value = "app_collect_result")
    List<ServiceCollectConfiguration> list();

    @Update("UPDATE arex_record_service_config SET" +
            "  sample_rate=#{sampleRate}" +
            " ,exclude_dependent_operation=#{excludeDependentOperationSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,exclude_dependent_service=#{excludeDependentServiceSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,exclude_operation=#{excludeDependentOperationSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,include_operation=#{includeOperationSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,include_service=#{includeServiceSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,allow_day_of_weeks=#{allowDayOfWeeks}" +
            " ,allow_time_of_day_from=#{allowTimeOfDayFrom}" +
            " ,allow_time_of_day_to=#{allowTimeOfDayTo}" +
            "WHERE app_Id=#{appId}")
    boolean update(ServiceCollectConfiguration configuration);

    @Override
    @Insert("INSERT INTO arex_record_service_config (" +
            "  app_id" +
            " ,sample_rate" +
            " ,exclude_dependent_operation" +
            " ,exclude_dependent_service" +
            " ,exclude_operation" +
            " ,include_operation" +
            " ,include_service" +
            " ,allow_day_of_weeks" +
            " ,allow_time_of_day_from" +
            " ,allow_time_of_day_to" +
            " ) VALUES (" +
            "  #{appId}" +
            " ,#{sampleRate}" +
            " ,#{excludeDependentOperationSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,#{excludeDependentServiceSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,#{excludeDependentOperationSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,#{includeOperationSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,#{includeServiceSet,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            " ,#{allowDayOfWeeks}" +
            " ,#{allowTimeOfDayFrom}" +
            " ,#{allowTimeOfDayTo}" +
            ")")
    boolean insert(ServiceCollectConfiguration configuration);

    @Override
    @Delete("DELETE FROM arex_record_service_config WHERE app_Id=#{appId}")
    boolean remove(ServiceCollectConfiguration configuration);
}
