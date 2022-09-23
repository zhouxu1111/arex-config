package com.arextest.config.web.api.service.controller;

import com.arextest.config.core.handler.ConfigurableHandler;
import com.arextest.config.model.replay.ComparisonInclusionsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/config/comparison/inclusions")
public class ComparisonInclusionsController extends AbstractConfigurableController<ComparisonInclusionsConfiguration> {
    public ComparisonInclusionsController(
            @Autowired ConfigurableHandler<ComparisonInclusionsConfiguration> configurableHandler) {
        super(configurableHandler);
    }
}
