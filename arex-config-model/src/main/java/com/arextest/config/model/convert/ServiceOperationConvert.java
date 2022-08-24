package com.arextest.config.model.convert;

import com.arextest.config.model.application.ApplicationOperationConfiguration;
import com.arextest.config.model.dao.mongodb.ServiceOperationCollection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceOperationConvert {
    ServiceOperationConvert INSTANCE = Mappers.getMapper(ServiceOperationConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ApplicationOperationConfiguration dtoFromDao(ServiceOperationCollection dao);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    ServiceOperationCollection daoFromDto(ApplicationOperationConfiguration dto);
}
