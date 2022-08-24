package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.application.ApplicationOperationConfiguration;
import com.arextest.config.model.dao.mongodb.ServiceOperationCollection;
import com.arextest.config.model.convert.ServiceOperationConvert;
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
public class ApplicationOperationConfigurationMongoMapper implements RepositoryProvider<ApplicationOperationConfiguration>, RepositoryField {

    private static final String RECORDED_CASE_COUNT = "recordedCaseCount";
    private static final String SERVICE_ID = "serviceId";

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public List<ApplicationOperationConfiguration> list() {
        Query query = new Query();
        List<ServiceOperationCollection> serviceOperationCollections = mongoTemplate.find(query, ServiceOperationCollection.class);
        return serviceOperationCollections.stream().map(ServiceOperationConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public List<ApplicationOperationConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ServiceOperationCollection> serviceOperationCollections = mongoTemplate.find(query, ServiceOperationCollection.class);
        return serviceOperationCollections.stream().map(ServiceOperationConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(ApplicationOperationConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getStatus(),
                configuration.getRecordedCaseCount());
        update.set(STATUS, configuration.getStatus());
        update.set(RECORDED_CASE_COUNT, configuration.getRecordedCaseCount());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ServiceOperationCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ApplicationOperationConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ServiceOperationCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ApplicationOperationConfiguration configuration) {
        ServiceOperationCollection serviceOperationCollection = ServiceOperationConvert.INSTANCE.daoFromDto(configuration);
        ServiceOperationCollection insert = mongoTemplate.insert(serviceOperationCollection);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }

    public List<ApplicationOperationConfiguration> operationList(String serviceId) {
        Query query = Query.query(Criteria.where(SERVICE_ID).is(serviceId));
        List<ServiceOperationCollection> serviceOperationCollections = mongoTemplate.find(query, ServiceOperationCollection.class);
        return serviceOperationCollections.stream().map(ServiceOperationConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }
}
