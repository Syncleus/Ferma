/**
 * Copyright 2004 - 2017 Syncleus, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.VertexFrame;
import java.util.Iterator;

/**
 *
 * @author rqpa
 */
public class ReframingVertexIterator<T> implements Iterator<T>{
    
    private final Iterator<? extends VertexFrame> vertexFrameIterator;
    private final Class<T> framedType;

    public ReframingVertexIterator(Iterator<? extends VertexFrame> vertexFrameIterator, Class<T> framedType) {
        this.vertexFrameIterator = vertexFrameIterator;
        this.framedType = framedType;
    }

    @Override
    public boolean hasNext() {
        return vertexFrameIterator.hasNext();
    }

    @Override
    public T next() {
        VertexFrame next = vertexFrameIterator.next();
        return next == null ? null : next.reframe(framedType);
    }

    @Override
    public void remove() {
        vertexFrameIterator.remove();
    }
    
    
}
