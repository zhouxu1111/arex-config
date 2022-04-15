package io.arex.config.core.repository.mapper;

import io.arex.config.core.repository.RepositoryProvider;
import io.arex.config.model.application.ApplicationConfiguration;
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
public interface ApplicationConfigurationMapper extends RepositoryProvider<ApplicationConfiguration> {

    @Select("SELECT " +
            "   app_id" +
            " , agent_version" +
            " , agent_ext_version" +
            " , app_name" +
            " , app_description" +
            " , app_owner" +
            " , bu_organization_name " +
            " , recorded_case_count" +
            " , bu_organization_id" +
            " , group_name" +
            " , group_id " +
            " , status" +
            " , features" +
            " , category" +
            " , dataChange_lastTime " +
            "FROM arex_app WHERE app_Id = #{appId}")
    @Results(id = "app_result", value = {
            @Result(column = "app_Id", property = "appId")
            , @Result(column = "agent_version", property = "agentVersion")
            , @Result(column = "agent_ext_version", property = "agentExtVersion")
            , @Result(column = "app_name", property = "appName")
            , @Result(column = "app_description", property = "description")
            , @Result(column = "app_owner", property = "owner")
            , @Result(column = "bu_organization_name", property = "organizationName")
            , @Result(column = "organization_Id", property = "organizationId")
            , @Result(column = "recorded_case_count", property = "recordedCaseCount")
            , @Result(column = "group_id", property = "groupId")
            , @Result(column = "group_name", property = "groupName")
            , @Result(column = "dataChange_lastTime", property = "modifiedTime")
    })
    @Override
    List<ApplicationConfiguration> listBy(@Param("appId") String appId);

    @Select("SELECT * FROM arex_app")
    @ResultMap(value = "app_result")
    @Override
    List<ApplicationConfiguration> list();

    @Update("UPDATE arex_app SET " +
            "  agent_version=#{agentVersion}" +
            " , agent_ext_version=#{agentExtVersion}" +
            " , status=#{status}" +
            " , features=#{features}" +
            " WHERE app_Id=#{appId}")
    @Override
    boolean update(ApplicationConfiguration configuration);

    @Insert("INSERT INTO arex_app (" +
            "   app_id" +
            " , agent_version" +
            " , agent_ext_version" +
            " , app_name" +
            " , app_description" +
            " , app_owner" +
            " , bu_organization_name " +
            " , recorded_case_count" +
            " , bu_organization_id" +
            " , group_name" +
            " , group_id " +
            " , status" +
            " , features" +
            " , category" +
            " ) VALUES ( " +
            "   #{appId}" +
            " , #{agentVersion}" +
            " , #{agentExtVersion}" +
            " , #{appName}" +
            " , #{description}" +
            " , #{owner}" +
            " , #{organizationName}" +
            " , #{recordedCaseCount}" +
            " , #{organizationId}" +
            " , #{groupName}" +
            " , #{groupId}" +
            " , #{status}" +
            " , #{features}" +
            " , #{category} " +
            ")")
    @Override
    boolean insert(ApplicationConfiguration configuration);
}
