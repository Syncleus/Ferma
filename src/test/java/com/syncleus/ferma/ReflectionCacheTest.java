/**
 * Copyright 2004 - 2016 Syncleus, Inc.
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
package com.syncleus.ferma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class ReflectionCacheTest {
    
    @Test
    public void testGetSubtypeNames() {
        ReflectionCache cache = new ReflectionCache();
        Set<? extends String> listSubTypes = cache.getSubTypeNames(List.class);
        Assert.assertEquals(1, listSubTypes.size());
        Assert.assertTrue(listSubTypes.contains(List.class.getName()));
        
        List<Class<?>> lists = Arrays.asList(List.class, ArrayList.class, LinkedList.class);
        cache = new ReflectionCache(lists);
        listSubTypes = cache.getSubTypeNames(List.class);
        Assert.assertEquals(lists.size(), listSubTypes.size());
        for (Class<?> list : lists) {
            Assert.assertTrue(listSubTypes.contains(list.getName()));
        }
    }
    
    @Test
    public void testForName() {
        ReflectionCache cache = new ReflectionCache();
        Assert.assertEquals(List.class, cache.forName(List.class.getName()));
        Assert.assertEquals(Double.class, cache.forName(Double.class.getName()));
        Assert.assertNotEquals(List.class, cache.forName(Double.class.getName()));
    }
    
    @Test (expected = IllegalStateException.class)
    public void testForNameMissingClass() {
        ReflectionCache cache = new ReflectionCache();
        cache.forName("org.foo.goo.Moo");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBadConstructorCall() {
        createCache(null);
    }
    
    private ReflectionCache createCache(Collection<? extends Class<?>> annotatedTypes) {
        return new ReflectionCache(annotatedTypes);
    }
}
