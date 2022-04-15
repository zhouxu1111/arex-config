package io.arex.config.model.application;

import io.arex.config.model.enums.ApplicationServiceOperationType;

/**
 * @author jmo
 * @since 2021/12/21
 */
public interface OperationDescription {
    String getOperationName();

    /**
     * {@link  ApplicationServiceOperationType}
     *
     * @return a code value
     */
    int getOperationType();
}
