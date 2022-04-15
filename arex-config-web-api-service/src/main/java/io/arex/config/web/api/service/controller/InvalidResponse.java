package io.arex.config.web.api.service.controller;

import io.arex.common.model.response.Response;
import io.arex.common.utils.ResponseUtils;

/**
 * @author jmo
 * @since 2021/12/21
 */
final class InvalidResponse {
    private InvalidResponse() {
    }

    static final Response REQUESTED_APP_ID_IS_EMPTY = ResponseUtils.parameterInvalidResponse("The requested " +
            "appId is empty");
    static final Response REQUESTED_IP_IS_EMPTY = ResponseUtils.parameterInvalidResponse("The requested " +
            "Ip is empty");
}
