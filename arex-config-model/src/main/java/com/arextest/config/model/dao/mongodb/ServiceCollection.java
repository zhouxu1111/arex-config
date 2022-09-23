package com.arextest.config.model.dao.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Document(collection = "Service")
public class ServiceCollection extends AbstractModelBase {
    @NonNull
    private String appId;
    @NonNull
    private String serviceName;
    @NonNull
    private String serviceKey;
    @NonNull
    private Integer status;

}
