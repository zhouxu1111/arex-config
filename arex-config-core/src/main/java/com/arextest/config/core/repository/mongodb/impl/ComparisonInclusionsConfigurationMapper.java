package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.convert.ComparisonInclusionsConfigConvert;
import com.arextest.config.model.dao.mongodb.ComparisonInclusionsCollection;
import com.arextest.config.model.replay.ComparisonInclusionsConfiguration;
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

@Repository
public class ComparisonInclusionsConfigurationMapper implements RepositoryProvider<ComparisonInclusionsConfiguration>,
        RepositoryField {

    private static final String APP_ID = "appId";
    private static final String INCLUSIONS = "inclusions";
    private static final String EXPIRATION_TYPE = "expirationType";
    private static final String EXPIRATION_DATE = "expirationDate";


    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ComparisonInclusionsConfiguration> list() {
        throw new UnsupportedOperationException("this method is not implemented");
    }

    @Override
    public List<ComparisonInclusionsConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ComparisonInclusionsCollection> comparisonInclusionsCollections = mongoTemplate.find(query, ComparisonInclusionsCollection.class);
        return comparisonInclusionsCollections.stream().map(ComparisonInclusionsConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(ComparisonInclusionsConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getInclusions(),
                configuration.getExpirationDate());
        update.set(INCLUSIONS, configuration.getInclusions());
        update.set(EXPIRATION_TYPE, configuration.getExpirationType());
        update.set(EXPIRATION_DATE, configuration.getExpirationDate());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ComparisonInclusionsCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ComparisonInclusionsConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ComparisonInclusionsCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ComparisonInclusionsConfiguration configuration) {
        ComparisonInclusionsCollection comparisonInclusionsCollection = ComparisonInclusionsConfigConvert.INSTANCE.daoFromDto(configuration);
        ComparisonInclusionsCollection insert = mongoTemplate.insert(comparisonInclusionsCollection);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }

    @Override
    public boolean insertList(List<ComparisonInclusionsConfiguration> configurationList) {
        if (CollectionUtils.isEmpty(configurationList)) {
            return false;
        }
        List<ComparisonInclusionsCollection> comparisonInclusionsCollections = configurationList.stream()
                .map(ComparisonInclusionsConfigConvert.INSTANCE::daoFromDto)
                .collect(Collectors.toList());
        Collection<ComparisonInclusionsCollection> insertAll = mongoTemplate.insertAll(comparisonInclusionsCollections);
        return CollectionUtils.isNotEmpty(insertAll);
    }
}