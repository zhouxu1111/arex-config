package com.arextest.config.web.api.service.controller;

import com.arextest.config.core.handler.ConfigurableHandler;
import com.arextest.config.core.repository.mapper.ApplicationServiceConfigurationMapper;
import com.arextest.config.model.application.ApplicationOperationConfiguration;
import com.arextest.config.model.replay.ComparisonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author jmo
 * @since 2022/1/22
 */
@Controller
@RequestMapping("/api/config/comparison")
public class ComparisonConfigurableController extends AbstractConfigurableController<ComparisonConfiguration> {
    @Resource
    private ApplicationServiceConfigurationMapper serviceConfigurationMapper;
    @Resource
    private ConfigurableHandler<ApplicationOperationConfiguration> operationConfigurableHandler;
    private String oldUrl = "http://replay.fat3.qa.nt.ctripcorp.com/api/queryCompareConfig";

    public ComparisonConfigurableController(@Autowired ConfigurableHandler<ComparisonConfiguration> configurableHandler) {
        super(configurableHandler);
    }
}
