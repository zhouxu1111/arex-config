package com.arextest.config.web.api.service.controller;

import com.arextest.common.model.response.Response;
import com.arextest.common.utils.ResponseUtils;
import com.arextest.config.core.application.ApplicationServiceConfigurableHandler;
import com.arextest.config.core.handler.ConfigurableHandler;
import com.arextest.config.model.application.ApplicationConfiguration;
import com.arextest.config.model.enums.StatusType;
import com.arextest.config.model.record.DynamicClassConfiguration;
import com.arextest.config.model.record.ServiceCollectConfiguration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author jmo
 * @since 2022/1/22
 */
@Slf4j
@Controller
@RequestMapping("/api/config/agent")
public final class AgentRemoteConfigurationController {
    @Resource
    private ConfigurableHandler<DynamicClassConfiguration> dynamicClassHandler;
    @Resource
    private ConfigurableHandler<ApplicationConfiguration> applicationHandler;
    @Resource
    private ConfigurableHandler<ServiceCollectConfiguration> serviceCollectHandler;
    @Resource
    private ApplicationServiceConfigurableHandler applicationServiceHandler;
    private ScheduledExecutorService executorService;
    @Value("${arex.config.application.service.update.delaySeconds:30}")
    private long delayUpdateServiceSeconds;

    @PostConstruct
    private void init() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PostMapping("/load")
    @ResponseBody
    public Response load(@RequestBody AgentRemoteConfigurationRequest request) {
        final String appId = request.getAppId();
        if (StringUtils.isEmpty(appId)) {
            return InvalidResponse.REQUESTED_APP_ID_IS_EMPTY;
        }
        String host = request.getHost();
        if (StringUtils.isEmpty(host)) {
            return InvalidResponse.REQUESTED_IP_IS_EMPTY;
        }
        LOGGER.info("from appId: {} ,remote Ip: {} load config", request.appId, host);
        ApplicationConfiguration applicationConfiguration = this.loadApplicationResult(request);
        if (applicationConfiguration == null) {
            LOGGER.info("from appId: {} ,remote Ip: {} load config resource not found", request.appId, host);
            return ResponseUtils.resourceNotFoundResponse();
        }
        ServiceCollectConfiguration serviceCollectConfiguration = serviceCollectHandler.useResult(appId);
        applicationServiceHandler.createOrUpdate(request.getAppId(), request.getHost());
        AgentRemoteConfigurationResponse body = new AgentRemoteConfigurationResponse();
        body.setDynamicClassConfigurationList(dynamicClassHandler.useResultAsList(appId));
        body.setServiceCollectConfiguration(serviceCollectConfiguration);
        body.setStatus(applicationConfiguration.getStatus());
        return ResponseUtils.successResponse(body);
    }

    private void delayUpdateApplicationService(AgentRemoteConfigurationRequest request) {
        executorService.schedule(new UpdateApplicationRunnable(request), delayUpdateServiceSeconds, TimeUnit.SECONDS);
    }

    private final class UpdateApplicationRunnable implements Runnable {
        private final AgentRemoteConfigurationRequest request;

        private UpdateApplicationRunnable(AgentRemoteConfigurationRequest request) {
            this.request = request;
        }

        @Override
        public void run() {
            try {
                applicationServiceHandler.createOrUpdate(request.getAppId(), request.getHost());
            } catch (Throwable e) {
                LOGGER.error("update application service error:{}", e.getMessage(), e);
            }
        }
    }

    private ApplicationConfiguration loadApplicationResult(AgentRemoteConfigurationRequest request) {
        ApplicationConfiguration applicationConfiguration = applicationHandler.useResult(request.getAppId());
        if (applicationConfiguration == null) {
            return null;
        }
        boolean changed = false;
        String newVersion = request.getCoreVersion();
        if (newVersionRequested(newVersion, applicationConfiguration.getAgentVersion())) {
            applicationConfiguration.setAgentVersion(newVersion);
            changed = true;
        }
        newVersion = request.getAgentExtVersion();
        if (newVersionRequested(newVersion, applicationConfiguration.getAgentExtVersion())) {
            applicationConfiguration.setAgentExtVersion(newVersion);
            changed = true;
        }
        if (changed) {
            applicationHandler.update(applicationConfiguration);
        }
        return applicationConfiguration;
    }

    private boolean newVersionRequested(String newVersion, String prevVersion) {
        if (StringUtils.isEmpty(newVersion)) {
            return false;
        }
        return !StringUtils.equals(newVersion, prevVersion);
    }

    @Data
    private static final class AgentRemoteConfigurationRequest {
        private String appId;
        private String agentExtVersion;
        private String coreVersion;
        private String host;
    }

    @Data
    private static final class AgentRemoteConfigurationResponse {
        private ServiceCollectConfiguration serviceCollectConfiguration;
        private List<DynamicClassConfiguration> dynamicClassConfigurationList;

        /**
         * Bit flag composed of bits that record/replay are enabled.
         * see {@link  StatusType }
         */
        private Integer status;
    }
}
