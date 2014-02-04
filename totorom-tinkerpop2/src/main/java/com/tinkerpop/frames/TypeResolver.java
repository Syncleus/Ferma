package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Element;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public interface TypeResolver {
    public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind);
    public <T extends FramedElement> void init(Element element, Class<T> kind);

    public static final TypeResolver Untyped = new TypeResolver() {
        @Override
        public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind) {
            return kind;
        }

		@Override
		public <T extends FramedElement> void init(Element element,
				Class<T> kind) {
			
		}
    };
    
    
    public static final TypeResolver Java = new TypeResolver() {
        @Override
        public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind) {
        	String clazz = element.getProperty("java_class");
        	if(clazz != null) {
        		try {
					return (Class<T>) Class.forName(clazz);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("The class " + clazz + " cannot be found");
				}
        	}
            return kind;
        }

		@Override
		public <T extends FramedElement> void init(Element element,
				Class<T> kind) {
			String clazz = element.getProperty("java_class");
        	if(clazz == null) {
        		element.setProperty("java_class", kind.getName());
        	}
		}
    };
}
