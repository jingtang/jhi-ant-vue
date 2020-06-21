package com.aidriveall.cms.domain;

import java.util.Map;

public interface Owner {
    String getEntityName();
    Long getId();
    Map<String, Object> getExtData();
}
