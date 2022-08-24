package com.arextest.config.model.convert;

import com.arextest.config.model.dao.mongodb.ReplayScheduleConfigCollection;
import com.arextest.config.model.replay.ScheduleConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ReplayScheduleConfigConvert {
    ReplayScheduleConfigConvert INSTANCE = Mappers.getMapper(ReplayScheduleConfigConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ScheduleConfiguration dtoFromDao(ReplayScheduleConfigCollection dao);

    @Mappings({
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    ReplayScheduleConfigCollection daoFromDto(ScheduleConfiguration dto);
}
