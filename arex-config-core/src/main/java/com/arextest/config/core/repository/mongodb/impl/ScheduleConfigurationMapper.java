package com.arextest.config.core.repository.mongodb.impl;

import com.arextest.config.core.repository.RepositoryProvider;
import com.arextest.config.core.repository.mongodb.impl.util.MongoHelper;
import com.arextest.config.model.dao.mongodb.ReplayScheduleConfigCollection;
import com.arextest.config.model.convert.ReplayScheduleConfigConvert;
import com.arextest.config.model.replay.ScheduleConfiguration;
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
public class ScheduleConfigurationMapper implements RepositoryProvider<ScheduleConfiguration>, RepositoryField {

    private static final String TARGET_ENV = "targetEnv";
    private static final String SEND_MAX_QPS = "sendMaxQps";
    private static final String OFFSET_DAYS = "offsetDays";


    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ScheduleConfiguration> list() {
        Query query = new Query();
        List<ReplayScheduleConfigCollection> replayScheduleConfigCollections = mongoTemplate.find(query, ReplayScheduleConfigCollection.class);
        return replayScheduleConfigCollections.stream().map(ReplayScheduleConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleConfiguration> listBy(String appId) {
        Query query = Query.query(Criteria.where(APP_ID).is(appId));
        List<ReplayScheduleConfigCollection> replayScheduleConfigCollections = mongoTemplate.find(query, ReplayScheduleConfigCollection.class);
        return replayScheduleConfigCollections.stream().map(ReplayScheduleConfigConvert.INSTANCE::dtoFromDao).collect(Collectors.toList());
    }

    @Override
    public boolean update(ScheduleConfiguration configuration) {
        Query query = Query.query(Criteria.where(APP_ID).is(configuration.getAppId()));
        Update update = MongoHelper.getUpdate();
        MongoHelper.assertNull("update parameter is null", configuration.getTargetEnv(),
                configuration.getSendMaxQps(), configuration.getOffsetDays());
        update.set(TARGET_ENV, configuration.getTargetEnv());
        update.set(SEND_MAX_QPS, configuration.getSendMaxQps());
        update.set(OFFSET_DAYS, configuration.getOffsetDays());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, ReplayScheduleConfigCollection.class);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean remove(ScheduleConfiguration configuration) {
        Query query = Query.query(Criteria.where(APP_ID).is(configuration.getAppId()));
        DeleteResult remove = mongoTemplate.remove(query, ReplayScheduleConfigCollection.class);
        return remove.getDeletedCount() > 0;
    }

    @Override
    public boolean insert(ScheduleConfiguration configuration) {
        ReplayScheduleConfigCollection replayScheduleConfigCollection = ReplayScheduleConfigConvert.INSTANCE.daoFromDto(configuration);
        ReplayScheduleConfigCollection insert = mongoTemplate.insert(replayScheduleConfigCollection);
        return insert.getId() != null;
    }
}
