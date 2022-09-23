package com.arextest.config.model.dao.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by rchen9 on 2022/9/16.
 */
@Data
@NoArgsConstructor
@Document(collection = "ConfigComparisonExclusions")
public class ComparisonExclusionsCollection extends AbstractComparisonDetails {
    @NonNull
    private List<String> exclusions;
}
