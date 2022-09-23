package com.arextest.config.core.dynamic;

import com.arextest.config.core.handler.AbstractConfigurableHandler;
import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.model.record.DynamicClassConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jmo
 * @since 2022/1/22
 */
@Component
final class DynamicClassConfigurableHandler extends AbstractConfigurableHandler<DynamicClassConfiguration> {
    protected DynamicClassConfigurableHandler(@Autowired RepositoryProvider<DynamicClassConfiguration> repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public boolean insert(DynamicClassConfiguration configuration) {
        if (StringUtils.isEmpty(configuration.getAppId())) {
            return false;
        }
        if (StringUtils.isEmpty(configuration.getFullClassName())) {
            return false;
        }
        return super.insert(configuration);
    }
}
