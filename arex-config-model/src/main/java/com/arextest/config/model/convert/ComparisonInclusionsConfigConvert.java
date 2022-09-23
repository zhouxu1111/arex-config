package com.arextest.config.model.convert;

import com.arextest.config.model.dao.mongodb.ComparisonInclusionsCollection;
import com.arextest.config.model.replay.ComparisonInclusionsConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ComparisonInclusionsConfigConvert {

    ComparisonInclusionsConfigConvert INSTANCE = Mappers.getMapper(ComparisonInclusionsConfigConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ComparisonInclusionsConfiguration dtoFromDao(ComparisonInclusionsCollection dao);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    ComparisonInclusionsCollection daoFromDto(ComparisonInclusionsConfiguration dto);
}