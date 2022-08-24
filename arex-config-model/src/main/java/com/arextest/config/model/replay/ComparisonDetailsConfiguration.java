package com.arextest.config.model.replay;


import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.ComparisonDetailsConfigCollection;
import com.arextest.config.model.enums.ExpirationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author jmo
 * @see ComparisonDetailsConfigCollection
 * @since 2022/1/21
 */
@Getter
@Setter
public class ComparisonDetailsConfiguration extends AbstractConfiguration {
    // id
    private String id;
    /**
     * the value from {@link ExpirationType} indicate which type should be used.
     */
    // expiration_type
    private int expirationType;
    // expiration_date
    private Date expirationDate;
    // path_name
    private String pathName;
    // path_value
    private List<String> pathValue;

    /**
     * The value ref from {@link ComparisonConfiguration}'s id
     */
    // comparsion_id
    private String comparisonId;

    /**
     * The decode type , eg:gzip,zstd,
     */
    // decode_type
    private String decodeType;
    /**
     * the prefix of value should be skipped before to decode
     */
    // decode_prefix
    private String decodePrefix;
    /**
     * the decode secretKey
     */
    // decode_secret
    private String decodeSecretKey;
}
