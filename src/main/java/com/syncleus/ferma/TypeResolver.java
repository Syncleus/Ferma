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

import com.syncleus.ferma.annotations.AnnotationTypeResolver;
import com.tinkerpop.blueprints.Element;

/**
 * Type resolvers resolve the frame type from the element being requested and
 * may optionally store metadata about the frame type on the element.
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
	public <T> Class<T> resolve(Element element, Class<T> kind);

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
	public <T> void init(Element element, Class<T> kind);

	/**
	 * This type resolver simply returns the type requested by the client.
	 */
	public static final TypeResolver UNTYPED = new TypeResolver() {
		@Override
		public <T> Class<T> resolve(Element element, Class<T> kind) {
			return kind;
		}

		@Override
		public <T> void init(Element element, Class<T> kind) {

		}
	};

	/**
	 * This type resolver will use the Java class stored in the 'java_class' on
	 * the element.
	 */
	public static final TypeResolver JAVA = new TypeResolver() {
		@SuppressWarnings("unchecked")
		@Override
		public <T> Class<T> resolve(Element element, Class<T> kind) {
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
		public <T> void init(Element element, Class<T> kind) {
			String clazz = element.getProperty("java_class");
			if (clazz == null) {
				element.setProperty("java_class", kind.getName());
			}
		}
	};

	/**
	 * This type resolver is used when Annotations are to be used.
	 */
	public static final TypeResolver ANNOTATED = new AnnotationTypeResolver();
}
