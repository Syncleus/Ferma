package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Element;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public interface TypeResolver {
    public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind);

    public static final TypeResolver Untyped = new TypeResolver() {
        @Override
        public <T extends FramedElement> Class<T> resolve(Element element, Class<T> kind) {
            return kind;
        }
    };
}
