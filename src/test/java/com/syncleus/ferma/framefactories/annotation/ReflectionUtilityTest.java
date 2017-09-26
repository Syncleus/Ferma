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
package com.syncleus.ferma.framefactories.annotation;

import com.syncleus.ferma.annotations.God;
import com.syncleus.ferma.graphtypes.network.ComputerVertex;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class ReflectionUtilityTest {
    
    private static class SomeMockClass<N extends Number> {
        
        public void foo(Function<? extends Number, ? super Comparable> f) {
            // Implementation is irrelevant
        }
    };
    
    private static Method getMethod(Class<?> clazz, String methodName) {
        return Stream.of(clazz.getMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElse(null);
    }
    
    @Test
    public void testActualTypeWithCollection() throws NoSuchMethodException {
        doGetActualTypeTest(
                Object.class, // List has no upper bounds
                Collection.class.getTypeParameters()[0],
                0);
    }
    
    @Test
    public void testReturnsMap() throws NoSuchMethodException {
        Assert.assertTrue(ReflectionUtility.returnsSet(Collections.class.getMethod("emptySet")));
        Assert.assertFalse(ReflectionUtility.returnsSet(Collections.class.getMethod("reverseOrder")));
    }
    
    @Test
    public void testActualTypeWithParameterizedType() throws NoSuchMethodException, NoSuchFieldException {
        doGetActualTypeTest(
                Number.class, // List has no upper bounds
                getMethod(SomeMockClass.class, "foo").getGenericParameterTypes()[0],
                0);
    }
    
    @Test
    public void testActualTypeWithWildcardType() throws NoSuchMethodException, NoSuchFieldException {
        ParameterizedType paramType = (ParameterizedType) getMethod(SomeMockClass.class, "foo").getGenericParameterTypes()[0];
        Type wildCardType = paramType.getActualTypeArguments()[0];
        doGetActualTypeTest(
                Number.class, // List has no upper bounds
                wildCardType,
                0);
    }
    
    @Test
    public void testIsSetMethod() {
        Assert.assertTrue(ReflectionUtility.isSetMethod(getMethod(ComputerVertex.class, "setName")));
        Assert.assertTrue(ReflectionUtility.isSetMethod(getMethod(God.class, "applyName")));
        Assert.assertFalse(ReflectionUtility.isSetMethod(getMethod(ComputerVertex.class, "disconnectFromNetwork")));
    }
    
    @Test
    public void testIsRemoveMethod() {
        Assert.assertTrue(ReflectionUtility.isRemoveMethod(getMethod(God.class, "removeSonEdge")));
        Assert.assertTrue(ReflectionUtility.isRemoveMethod(getMethod(God.class, "deleteSonEdge")));
        Assert.assertTrue(ReflectionUtility.isRemoveMethod(getMethod(ComputerVertex.class, "disconnectFromNetwork")));
        Assert.assertFalse(ReflectionUtility.isRemoveMethod(getMethod(ComputerVertex.class, "setName")));
    }
    
    
    private void doGetActualTypeTest(Class<?> expected, Type toTest, int pos) {
        Class<?> actualType = ReflectionUtility.getActualType(toTest, pos);
        Assert.assertEquals("Types mismatch!", expected, actualType);
    }
}
