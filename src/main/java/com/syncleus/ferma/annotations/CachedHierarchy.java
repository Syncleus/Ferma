package com.syncleus.ferma.annotations;

import java.util.Map;
import java.util.Set;

public interface CachedHierarchy {
    Map<String, Set<String>> getHierarchy();
    void setHierarchy(Map<String, Set<String>> hierarchy);
}
