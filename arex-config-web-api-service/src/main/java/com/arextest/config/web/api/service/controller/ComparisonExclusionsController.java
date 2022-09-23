package com.arextest.config.web.api.service.controller;

import com.arextest.config.core.handler.ConfigurableHandler;
import com.arextest.config.model.replay.ComparisonExclusionsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/config/comparison/exclusions")
public class ComparisonExclusionsController extends AbstractConfigurableController<ComparisonExclusionsConfiguration> {
    public ComparisonExclusionsController(
            @Autowired ConfigurableHandler<ComparisonExclusionsConfiguration> configurableHandler) {
        super(configurableHandler);
    }
}