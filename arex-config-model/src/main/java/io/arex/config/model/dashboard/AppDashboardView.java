package io.arex.config.model.dashboard;

import io.arex.config.model.application.ApplicationDescription;
import lombok.Data;

/**
 * @author jmo
 * @since 2022/1/21
 */
@Data
public class AppDashboardView {
    private ApplicationDescription applicationDescription;
    private int operationCount;
}
