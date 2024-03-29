package com.arextest.config.model.dao.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Data
@NoArgsConstructor
@Document(collection = "ReplayScheduleConfig")
public class ReplayScheduleConfigCollection extends AbstractModelBase {

    @NonNull
    @Indexed(unique = true)
    private String appId;
    @NonNull
    private Integer offsetDays;
    @NonNull
    private Set<String> targetEnv;
    @NonNull
    private Integer sendMaxQps;
}
