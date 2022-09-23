package com.arextest.config.model.convert;

import com.arextest.config.model.dao.mongodb.ComparisonReferenceCollection;
import com.arextest.config.model.replay.ComparisonReferenceConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by rchen9 on 2022/9/16.
 */
@Mapper
public interface ComparisonReferenceConfigConvert {
    
    ComparisonReferenceConfigConvert INSTANCE = Mappers.getMapper(ComparisonReferenceConfigConvert.class);

    @Mappings({
            @Mapping(target = "modifiedTime", expression = "java(dao.getDataChangeUpdateTime() == null ? null : new java.sql.Timestamp(dao.getDataChangeUpdateTime()))")
    })
    ComparisonReferenceConfiguration dtoFromDao(ComparisonReferenceCollection dao);

    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "dataChangeCreateTime", expression = "java(System.currentTimeMillis())"),
            @Mapping(target = "dataChangeUpdateTime", expression = "java(System.currentTimeMillis())")
    })
    ComparisonReferenceCollection daoFromDto(ComparisonReferenceConfiguration dto);
}
