package com.arextest.config.model.application;

import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.AppCollection;
import com.arextest.config.model.enums.FeatureType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jmo
 * @see AppCollection
 * @since 2022/1/22
 */
@Getter
@Setter
public class ApplicationConfiguration extends AbstractConfiguration implements ApplicationDescription {
    private String appId;
    /**
     * Bit flag composed of bits that indicate which {@link FeatureType}s are enabled.
     */
    private int features;
    private String groupName;
    private String groupId;
    private String agentVersion;
    private String agentExtVersion;
    private String appName;
    private String description;

    /**
     * java_web_service
     * nodeJs_Web_service
     */
    private String category;
    private String owner;
    private String organizationName;
    private Integer recordedCaseCount;
    private String defaultFormatter;

    /**
     * organization_id
     */
    private String organizationId;
}
