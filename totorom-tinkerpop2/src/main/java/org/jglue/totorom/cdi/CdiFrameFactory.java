package org.jglue.totorom.cdi;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jglue.totorom.FrameFactory;
import org.jglue.totorom.FramedElement;
import org.jglue.totorom.FramedGraph;

import com.tinkerpop.blueprints.Element;

/**
 * Frame builder that will delegate the creation of frames to CDI. With this you
 * can take advantage of injection/interceptors etc. Usage:
 * <pre>
 * <code>
 * {@literal @}Inject
 * CdiFrameBuilder frameBuilder;
 * 
 * public void myMethod() {
 * 	{@link FramedGraph} g = new {@link FramedGraph}(frameBuilder, TypeResolver.Default);</br> 
 * }
 * </code></pre>
 * @author Bryn Cooke (http://jglue.org)
 * 
 */
public class CdiFrameFactory implements FrameFactory {

	@Inject
	private Instance<Object> instance;

	@Override
	public <T extends FramedElement<?>> T create(Element element, Class<T> kind) {
		return instance.select(kind).get();
	}

}
