package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.convert.ComparisonReferenceConfigConvert;
import com.arextest.config.model.dao.mongodb.ComparisonReferenceCollection;
import com.arextest.config.model.replay.ComparisonReferenceConfiguration;
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
public class ComparisonReferenceConfigurationMapper implements RepositoryProvider<ComparisonReferenceConfiguration>,
        RepositoryField {

    private static final String APP_ID = "appId";
    private static final String PK_PATH = "pkPath";
    private static final String FK_PATH = "fkPath";
    private static final String EXPIRATION_TYPE = "expirationType";
    private static final String EXPIRATION_DATE = "expirationDate";


    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ComparisonReferenceConfiguration> list() {
        throw new UnsupportedOperationException("this method is not implemented");
    }

    @Override
    public List<ComparisonReferenceConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ComparisonReferenceCollection> comparisonReferenceCollections = mongoTemplate.find(query, ComparisonReferenceCollection.class);
        return comparisonReferenceCollections.stream().map(ComparisonReferenceConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(ComparisonReferenceConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getPkPath(), configuration.getFkPath(),
                configuration.getExpirationDate());
        update.set(PK_PATH, configuration.getPkPath());
        update.set(FK_PATH, configuration.getFkPath());
        update.set(EXPIRATION_TYPE, configuration.getExpirationType());
        update.set(EXPIRATION_DATE, configuration.getExpirationDate());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ComparisonReferenceCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ComparisonReferenceConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ComparisonReferenceCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ComparisonReferenceConfiguration configuration) {
        ComparisonReferenceCollection comparisonReferenceCollection = ComparisonReferenceConfigConvert.INSTANCE.daoFromDto(configuration);
        ComparisonReferenceCollection insert = mongoTemplate.insert(comparisonReferenceCollection);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }

    @Override
    public boolean insertList(List<ComparisonReferenceConfiguration> configurationList) {
        if (CollectionUtils.isEmpty(configurationList)) {
            return false;
        }
        List<ComparisonReferenceCollection> comparisonReferenceCollections = configurationList.stream()
                .map(ComparisonReferenceConfigConvert.INSTANCE::daoFromDto)
                .collect(Collectors.toList());
        Collection<ComparisonReferenceCollection> insertAll = mongoTemplate.insertAll(comparisonReferenceCollections);
        return CollectionUtils.isNotEmpty(insertAll);
    }
}