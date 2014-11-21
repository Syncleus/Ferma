package com.syncleus.ferma;

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
