package org.jglue.totorom;

import com.tinkerpop.blueprints.Element;

/**
 * Does the actual work of constructing the frame. Implementations
 * 
 * @author Bryn Cooke (http://jglue.org)
 * 
 */
public interface FrameFactory {

	public <T extends FramedElement> T create(Element e, Class<T> kind);

	/**
	 * Creates the frame using reflection.
	 */
	public static FrameFactory Default = new FrameFactory() {
		@Override
		public <T extends FramedElement> T create(Element element, Class<T> kind) {
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
