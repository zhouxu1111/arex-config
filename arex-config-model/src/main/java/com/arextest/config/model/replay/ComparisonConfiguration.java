package com.arextest.config.model.replay;

import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.ComparisonConfigCollection;
import com.arextest.config.model.enums.ComparisonConfigurationType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author jmo
 * @see ComparisonConfigCollection
 * @since 2022/1/21
 */
@Getter
@Setter
public class ComparisonConfiguration extends AbstractConfiguration {
    private String id;
    /**
     * optional
     * if empty should be apply to all appIds replay compare,means global default
     */
    private String appId;

    /**
     * optional
     * The value limit to special operation should be used, else,couldn't apply for it.
     * empty,means is unlimited.
     */
    private String operationId;
    /**
     * required
     * the value from {@link ComparisonConfigurationType} indicate which type should be used.
     */
    private int categoryType;
    private List<ComparisonDetailsConfiguration> detailsList;
}
