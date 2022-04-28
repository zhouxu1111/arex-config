package com.arextest.config.core.repository.mapper;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.handler.StringToHashSetHandler;
import com.arextest.config.model.replay.ScheduleConfiguration;
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
public interface ScheduleConfigurationMapper extends RepositoryProvider<ScheduleConfiguration> {

    @Select("SELECT * FROM arex_replay_schedule_config WHERE app_Id = #{appId}")
    @Results(id = "app_schedule_result", value = {
            @Result(column = "dataChange_lastTime", property = "modifiedTime")
            , @Result(column = "send_max_qps", property = "sendMaxQps")
            , @Result(column = "app_Id", property = "appId")
            , @Result(column = "offset_days", property = "offsetDays")
            , @Result(column = "target_env", property = "targetEnv", typeHandler = StringToHashSetHandler.class)
    })
    @Override
    List<ScheduleConfiguration> listBy(@Param("appId") String appId);

    @Override
    @Select("SELECT * FROM arex_replay_schedule_config")
    @ResultMap(value = "app_schedule_result")
    List<ScheduleConfiguration> list();

    @Override
    @Update("UPDATE arex_replay_schedule_config SET " +
            "target_env=#{targetEnv,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            ", send_max_qps=#{sendMaxQps}" +
            ", offset_days=#{offsetDays}" +
            " WHERE app_Id=#{appId}")
    boolean update(ScheduleConfiguration configuration);

    @Insert("INSERT INTO arex_replay_schedule_config ( " +
            "app_id,target_env, send_max_qps,offset_days" +
            ") VALUES (" +
            "#{appId}" +
            ",#{targetEnv,jdbcType=OTHER,typeHandler=StringToHashSetHandler}" +
            ",#{sendMaxQps}" +
            ",#{offsetDays}" +
            ")")
    @Override
    boolean insert(ScheduleConfiguration configuration);

    @Delete("DELETE FROM arex_replay_schedule_config WHERE app_id=#{appId}")
    @Override
    boolean remove(ScheduleConfiguration configuration);
}
