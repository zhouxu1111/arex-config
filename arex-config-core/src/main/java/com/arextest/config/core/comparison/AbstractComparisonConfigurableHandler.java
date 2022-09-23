package com.arextest.config.core.comparison;

import com.arextest.config.core.handler.AbstractConfigurableHandler;
import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.model.enums.ExpirationType;
import com.arextest.config.model.replay.AbstractComparisonDetailsConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jmo
 * @since 2022/1/22
 */
public abstract class AbstractComparisonConfigurableHandler<T extends AbstractComparisonDetailsConfiguration> extends AbstractConfigurableHandler<T> {

    protected AbstractComparisonConfigurableHandler(RepositoryProvider<T> repositoryProvider) {
        super(repositoryProvider);
    }

    @Override
    public List<T> useResultAsList(String appId) {
        List<T> comparisonDetails = repositoryProvider.listBy(appId);
        if (CollectionUtils.isNotEmpty(comparisonDetails)) {
            comparisonDetails.removeIf(this::removeDetailsExpired);
        }
        return comparisonDetails;
    }

    private boolean removeDetailsExpired(T comparisonDetails) {
        int expirationType = comparisonDetails.getExpirationType();
        if (expirationType == ExpirationType.ABSOLUTE_TIME_EXPIRED.getCodeValue()) {
            return comparisonDetails.getExpirationDate().getTime() < System.currentTimeMillis();
        }
        return expirationType != ExpirationType.PINNED_NEVER_EXPIRED.getCodeValue();
    }

    @Override
    public boolean insert(T comparisonDetail) {
        if (StringUtils.isEmpty(comparisonDetail.getAppId())) {
            return false;
        }

        if (comparisonDetail.getExpirationDate() == null) {
            comparisonDetail.setExpirationDate(new Date());
        }
        return repositoryProvider.insert(comparisonDetail);
    }

    @Override
    public boolean insertList(List<T> configurationList) {
        List<T> configurations = Optional.ofNullable(configurationList).orElse(new ArrayList<>()).stream()
                .filter(item -> item != null && StringUtils.isNotEmpty(item.getAppId()))
                .peek(item -> {
                    if (item.getExpirationDate() == null) {
                        item.setExpirationDate(new Date());
                    }
                }).collect(Collectors.toList());

        return repositoryProvider.insertList(configurations);
    }
}
