package com.arextest.config.web.api.service.controller;

import com.arextest.config.core.handler.ConfigurableHandler;
import com.arextest.config.model.replay.ComparisonListSortConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rchen9 on 2022/9/16.
 */
@Controller
@RequestMapping("/api/config/comparison/listsort")
public class ComparisonListSortController extends AbstractConfigurableController<ComparisonListSortConfiguration> {
    public ComparisonListSortController(
            @Autowired ConfigurableHandler<ComparisonListSortConfiguration> configurableHandler) {
        super(configurableHandler);
    }
}
