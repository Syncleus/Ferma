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
package com.syncleus.ferma;

import org.apache.tinkerpop.gremlin.structure.Element;
import java.util.Set;

public interface ElementFrame {

    /**
     * @param <N> The ID's type.
     * @return The id of this element.
     */
    <N> N getId();

    /**
     * @return The property keys of this element.
     */
    Set<String> getPropertyKeys();

    /**
     * Remove this element from the graph.
     */
    void remove();

    /**
     * @return The underlying element.
     */
    Element getElement();

    /**
     * Set the underlying element.
     * @param element The new underlying element.
     */
	void setElement(Element element);

    /**
     * @return The underlying graph.
     */
    FramedGraph getGraph();

    /**
     * Return a property value.
     *
     * @param <T> The type of the property value.
     * @param name
     *            The name of the property.
     * @return the value of the property or null if none was present.
     */
    <T> T getProperty(String name);

    /**
     * Return a property value.
     *
     * @param <T> The type of the property value.
     * @param name
     *            The name of the property.
     * @param type
     *            The type of the property.
     *
     * @return the value of the property or null if none was present.
     */
    <T> T getProperty(String name, Class<T> type);

    /**
     * Set a property value.
     *
     * @param name
     *            The name of the property.
     * @param value
     *            The value of the property.
     */
    void setProperty(String name, Object value);

    /**
     * Returns the type resolution currently encoded into the element.
     *
     * @return the current type resolution.
     * @since 2.1.0
     */
    Class<?> getTypeResolution();

    /**
     * Sets the type resolution and encodes it into the element in the graph.
     *
     * @param type The new type to resolve this element to.
     * @since 2.1.0
     */
    void setTypeResolution(Class<?> type);

    /**
     * Removes type resolution from this node and decodes it from the element in the graph.
     *
     * @since 2.1.0
     */
    void removeTypeResolution();

}
