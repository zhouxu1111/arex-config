package com.arextest.config.model.convert;

import com.arextest.config.model.dao.mongodb.ComparisonDetailsConfigCollection;
import com.arextest.config.model.replay.ComparisonDetailsConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ComparisonDetailsConfigConvert {

    ComparisonDetailsConfigConvert INSTANCE = Mappers.getMapper(ComparisonDetailsConfigConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ComparisonDetailsConfiguration dtoFromDao(ComparisonDetailsConfigCollection dao);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    ComparisonDetailsConfigCollection daoFromDto(ComparisonDetailsConfiguration dto);

}
