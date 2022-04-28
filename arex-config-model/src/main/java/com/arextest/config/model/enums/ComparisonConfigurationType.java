package com.arextest.config.model.enums;

import lombok.Getter;

/**
 * @author jmo
 * @since 2022/1/21
 */
public enum ComparisonConfigurationType {

    /**
     * A path should be ignored to compare,eg: /user/birthday
     */
    IGNORE_PATH(0),
    /**
     * The name of any node should be ignored to compare,eg: transactionId,timestamp,sessionId
     */
    IGNORE_NODE_NAME(1),
    /**
     * Any dependent category should be ignored to compare,eg:SOA,EXTSOA,QMQ,DAL,REDIS
     * see MockCategoryType
     */
    IGNORE_CATEGORY(2),
    /**
     * The operation name should be ignored to compare,eg: name of db,name of service's operation
     */
    IGNORE_OPERATION_NAME(3),
    /**
     * A array should be used primary key instead of default index (from zero) to compare.
     */
    LIST_KEY(4),
    /**
     * The request or response exists ref relation,
     * which should be given the primary key and foreign key to find correct path to compare
     */
    REFERENCE_KEY(5),
    /**
     * The value of leaf's node should be decoded before to compare
     */
    NODE_VALUE_DECODED(6);
    @Getter
    private final int codeValue;

    ComparisonConfigurationType(int codeValue) {
        this.codeValue = codeValue;
    }
}
