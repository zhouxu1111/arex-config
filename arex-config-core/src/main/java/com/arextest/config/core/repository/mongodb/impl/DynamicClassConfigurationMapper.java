package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.dao.mongodb.DynamicClassCollection;
import com.arextest.config.model.convert.DynamicClassConvert;
import com.arextest.config.model.record.DynamicClassConfiguration;
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
public class DynamicClassConfigurationMapper implements RepositoryProvider<DynamicClassConfiguration>, RepositoryField {

    private static final String FULL_CLASS_NAME = "fullClassName";
    private static final String METHOD_NAME = "methodName";
    private static final String PARAMETER_TYPES = "parameterTypes";
    private static final String KEY_FORMULA = "keyFormula";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<DynamicClassConfiguration> list() {
        Query query = new Query();
        List<DynamicClassCollection> dynamicClassCollections = mongoTemplate.find(query, DynamicClassCollection.class);
        return dynamicClassCollections.stream().map(DynamicClassConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public List<DynamicClassConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<DynamicClassCollection> dynamicClassCollections = mongoTemplate.find(query, DynamicClassCollection.class);
        return dynamicClassCollections.stream().map(DynamicClassConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(DynamicClassConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        update.set(FULL_CLASS_NAME, configuration.getFullClassName());
        update.set(METHOD_NAME, configuration.getMethodName());
        update.set(PARAMETER_TYPES, configuration.getParameterTypes());
        update.set(KEY_FORMULA, configuration.getKeyFormula());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, DynamicClassCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(DynamicClassConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, DynamicClassCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(DynamicClassConfiguration configuration) {
        DynamicClassCollection dynamicClassConfiguration = DynamicClassConvert.INSTANCE.daoFromDto(configuration);
        DynamicClassCollection insert = mongoTemplate.insert(dynamicClassConfiguration);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }
}
