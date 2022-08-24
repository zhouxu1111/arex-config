package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.dao.mongodb.ComparisonDetailsConfigCollection;
import com.arextest.config.model.convert.ComparisonDetailsConfigConvert;
import com.arextest.config.model.replay.ComparisonDetailsConfiguration;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ComparisonDetailsConfigurationMapper implements RepositoryProvider<ComparisonDetailsConfiguration>, RepositoryField {

    private static final String COMPARISON_ID = "comparisonId";
    private static final String PATH_NAME = "pathName";
    private static final String PATH_VALUE = "pathValue";
    private static final String EXPIRATION_TYPE = "expirationType";
    private static final String EXPIRATION_DATE = "expirationDate";
    private static final String DECODE_TYPE = "decodeType";
    private static final String DECODE_PREFIX = "decodePrefix";
    private static final String DECODE_SECRET_KEY = "decodeSecretKey";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ComparisonDetailsConfiguration> list() {
        Query query = new Query();
        List<ComparisonDetailsConfigCollection> ComparisonDetailsConfigCollections = mongoTemplate.find(query, ComparisonDetailsConfigCollection.class);
        return ComparisonDetailsConfigCollections.stream().map(ComparisonDetailsConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public List<ComparisonDetailsConfiguration> listBy(String comparisonId) {
        Query query = Query.query(Criteria.where(COMPARISON_ID).is(comparisonId));
        List<ComparisonDetailsConfigCollection> comparisonDetailsConfigCollections = mongoTemplate.find(query, ComparisonDetailsConfigCollection.class);
        return comparisonDetailsConfigCollections.stream().map(ComparisonDetailsConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(ComparisonDetailsConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getPathName(), configuration.getPathValue(),
                configuration.getExpirationDate());
        update.set(PATH_NAME, configuration.getPathName());
        update.set(PATH_VALUE, configuration.getPathValue());
        update.set(EXPIRATION_TYPE, configuration.getExpirationType());
        update.set(EXPIRATION_DATE, configuration.getExpirationDate());
        update.set(DECODE_TYPE, configuration.getDecodeType());
        update.set(DECODE_PREFIX, configuration.getDecodePrefix());
        update.set(DECODE_SECRET_KEY, configuration.getDecodeSecretKey());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ComparisonDetailsConfigCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ComparisonDetailsConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ComparisonDetailsConfigCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ComparisonDetailsConfiguration configuration) {
        ComparisonDetailsConfigCollection comparisonDetailsConfigCollection = ComparisonDetailsConfigConvert.INSTANCE.daoFromDto(configuration);
        ComparisonDetailsConfigCollection insert = mongoTemplate.insert(comparisonDetailsConfigCollection);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }
}
