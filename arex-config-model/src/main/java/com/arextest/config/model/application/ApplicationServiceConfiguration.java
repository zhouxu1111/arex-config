package com.arextest.config.model.application;

import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.ServiceCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author jmo
 * @see ServiceCollection
 * @since 2021/12/22
 */
@Getter
@Setter
public class ApplicationServiceConfiguration extends AbstractConfiguration implements ServiceDescription {
    private String id;
    private String appId;
    private String serviceName;
    private String serviceKey;
    private List<ApplicationOperationConfiguration> operationList;
}