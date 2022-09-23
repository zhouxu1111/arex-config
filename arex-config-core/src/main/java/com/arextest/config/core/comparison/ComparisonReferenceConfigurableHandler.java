package com.arextest.config.core.comparison;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.model.replay.ComparisonReferenceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by rchen9 on 2022/9/16.
 */
@Component
public class ComparisonReferenceConfigurableHandler extends AbstractComparisonConfigurableHandler<ComparisonReferenceConfiguration> {
    protected ComparisonReferenceConfigurableHandler(@Autowired RepositoryProvider<ComparisonReferenceConfiguration> repositoryProvider) {
        super(repositoryProvider);
    }
}
