/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.ferma;

import java.util.*;

public class GenericClassInitializer<C> implements ClassInitializer<C> {
    private final Class<C> type;
    private final Map<String, Object> properties;

    public GenericClassInitializer(final Class<C> type, final Map<String, Object> properties) {
        this.type = type;
        this.properties = Collections.unmodifiableMap(new HashMap<>(properties));
    }

    @Override
    public Class<C> getInitializationType() {
        return this.type;
    }
    
    protected Map<String, Object> getProperties() {
        return properties;
    }
    
    @Override
    public void initalize(C frame) {
        if( !(frame instanceof ElementFrame) )
            throw new IllegalArgumentException("frame was not an instance of an ElementFrame");
        final ElementFrame elementFrame = (ElementFrame) frame;
        for(final Map.Entry<String, Object> property : this.properties.entrySet() )
            elementFrame.setProperty(property.getKey(), property.getValue());
    }
}
