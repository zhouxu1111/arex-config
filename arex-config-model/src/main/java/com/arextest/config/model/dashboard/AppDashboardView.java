package com.arextest.config.model.dashboard;

import com.arextest.config.model.application.ApplicationDescription;
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
