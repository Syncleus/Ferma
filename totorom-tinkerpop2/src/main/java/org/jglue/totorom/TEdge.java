package org.jglue.totorom;

import java.util.Set;

/**
 * A framed edge for use when you don't want to create a new frame class.
 * Typically used in traversals.
 * 
 * @author bryn
 *
 */
public final class TEdge extends FramedEdge {
	@Override
	public VertexTraversal<?, ?, ?> bothV() {
		return super.bothV();
	}

	@Override
	public VertexTraversal<?, ?, ?> inV() {
		return super.inV();
	}

	@Override
	public VertexTraversal<?, ?, ?> outV() {
		return super.outV();
	}

	@Override
	public EdgeTraversal<?, ?, ?> traversal() {
		return super.traversal();
	}

	@Override
	public VertexTraversal<?, ?, ?> v(Object... ids) {
		return super.v(ids);
	}

	@Override
	public EdgeTraversal<?, ?, ?> E() {
		return super.E();
	}

	@Override
	public VertexTraversal<?, ?, ?> V() {
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
	public <T> T getProperty(String name, Class<T> clazz) {
		return super.getProperty(name, clazz);
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

	@Override
	public <N> Traversal<N, ?, ?, ?> property(String key) {
		return super.property(key);
	}

	@Override
	public <N> Traversal<N, ?, ?, ?> property(String key, Class<N> kind) {

		return super.property(key, kind);
	}
	
	
}
