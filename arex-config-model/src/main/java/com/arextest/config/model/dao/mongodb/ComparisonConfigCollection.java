package com.arextest.config.model.dao.mongodb;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "ComparisonConfig")
public class ComparisonConfigCollection extends ModelBase {

    private String appId;

    private String operationId;

    private int categoryType;
}
