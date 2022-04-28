package com.arextest.config.core.repository.handler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jmo
 * @since 2021/12/21
 */
public class StringToArrayListHandler extends StringToHashSetHandler {
    @Override
    Collection<String> newCollectionInstance() {
        return new ArrayList<>();
    }
}
