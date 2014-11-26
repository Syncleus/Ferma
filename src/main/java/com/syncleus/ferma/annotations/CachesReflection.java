package com.syncleus.ferma.annotations;

import com.syncleus.ferma.ReflectionCache;

import java.util.Map;
import java.util.Set;

public interface CachesReflection {
    ReflectionCache getReflectionCache();
    void setReflectionCache(ReflectionCache hierarchy);
}
