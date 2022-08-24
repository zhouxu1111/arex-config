package com.arextest.config.model.replay;

import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.ReplayScheduleConfigCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author jmo
 * @see ReplayScheduleConfigCollection
 * @since 2021/12/21
 */
@Getter
@Setter
public class ScheduleConfiguration extends AbstractConfiguration {
    private String appId;
    /**
     * 默认回放Case范围
     */
    private Integer offsetDays;

    /**
     * 默认回放环境
     */
    private Set<String> targetEnv;
    private Integer sendMaxQps;

}
