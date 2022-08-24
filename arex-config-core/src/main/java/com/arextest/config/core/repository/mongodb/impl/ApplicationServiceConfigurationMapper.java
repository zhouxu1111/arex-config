package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.application.ApplicationServiceConfiguration;
import com.arextest.config.model.dao.mongodb.ServiceCollection;
import com.arextest.config.model.convert.ServiceConvert;
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
public class ApplicationServiceConfigurationMapper implements RepositoryProvider<ApplicationServiceConfiguration>,RepositoryField {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ApplicationOperationConfigurationMongoMapper applicationOperationConfigurationMongoMapper;

    @Override
    public List<ApplicationServiceConfiguration> list() {
        Query query = new Query();
        List<ServiceCollection> serviceCollections = mongoTemplate.find(query, ServiceCollection.class);
        List<ApplicationServiceConfiguration> applicationServiceConfigurations = serviceCollections.stream().map(ServiceConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
        applicationServiceConfigurations.forEach(item -> item.setOperationList(applicationOperationConfigurationMongoMapper.operationList(item.getId())));
        return applicationServiceConfigurations;
    }

    @Override
    public List<ApplicationServiceConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ServiceCollection> serviceCollections = mongoTemplate.find(query, ServiceCollection.class);
        List<ApplicationServiceConfiguration> applicationServiceConfigurations = serviceCollections.stream().map(ServiceConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
        applicationServiceConfigurations.forEach(item -> item.setOperationList(applicationOperationConfigurationMongoMapper.operationList(item.getId())));
        return applicationServiceConfigurations;
    }

    @Override
    public boolean update(ApplicationServiceConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getStatus());
        update.set(STATUS, configuration.getStatus());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ServiceCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ApplicationServiceConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ServiceCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ApplicationServiceConfiguration configuration) {
        ServiceCollection serviceCollection = ServiceConvert.INSTANCE.daoFromDto(configuration);
        ServiceCollection insert = mongoTemplate.insert(serviceCollection);
        if (insert.getId() != null){
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }

    @Override
    public long count(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        return mongoTemplate.count(query, ServiceCollection.class);
    }
}
