package io.arex.config.core.comparison;

import io.arex.config.core.handler.AbstractConfigurableHandler;
import io.arex.config.core.repository.RepositoryProvider;
import io.arex.config.model.enums.ExpirationType;
import io.arex.config.model.replay.ComparisonConfiguration;
import io.arex.config.model.replay.ComparisonDetailsConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jmo
 * @since 2022/1/22
 */
@Component
final class ComparisonConfigurableHandler extends AbstractConfigurableHandler<ComparisonConfiguration> {
    @Resource
    private RepositoryProvider<ComparisonDetailsConfiguration> detailsConfigurableHandler;

    protected ComparisonConfigurableHandler(@Autowired RepositoryProvider<ComparisonConfiguration> repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public List<ComparisonConfiguration> useResultAsList(String appId) {
        List<ComparisonConfiguration> comparisonList = repositoryProvider.listBy(appId);
        if (CollectionUtils.isNotEmpty(comparisonList)) {
            comparisonList.removeIf(this::removeExpired);
        }
        return comparisonList;
    }

    private boolean removeExpired(ComparisonConfiguration comparison) {
        List<ComparisonDetailsConfiguration> detailsList = comparison.getDetailsList();
        if (CollectionUtils.isNotEmpty(detailsList)) {
            detailsList.removeIf(this::removeDetailsExpired);
        }
        return CollectionUtils.isEmpty(detailsList);
    }

    private boolean removeDetailsExpired(ComparisonDetailsConfiguration comparisonDetails) {
        int expirationType = comparisonDetails.getExpirationType();
        if (expirationType == ExpirationType.ABSOLUTE_TIME_EXPIRED.getCodeValue()) {
            return comparisonDetails.getExpirationDate().getTime() < System.currentTimeMillis();
        }
        return expirationType != ExpirationType.PINNED_NEVER_EXPIRED.getCodeValue();
    }

    @Override
    public boolean insert(ComparisonConfiguration configuration) {
        List<ComparisonDetailsConfiguration> detailsList = configuration.getDetailsList();
        if (CollectionUtils.isEmpty(detailsList)) {
            return false;
        }
        if (StringUtils.isEmpty(configuration.getAppId())) {
            return false;
        }
        ComparisonConfiguration parent = findBy(configuration.getAppId(), configuration.getCategoryType());
        if (parent == null) {
            super.insert(configuration);
            parent = configuration;
        }
        for (ComparisonDetailsConfiguration detailsConfiguration : detailsList) {
            detailsConfiguration.setComparisonId(parent.getId());
            if (detailsConfiguration.getExpirationDate() == null) {
                detailsConfiguration.setExpirationDate(new Date());
            }
            detailsConfigurableHandler.insert(detailsConfiguration);
        }
        return true;
    }

    private ComparisonConfiguration findBy(String appId, int category) {
        List<ComparisonConfiguration> exists = this.editList(appId);
        if (CollectionUtils.isEmpty(exists)) {
            return null;
        }
        for (ComparisonConfiguration exist : exists) {
            if (exist.getCategoryType() == category) {
                return exist;
            }
        }
        return null;
    }

    @Override
    public boolean remove(ComparisonConfiguration configuration) {
        List<ComparisonDetailsConfiguration> detailsList = configuration.getDetailsList();
        if (CollectionUtils.isEmpty(detailsList)) {
            return super.remove(configuration);
        }
        for (ComparisonDetailsConfiguration detailsConfiguration : detailsList) {
            detailsConfigurableHandler.remove(detailsConfiguration);
        }
        return true;
    }

    @Override
    public boolean update(ComparisonConfiguration configuration) {
        List<ComparisonDetailsConfiguration> detailsList = configuration.getDetailsList();
        if (CollectionUtils.isEmpty(detailsList)) {
            return false;
        }
        for (ComparisonDetailsConfiguration detailsConfiguration : detailsList) {
            detailsConfigurableHandler.update(detailsConfiguration);
        }
        return true;
    }
}
