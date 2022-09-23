package com.arextest.config.model.dao.mongodb;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

/**
 * Created by rchen9 on 2022/9/16.
 */
@Getter
@Setter
public abstract class AbstractComparisonDetails extends AbstractModelBase {

    @NonNull
    private String appId;

    private String operationId;

    private int expirationType;
    @NonNull
    private Date expirationDate;
}
