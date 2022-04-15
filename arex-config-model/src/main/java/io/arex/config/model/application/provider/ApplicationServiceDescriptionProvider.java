package io.arex.config.model.application.provider;

import io.arex.config.model.application.ServiceDescription;

import java.util.List;

/**
 * @author jmo
 * @since 2022/1/21
 */
public interface ApplicationServiceDescriptionProvider {
    List<? extends ServiceDescription> get(String appId, String host);
}
