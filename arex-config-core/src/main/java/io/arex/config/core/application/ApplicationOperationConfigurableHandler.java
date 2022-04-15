package io.arex.config.core.application;

import io.arex.config.core.handler.AbstractConfigurableHandler;
import io.arex.config.core.repository.RepositoryProvider;
import io.arex.config.model.application.ApplicationOperationConfiguration;
import io.arex.config.model.record.ServiceCollectConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jmo
 * @since 2022/1/23
 */
@Component
final class ApplicationOperationConfigurableHandler extends AbstractConfigurableHandler<ApplicationOperationConfiguration> {
    @Resource
    private ServiceCollectConfiguration globalDefaultConfiguration;

    protected ApplicationOperationConfigurableHandler(@Autowired RepositoryProvider<ApplicationOperationConfiguration> repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public boolean insert(ApplicationOperationConfiguration configuration) {
        if (configuration.getServiceId() == null) {
            return false;
        }
        if (StringUtils.isEmpty(configuration.getOperationName())) {
            return false;
        }
        if (isExcludedOperation(configuration.getOperationName())) {
            return true;
        }
        if (isIncludedOperation(configuration.getOperationName())) {
            return super.insert(configuration);
        }
        return true;
    }

    private boolean isIncludedOperation(String operationName) {
        return super.isIncluded(globalDefaultConfiguration.getIncludeOperationSet(), operationName);
    }

    private boolean isExcludedOperation(String operationName) {
        return super.isExcluded(globalDefaultConfiguration.getExcludeOperationSet(), operationName);
    }
}
