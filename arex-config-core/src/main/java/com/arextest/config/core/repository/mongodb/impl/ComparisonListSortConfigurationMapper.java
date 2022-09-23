package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.convert.ComparisonListSortConfigConvert;
import com.arextest.config.model.dao.mongodb.ComparisonListSortCollection;
import com.arextest.config.model.replay.ComparisonListSortConfiguration;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rchen9 on 2022/9/16.
 */
@Repository
public class ComparisonListSortConfigurationMapper implements RepositoryProvider<ComparisonListSortConfiguration>,
        RepositoryField {

    private static final String APP_ID = "appId";
    private static final String LIST_PATH = "listPath";
    private static final String KEYS = "keys";
    private static final String EXPIRATION_TYPE = "expirationType";
    private static final String EXPIRATION_DATE = "expirationDate";


    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ComparisonListSortConfiguration> list() {
        throw new UnsupportedOperationException("this method is not implemented");
    }

    @Override
    public List<ComparisonListSortConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ComparisonListSortCollection> comparisonListSortCollections = mongoTemplate.find(query, ComparisonListSortCollection.class);
        return comparisonListSortCollections.stream().map(ComparisonListSortConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(ComparisonListSortConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getListPath(), configuration.getKeys(),
                configuration.getExpirationDate());
        update.set(LIST_PATH, configuration.getListPath());
        update.set(KEYS, configuration.getKeys());
        update.set(EXPIRATION_TYPE, configuration.getExpirationType());
        update.set(EXPIRATION_DATE, configuration.getExpirationDate());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ComparisonListSortCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ComparisonListSortConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ComparisonListSortCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ComparisonListSortConfiguration configuration) {
        ComparisonListSortCollection comparisonListSortCollection = ComparisonListSortConfigConvert.INSTANCE.daoFromDto(configuration);
        ComparisonListSortCollection insert = mongoTemplate.insert(comparisonListSortCollection);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }

    @Override
    public boolean insertList(List<ComparisonListSortConfiguration> configurationList) {
        if (CollectionUtils.isEmpty(configurationList)) {
            return false;
        }
        List<ComparisonListSortCollection> comparisonListSortCollections = configurationList.stream()
                .map(ComparisonListSortConfigConvert.INSTANCE::daoFromDto)
                .collect(Collectors.toList());
        Collection<ComparisonListSortCollection> insertAll = mongoTemplate.insertAll(comparisonListSortCollections);
        return CollectionUtils.isNotEmpty(insertAll);
    }
}