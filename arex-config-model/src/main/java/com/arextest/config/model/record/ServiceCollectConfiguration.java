package com.arextest.config.model.record;

import com.arextest.config.model.AbstractConfiguration;
import com.arextest.config.model.dao.mongodb.RecordServiceConfigCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author jmo
 * @see RecordServiceConfigCollection
 * @since 2021/12/21
 */
@Setter
@Getter
public class ServiceCollectConfiguration extends AbstractConfiguration {
    private String appId;
    /**
     * The sample rate means for in 100 seconds should be occurred the number of records.
     * example:
     * if the value is 50,would be recorded 50 times in 100 seconds.
     */
    private int sampleRate;

    private Set<String> excludeDependentOperationSet;

    /**
     * All the dependent services should be skipped when recording.
     */
    private Set<String> excludeDependentServiceSet;
    /**
     * skip record the operations
     * excludeOperations, eg:check health
     */
    private Set<String> excludeOperationSet;

    /**
     * The main entry's service should be recorded it.
     * if the value is empty, means there is unlimited
     */
    private Set<String> includeServiceSet;
    /**
     * The main entry's operation should be  recording it.
     * if the value is empty, means there is unlimited
     */
    private Set<String> includeOperationSet;
    /**
     * Bit flag composed of bits that indicate which {@link java.time.DayOfWeek}s are enabled to recording.
     * if the value is zero means everyday should be working,it is unlimited.
     * for example:
     * {@code private int allowDayOfWeeks=1<< DayOfWeek.MONDAY.getValue()}
     */
    private int allowDayOfWeeks;
    /**
     * HH:mm  example: 00:01
     */
    private String allowTimeOfDayFrom;
    /**
     * HH:mm example: 23:59
     */
    private String allowTimeOfDayTo;
}
