package com.arextest.config.model.convert;

import com.arextest.config.model.dao.mongodb.ComparisonConfigCollection;
import com.arextest.config.model.replay.ComparisonConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ComparisonConfigConvert {

    ComparisonConfigConvert INSTANCE = Mappers.getMapper(ComparisonConfigConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ComparisonConfiguration dtoFromDao(ComparisonConfigCollection dao);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    ComparisonConfigCollection daoFromDto(ComparisonConfiguration dto);


}
