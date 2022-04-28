package com.arextest.config.web.api.service.controller;

import com.arextest.config.core.handler.ConfigurableHandler;
import com.arextest.config.model.application.ApplicationServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jmo
 * @since 2022/1/22
 */
@Controller
@RequestMapping("/api/config/applicationService")
public final class ApplicationServiceConfigurableController extends AbstractConfigurableController<ApplicationServiceConfiguration> {
    public ApplicationServiceConfigurableController(@Autowired ConfigurableHandler<ApplicationServiceConfiguration> configurableHandler) {
        super(configurableHandler);
    }
}
