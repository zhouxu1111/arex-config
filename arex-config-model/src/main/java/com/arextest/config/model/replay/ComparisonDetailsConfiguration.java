package com.arextest.config.model.replay;


import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.enums.ExpirationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author jmo
 * @since 2022/1/21
 */
@Getter
@Setter
public class ComparisonDetailsConfiguration extends AbstractConfiguration {
    private long id;
    /**
     * the value from {@link ExpirationType} indicate which type should be used.
     */
    private int expirationType;
    private Date expirationDate;
    private String pathName;
    private List<String> pathValue;

    /**
     * The value ref from {@link ComparisonConfiguration}'s id
     */
    private long comparisonId;

    /**
     * The decode type , eg:gzip,zstd,
     */
    private String decodeType;
    /**
     * the prefix of value should be skipped before to decode
     */
    private String decodePrefix;
    /**
     * the decode secretKey
     */
    private String decodeSecretKey;
}
