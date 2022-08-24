package com.arextest.config.model.convert;

import com.arextest.config.model.dao.mongodb.RecordServiceConfigCollection;
import com.arextest.config.model.record.ServiceCollectConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface RecordServiceConfigConvert {

    RecordServiceConfigConvert INSTANCE = Mappers.getMapper(RecordServiceConfigConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ServiceCollectConfiguration dtoFromDao(RecordServiceConfigCollection dao);

    @Mappings({
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    RecordServiceConfigCollection daoFromDto(ServiceCollectConfiguration dto);

}
