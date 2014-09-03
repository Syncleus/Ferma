package org.jglue.totorom;

import java.util.Set;

/**
 * A framed edge for use when you don't want to create a new frame class. Typically used in traversals.
 * @author bryn
 *
 */
public final class GenericFramedEdge extends FramedEdge {
	@Override
	public FramedVertexTraversal<?> bothV() {
		return super.bothV();
	}

	@Override
	public FramedVertexTraversal<?> inV() {
		return super.inV();
	}

	@Override
	public FramedVertexTraversal<?> outV() {
		return super.outV();
	}

	@Override
	public FramedEdgeTraversal<?> traversal() {
		return super.traversal();
	}

	@Override
	public FramedVertexTraversal<?> v(Object... ids) {
		return super.v(ids);
	}

	@Override
	public FramedEdgeTraversal<?> E() {
		return super.E();
	}

	@Override
	public FramedVertexTraversal<?> V() {
		return super.V();
	}

	@Override
	public void remove() {
		super.remove();
	}

	@Override
	public String getLabel() {

		return super.getLabel();
	}

	@Override
	public <N> N getId() {

		return super.getId();
	}

	@Override
	public <T> T getProperty(String name) {

		return super.getProperty(name);
	}

	@Override
	public Set<String> getPropertyKeys() {

		return super.getPropertyKeys();
	}

	@Override
	public void setProperty(String name, Object value) {

		super.setProperty(name, value);
	}
	
	@Override
	public <N> N getId(Class<N> clazz) {

		return (N) super.getId(clazz);
	}
}
