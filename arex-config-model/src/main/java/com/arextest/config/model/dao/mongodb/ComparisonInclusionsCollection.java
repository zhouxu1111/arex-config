package com.arextest.config.model.dao.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "ConfigComparisonInclusions")
public class ComparisonInclusionsCollection extends AbstractComparisonDetails {

    @NonNull
    private List<String> inclusions;

}
