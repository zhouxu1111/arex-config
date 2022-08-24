package com.arextest.config.model.dao.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@Document(collection = "ComparisonDetailsConfig")
public class ComparisonDetailsConfigCollection extends ModelBase {

    private int expirationType;
    @NonNull
    private Date expirationDate;
    @NonNull
    private String pathName;
    @NonNull
    private List<String> pathValue;
    @NonNull
    private String comparisonId;

    private String decodeType;

    private String decodePrefix;

    private String decodeSecretKey;
}
