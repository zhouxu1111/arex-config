package io.arex.config.web.api.service.controller;

import io.arex.common.model.response.Response;
import io.arex.common.utils.ResponseUtils;
import io.arex.config.core.handler.ViewHandler;
import io.arex.config.model.dashboard.AppDashboardView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @author jmo
 * @since 2022/1/24
 */
@Controller
@RequestMapping("/api/config/dashboard")
public class AppNumbersDashboardController {
    @Resource
    private ViewHandler<AppDashboardView> numbersDashboardViewHandler;

    @GetMapping("/")
    @ResponseBody
    public final Response list() {
        return ResponseUtils.successResponse(this.numbersDashboardViewHandler.useResultAsList());
    }

    @GetMapping("/appId/{appId}")
    @ResponseBody
    public final Response list(@PathVariable String appId) {
        if (StringUtils.isEmpty(appId)) {
            return InvalidResponse.REQUESTED_APP_ID_IS_EMPTY;
        }
        return ResponseUtils.successResponse(this.numbersDashboardViewHandler.useResult(appId));
    }
}
