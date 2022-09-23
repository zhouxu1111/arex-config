package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.convert.ComparisonExclusionsConfigConvert;
import com.arextest.config.model.dao.mongodb.ComparisonExclusionsCollection;
import com.arextest.config.model.replay.ComparisonExclusionsConfiguration;
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
public class ComparisonExclusionsConfigurationMapper implements RepositoryProvider<ComparisonExclusionsConfiguration>,
        RepositoryField {

    private static final String APP_ID = "appId";
    private static final String EXCLUSIONS = "exclusions";
    private static final String EXPIRATION_TYPE = "expirationType";
    private static final String EXPIRATION_DATE = "expirationDate";


    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ComparisonExclusionsConfiguration> list() {
        throw new UnsupportedOperationException("this method is not implemented");
    }

    @Override
    public List<ComparisonExclusionsConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ComparisonExclusionsCollection> comparisonExclusionsCollections = mongoTemplate.find(query, ComparisonExclusionsCollection.class);
        return comparisonExclusionsCollections.stream().map(ComparisonExclusionsConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(ComparisonExclusionsConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getExclusions(),
                configuration.getExpirationDate());
        update.set(EXCLUSIONS, configuration.getExclusions());
        update.set(EXPIRATION_TYPE, configuration.getExpirationType());
        update.set(EXPIRATION_DATE, configuration.getExpirationDate());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ComparisonExclusionsCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ComparisonExclusionsConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ComparisonExclusionsCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ComparisonExclusionsConfiguration configuration) {
        ComparisonExclusionsCollection comparisonExclusionsCollection = ComparisonExclusionsConfigConvert.INSTANCE.daoFromDto(configuration);
        ComparisonExclusionsCollection insert = mongoTemplate.insert(comparisonExclusionsCollection);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }

    @Override
    public boolean insertList(List<ComparisonExclusionsConfiguration> configurationList) {
        if (CollectionUtils.isEmpty(configurationList)) {
            return false;
        }
        List<ComparisonExclusionsCollection> comparisonExclusionsConfigurations = configurationList.stream()
                .map(ComparisonExclusionsConfigConvert.INSTANCE::daoFromDto)
                .collect(Collectors.toList());
        Collection<ComparisonExclusionsCollection> insertAll = mongoTemplate.insertAll(comparisonExclusionsConfigurations);
        return CollectionUtils.isNotEmpty(insertAll);
    }
}