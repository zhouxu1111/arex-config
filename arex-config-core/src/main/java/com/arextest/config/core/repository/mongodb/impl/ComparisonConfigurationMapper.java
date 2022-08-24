package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.model.dao.mongodb.ComparisonConfigCollection;
import com.arextest.config.model.convert.ComparisonConfigConvert;
import com.arextest.config.model.replay.ComparisonConfiguration;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ComparisonConfigurationMapper implements RepositoryProvider<ComparisonConfiguration>, RepositoryField{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ComparisonDetailsConfigurationMapper comparisonDetailsConfigurationMapper;

    @Override
    public List<ComparisonConfiguration> list() {
        Query query = new Query();
        List<ComparisonConfigCollection> comparisonConfigCollections = mongoTemplate.find(query, ComparisonConfigCollection.class);
        List<ComparisonConfiguration> comparisonConfigurations = comparisonConfigCollections.stream().map(ComparisonConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
        comparisonConfigurations.forEach(item -> item.setDetailsList(comparisonDetailsConfigurationMapper.listBy(item.getId())));
        return comparisonConfigurations;
    }

    @Override
    public List<ComparisonConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ComparisonConfigCollection> comparisonConfigCollections = mongoTemplate.find(query, ComparisonConfigCollection.class);
        List<ComparisonConfiguration> comparisonConfigurations = comparisonConfigCollections.stream().map(ComparisonConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
        comparisonConfigurations.forEach(item -> item.setDetailsList(comparisonDetailsConfigurationMapper.listBy(item.getId())));
        return comparisonConfigurations;
    }

    @Override
    public boolean update(ComparisonConfiguration configuration) {
        return false;
    }

    @Override
    public boolean remove(ComparisonConfiguration configuration) {
        Query query = Query.query(Criteria.where(DASH_ID).is(configuration.getId()));
        DeleteResult remove = mongoTemplate.remove(query, ComparisonConfigCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ComparisonConfiguration configuration) {
        ComparisonConfigCollection comparisonConfigCollection = ComparisonConfigConvert.INSTANCE.daoFromDto(configuration);
        ComparisonConfigCollection insert = mongoTemplate.insert(comparisonConfigCollection);
        if (insert.getId() != null) {
            configuration.setId(insert.getId());
        }
        return insert.getId() != null;
    }
}
