package com.arextest.config.model.replay;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ComparisonInclusionsConfiguration extends AbstractComparisonDetailsConfiguration {
    List<String> inclusions;
}