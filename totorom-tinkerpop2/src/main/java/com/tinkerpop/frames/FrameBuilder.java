package com.tinkerpop.frames;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public interface FrameBuilder {


    public <T extends FramedElement> T create(Class<T> kind);

    public static FrameBuilder Default = new FrameBuilder() {
        @Override
        public <T extends FramedElement> T create(Class<T> kind) {
            try {
                return kind.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    };
}
