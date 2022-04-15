package io.arex.config.core.application;

import io.arex.config.core.handler.AbstractConfigurableHandler;
import io.arex.config.core.repository.RepositoryProvider;
import io.arex.config.model.application.ApplicationOperationConfiguration;
import io.arex.config.model.application.ApplicationServiceConfiguration;
import io.arex.config.model.application.OperationDescription;
import io.arex.config.model.application.ServiceDescription;
import io.arex.config.model.application.provider.ApplicationServiceDescriptionProvider;
import io.arex.config.model.enums.StatusType;
import io.arex.config.model.record.ServiceCollectConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jmo
 * @since 2022/1/23
 */
@Slf4j
@Component
public final class ApplicationServiceConfigurableHandler extends AbstractConfigurableHandler<ApplicationServiceConfiguration> {
    @Resource
    private ApplicationServiceDescriptionProvider applicationServiceProvider;
    @Resource
    private AbstractConfigurableHandler<ApplicationOperationConfiguration> operationConfigurableHandler;
    @Resource
    private ServiceCollectConfiguration globalDefaultConfiguration;

    protected ApplicationServiceConfigurableHandler(@Autowired RepositoryProvider<ApplicationServiceConfiguration> repositoryProvider) {
        super(repositoryProvider);
    }

    public void createOrUpdate(String appId, String env) {
        if (this.repositoryProvider.count(appId) != 0) {
            LOGGER.info("skip create serviceList when exists by appId:{},env:{}", appId, env);
            return;
        }
        List<? extends ServiceDescription> originServiceList = applicationServiceProvider.get(appId, env);
        if (CollectionUtils.isEmpty(originServiceList)) {
            LOGGER.info("skip empty originServiceList from appId:{},env:{}", appId, env);
            return;
        }
        this.create(originServiceList);
    }

    private void create(List<? extends ServiceDescription> originServiceList) {
        ApplicationServiceConfiguration serviceConfiguration;
        List<? extends OperationDescription> sourceOperationList;
        for (ServiceDescription originService : originServiceList) {
            if (!isIncludedService(originService.getServiceName())) {
                continue;
            }
            serviceConfiguration = new ApplicationServiceConfiguration();
            serviceConfiguration.setAppId(originService.getAppId());
            serviceConfiguration.setServiceKey(originService.getServiceKey());
            serviceConfiguration.setServiceName(originService.getServiceName());
            serviceConfiguration.setStatus(StatusType.NORMAL.getMask());
            sourceOperationList = originService.getOperationList();
            if (super.insert(serviceConfiguration) && CollectionUtils.isNotEmpty(sourceOperationList)) {
                this.buildOperationList(serviceConfiguration, sourceOperationList);
                operationConfigurableHandler.insertList(serviceConfiguration.getOperationList());
                LOGGER.info("add {} service's operations size:{}", originService.getServiceName(),
                        sourceOperationList.size());
            }
        }
    }

    @Override
    public boolean insert(ApplicationServiceConfiguration configuration) {
        if (StringUtils.isEmpty(configuration.getServiceName())) {
            return false;
        }
        if (StringUtils.isEmpty(configuration.getServiceKey())) {
            return false;
        }
        if (isIncludedService(configuration.getServiceName())) {
            return super.insert(configuration);
        }
        return true;
    }

    private void buildOperationList(ApplicationServiceConfiguration service,
                                    List<? extends OperationDescription> source) {
        List<ApplicationOperationConfiguration> operationList = new ArrayList<>(source.size());
        ApplicationOperationConfiguration operationConfiguration;
        String operationName;
        for (OperationDescription operationDescription : source) {
            operationName = operationDescription.getOperationName();
            operationConfiguration = new ApplicationOperationConfiguration();
            operationConfiguration.setOperationName(operationName);
            operationConfiguration.setOperationType(operationDescription.getOperationType());
            operationConfiguration.setServiceId(service.getId());
            operationConfiguration.setAppId(service.getAppId());
            operationConfiguration.setStatus(StatusType.NORMAL.getMask());
            operationConfiguration.setRecordedCaseCount(0);
            operationList.add(operationConfiguration);
        }
        service.setOperationList(operationList);
    }

    private boolean isIncludedService(String serviceName) {
        return isIncluded(globalDefaultConfiguration.getIncludeServiceSet(), serviceName);
    }
}
