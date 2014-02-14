package org.jglue.totorom.spring;

import org.jglue.totorom.FrameFactory;
import org.jglue.totorom.FramedElement;
import org.jglue.totorom.FramedGraph;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.tinkerpop.blueprints.Element;

/**
 * Frame builder that will delegate the creation of frames to Spring. With this you
 * can take advantage of injection/interceptors etc. Usage:
 * <pre>
 * <code>
 * {@literal @}Autowired
 * SpringFrameBuilder frameBuilder;
 * 
 * public void myMethod() {
 * 	{@link FramedGraph} g = new {@link FramedGraph}(frameBuilder, TypeResolver.Default);</br> 
 * }
 * </code></pre>
 * @author Bryn Cooke (http://jglue.org)
 * 
 */
public class SpringFrameFactory implements FrameFactory, BeanFactoryAware {

	
	private BeanFactory factory;

	@Override
	public <T extends FramedElement<?>> T create(Element element, Class<T> kind) {
		return factory.getBean(kind);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		factory = beanFactory;
	}

}
