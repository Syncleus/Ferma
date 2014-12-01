/**
 * Copyright 2014-Infinity Bryn Cooke
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * This project is derived from code in the Tinkerpop project under the following licenses:
 *
 * Tinkerpop3
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Tinkerpop2
 * Copyright (c) 2009-Infinity, TinkerPop [http://tinkerpop.com]
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the TinkerPop nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL TINKERPOP BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jglue.totorom;

import com.tinkerpop.blueprints.Element;

/**
 * Type resolvers resolve the frame type from the element being requested and
 * may optionally store metadata about the frame type on the element.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */
public interface TypeResolver {
	/**
	 * Resolve the type of frame that a an element should be.
	 * 
	 * @param element
	 *            The element that is being framed.
	 * @param kind
	 *            The kind of frame that is being requested by the client code.
	 * @return The kind of frame
	 */
	public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind);

	/**
	 * Called when a new element is created on the graph. Initialization can be
	 * performed, for instance to save the Java type of the frame on the
	 * underlying element.
	 * 
	 * @param element
	 *            The element that was created.
	 * @param kind
	 *            The kind of frame that was resolved.
	 */
	public <T extends FramedElement> void init(Element element, Class<T> kind);

	/**
	 * This type resolver simply returns the type requested by the client.
	 */
	public static final TypeResolver Untyped = new TypeResolver() {
		@Override
		public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind) {
			return kind;
		}

		@Override
		public <T extends FramedElement> void init(Element element, Class<T> kind) {

		}
	};

	/**
	 * This type resolver will use the Java class stored in the 'java_class' on
	 * the element.
	 */
	public static final TypeResolver Java = new TypeResolver() {
		@SuppressWarnings("unchecked")
		@Override
		public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind) {
			String clazz = element.getProperty("java_class");
			if (clazz != null) {
				try {
					return (Class<T>) Class.forName(clazz);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("The class " + clazz + " cannot be found");
				}
			}
			return kind;
		}

		@Override
		public <T extends FramedElement> void init(Element element, Class<T> kind) {
			String clazz = element.getProperty("java_class");
			if (clazz == null) {
				element.setProperty("java_class", kind.getName());
			}
		}
	};
}
