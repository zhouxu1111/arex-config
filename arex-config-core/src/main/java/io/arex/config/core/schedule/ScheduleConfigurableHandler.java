package io.arex.config.core.schedule;

import io.arex.config.core.handler.AbstractConfigurableHandler;
import io.arex.config.core.repository.RepositoryProvider;
import io.arex.config.model.replay.ScheduleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author jmo
 * @since 2022/1/22
 */
@Component
final class ScheduleConfigurableHandler extends AbstractConfigurableHandler<ScheduleConfiguration> {
    @Resource
    private ScheduleConfiguration globalScheduleConfiguration;

    protected ScheduleConfigurableHandler(@Autowired RepositoryProvider<ScheduleConfiguration> repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public boolean update(ScheduleConfiguration configuration) {
        return super.update(configuration) || super.insert(configuration);
    }

    @Override
    protected List<ScheduleConfiguration> createFromGlobalDefault(String appId) {
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration();
        scheduleConfiguration.setAppId(appId);
        scheduleConfiguration.setOffsetDays(globalScheduleConfiguration.getOffsetDays());
        scheduleConfiguration.setSendMaxQps(globalScheduleConfiguration.getSendMaxQps());
        scheduleConfiguration.setTargetEnv(globalScheduleConfiguration.getTargetEnv());
        return Collections.singletonList(scheduleConfiguration);
    }

    @Override
    protected boolean shouldMergeGlobalDefault() {
        return false;
    }

    @Configuration
    @ConfigurationProperties(prefix = "arex.config.default.schedule")
    static class GlobalScheduleConfiguration extends ScheduleConfiguration {

    }

}
