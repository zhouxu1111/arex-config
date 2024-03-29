package com.arextest.config.model.dao.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Document(collection = "DynamicClass")
public class DynamicClassCollection extends AbstractModelBase {

    @NonNull
    private String appId;

    private String fullClassName;

    private String methodName;

    private String parameterTypes;

    private String keyFormula;

    private int configType;
}
