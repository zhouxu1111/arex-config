package com.arextest.config.model.application;

import com.arextest.config.model.AbstractConfiguration;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jmo
 * @since 2021/12/22
 */
@Getter
@Setter
public class ApplicationOperationConfiguration extends AbstractConfiguration implements OperationDescription {
    private Long id;
    private String appId;
    private Long serviceId;
    private String operationName;
    private int operationType;
    private Integer recordedCaseCount;
}
