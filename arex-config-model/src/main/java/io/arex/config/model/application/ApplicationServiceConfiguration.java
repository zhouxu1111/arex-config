package io.arex.config.model.application;

import io.arex.config.model.AbstractConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author jmo
 * @since 2021/12/22
 */
@Getter
@Setter
public class ApplicationServiceConfiguration extends AbstractConfiguration implements ServiceDescription {
    private Long id;
    private String appId;
    private String serviceName;
    private String serviceKey;
    private List<ApplicationOperationConfiguration> operationList;
}