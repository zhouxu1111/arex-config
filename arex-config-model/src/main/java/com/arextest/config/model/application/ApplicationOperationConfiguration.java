package com.arextest.config.model.application;

import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.ServiceOperationCollection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jmo
 * @see ServiceOperationCollection
 * @since 2021/12/22
 */
@Getter
@Setter
public class ApplicationOperationConfiguration extends AbstractConfiguration implements OperationDescription {
    private String id;
    private String appId;
    private String serviceId;
    private String operationName;
    private int operationType;
    private Integer recordedCaseCount;
}
