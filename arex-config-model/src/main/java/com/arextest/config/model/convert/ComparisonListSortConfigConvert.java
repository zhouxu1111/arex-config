package com.arextest.config.model.convert;

import com.arextest.config.model.dao.mongodb.ComparisonListSortCollection;
import com.arextest.config.model.replay.ComparisonListSortConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by rchen9 on 2022/9/16.
 */
@Mapper
public interface ComparisonListSortConfigConvert {

    ComparisonListSortConfigConvert INSTANCE = Mappers.getMapper(ComparisonListSortConfigConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ComparisonListSortConfiguration dtoFromDao(ComparisonListSortCollection dao);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    ComparisonListSortCollection daoFromDto(ComparisonListSortConfiguration dto);
}
