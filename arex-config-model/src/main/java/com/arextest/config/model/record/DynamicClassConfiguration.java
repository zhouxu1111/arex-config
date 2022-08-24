package com.arextest.config.model.record;

import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.DynamicClassCollection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jmo
 * @see DynamicClassCollection
 * @since 2021/12/22
 */
@Getter
@Setter
public class DynamicClassConfiguration extends AbstractConfiguration {
    private String id;
    private String appId;
    private String fullClassName;
    private String methodName;
    private String parameterTypes;
    private String keyFormula;

    /**
     * from system provide or user custom provide
     */
    private int configType;

}
