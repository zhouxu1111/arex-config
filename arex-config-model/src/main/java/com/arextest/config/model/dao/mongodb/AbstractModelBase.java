package com.arextest.config.model.dao.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public abstract class AbstractModelBase {
    @Id
    private String id;
    private Long dataChangeCreateTime;
    private Long dataChangeUpdateTime;
}
