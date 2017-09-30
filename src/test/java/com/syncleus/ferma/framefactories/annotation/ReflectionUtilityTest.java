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

import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.annotations.God;
import com.syncleus.ferma.graphtypes.javaclass.JavaClassVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaTypeVertex;
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

        // Implementation of all methods is completely irrelevant
        public void foo(Function<? extends Number, ? super Comparable> f) {
        }
        
        public void noWildcardFunction(Function<Number, Comparable> f) {
        }
        
        public Function<Number, Comparable> functionReturningMethod() {
            return null;
        }

        public Boolean isFoo() {
            return true;
        }

        public boolean canFoo() {
            return true;
        }
        
        public void doesAcceptVertexFrame(VertexFrame frame) {
            
        }
        
        public void doesAcceptEdgeFrame(EdgeFrame frame) {
            
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
        assertGetActualType(
                Object.class, // List has no upper bounds
                Collection.class.getTypeParameters()[0],
                0);
    }

    @Test
    public void testActualTypeWithArray() throws NoSuchMethodException {
        assertGetActualType(
                Double.class, 
                Double[].class,
                0);
    }

    @Test
    public void testActualTypeWithNull()  {
        assertGetActualType(
                null, 
                null,
                0);
    }

    @Test
    public void testReturnsMap() throws NoSuchMethodException {
        Assert.assertTrue(ReflectionUtility.returnsSet(Collections.class.getMethod("emptySet")));
        Assert.assertFalse(ReflectionUtility.returnsSet(Collections.class.getMethod("reverseOrder")));
    }

    @Test
    public void testActualTypeWithParameterizedType() throws NoSuchMethodException, NoSuchFieldException {
        assertGetActualType(
                Number.class, 
                getMethod(SomeMockClass.class, "foo").getGenericParameterTypes()[0],
                0);
    }

    @Test
    public void testActualTypeWithWildcardTypeUpperBounds() throws NoSuchMethodException, NoSuchFieldException {
        ParameterizedType paramType = (ParameterizedType) getMethod(SomeMockClass.class, "foo").getGenericParameterTypes()[0];
        Type wildCardType = paramType.getActualTypeArguments()[0];
        assertGetActualType(
                Number.class, 
                wildCardType,
                0);
    }

    @Test
    public void testActualTypeParamTypeNoWildcard() throws NoSuchMethodException, NoSuchFieldException {
        ParameterizedType paramType = (ParameterizedType) getMethod(SomeMockClass.class, "noWildcardFunction").getGenericParameterTypes()[0];
        assertGetActualType(
                Comparable.class, 
                paramType,
                1);
    }

    @Test
    public void testActualTypeWithWildcardTypeLowerBounds() throws NoSuchMethodException, NoSuchFieldException {
        ParameterizedType paramType = (ParameterizedType) getMethod(SomeMockClass.class, "foo").getGenericParameterTypes()[0];
        Type wildCardType = paramType.getActualTypeArguments()[1];
        assertGetActualType(
                Comparable.class, 
                wildCardType,
                0);
    }

    @Test
    public void testGetGenericType() throws NoSuchMethodException, NoSuchFieldException {
        // Maybe we want to add position to getGenericType and not just assume the first
        // generic type is required?
        Class actual = ReflectionUtility.getGenericClass(getMethod(SomeMockClass.class, "functionReturningMethod"));
        Assert.assertEquals(Number.class, actual);
        actual = ReflectionUtility.getGenericClass(getMethod(SomeMockClass.class, "foo"));
        Assert.assertEquals(void.class, actual);
    }

    @Test
    public void testAcceptsIterator() {
        Assert.assertFalse(ReflectionUtility.acceptsIterator(getMethod(ComputerVertex.class, "addAndConnectOutVertexTypedEdgeTyped"), 0));
        Assert.assertTrue(ReflectionUtility.acceptsIterator(getMethod(ComputerVertex.class, "setTwoWayConnectionsWithIterator"), 0));
        Assert.assertFalse(ReflectionUtility.acceptsIterator(getMethod(ComputerVertex.class, "setTwoWayConnectionsWithIterator"), 1));
        Assert.assertFalse(ReflectionUtility.acceptsIterator(getMethod(ComputerVertex.class, "setTwoWayConnectionsWithIterable"), 0));
    }

    @Test
    public void testAcceptsIterable() {
        Assert.assertFalse(ReflectionUtility.acceptsIterable(getMethod(ComputerVertex.class, "addAndConnectOutVertexTypedEdgeTyped"), 0));
        Assert.assertTrue(ReflectionUtility.acceptsIterable(getMethod(ComputerVertex.class, "setTwoWayConnectionsWithIterable"), 0));
        Assert.assertFalse(ReflectionUtility.acceptsIterable(getMethod(ComputerVertex.class, "setTwoWayConnectionsWithIterable"), 1));
        Assert.assertFalse(ReflectionUtility.acceptsIterable(getMethod(ComputerVertex.class, "setTwoWayConnectionsWithIterator"), 0));
    }

    @Test
    public void testAcceptsVertexFrame() {
        Assert.assertFalse(ReflectionUtility.acceptsVertexFrame(getMethod(SomeMockClass.class, "doesAcceptEdgeFrame"), 0));
        Assert.assertFalse(ReflectionUtility.acceptsVertexFrame(getMethod(SomeMockClass.class, "doesAcceptVertexFrame"), 1));
        Assert.assertTrue(ReflectionUtility.acceptsVertexFrame(getMethod(SomeMockClass.class, "doesAcceptVertexFrame"), 0));
    }
    
    @Test
    public void testGetTypes() {
        Type[] expected = new Type[] {
            Integer.TYPE,
            Double.TYPE,
            Character.TYPE
        };
        
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(ReflectionUtility.getType(expected, i), expected[i]);
        }
    }
    
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetTypesOutOfBounds() {
        Type[] expected = new Type[] {
            Integer.TYPE,
            Double.TYPE,
            Character.TYPE
        };
        
        ReflectionUtility.getType(expected, expected.length);
    }
    
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetTypesOutOfBounds2() {
        Type[] expected = new Type[] {
            Integer.TYPE,
            Double.TYPE,
            Character.TYPE
        };
        
        ReflectionUtility.getType(expected, -3);
    }

    @Test
    public void testIsGetMethod() {
        Assert.assertTrue(ReflectionUtility.isGetMethod(getMethod(SomeMockClass.class, "isFoo")));
        Assert.assertTrue(ReflectionUtility.isGetMethod(getMethod(SomeMockClass.class, "canFoo")));
        Assert.assertTrue(ReflectionUtility.isGetMethod(getMethod(God.class, "getSons")));
        Assert.assertFalse(ReflectionUtility.isGetMethod(getMethod(ComputerVertex.class, "disconnectFromNetwork")));
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
        Assert.assertFalse(ReflectionUtility.isRemoveMethod(getMethod(JavaTypeVertex.class, "getFullyQualifiedName")));
        Assert.assertFalse(ReflectionUtility.isRemoveMethod(getMethod(JavaClassVertex.class, "implementNewInterface")));
    }

    private void assertGetActualType(Class<?> expected, Type toTest, int pos) {
        Class<?> actualType = ReflectionUtility.getActualType(toTest, pos);
        Assert.assertEquals("Types mismatch!", expected, actualType);
    }
}
