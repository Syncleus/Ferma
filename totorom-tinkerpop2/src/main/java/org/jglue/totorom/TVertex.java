package org.jglue.totorom;

import java.util.Set;

/**
 * A framed vertex for use when you don't want to create a new frame class.
 * Typically used in traversals.
 * 
 * @author bryn
 *
 */
public final class TVertex extends FramedVertex {

	@Override
	public VertexTraversal<?, ?, ?> both(int branchFactor, String... labels) {

		return super.both(branchFactor, labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> both(String... labels) {

		return super.both(labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> bothE(int branchFactor, String... labels) {

		return super.bothE(branchFactor, labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> bothE(String... labels) {

		return super.bothE(labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> E() {

		return super.E();
	}

	@Override
	public EdgeTraversal<?, ?, ?> e(Object... ids) {

		return super.e(ids);
	}

	@Override
	public <N> N getId() {

		return super.getId();
	}

	@Override
	public <N> N getId(Class<N> clazz) {

		return (N) super.getId(clazz);
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
	public VertexTraversal<?, ?, ?> in(int branchFactor, String... labels) {

		return super.in(branchFactor, labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> in(String... labels) {

		return super.in(labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> inE(int branchFactor, String... labels) {

		return super.inE(branchFactor, labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> inE(String... labels) {

		return super.inE(labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> out(int branchFactor, String... labels) {

		return super.out(branchFactor, labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> out(String... labels) {

		return super.out(labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> outE(int branchFactor, String... labels) {

		return super.outE(branchFactor, labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> outE(String... labels) {

		return super.outE(labels);
	}

	@Override
	public void setProperty(String name, Object value) {
		super.setProperty(name, value);
	}

	@Override
	public void linkBoth(FramedVertex vertex, String... labels) {
		super.linkBoth(vertex, labels);
	}

	@Override
	public void linkIn(FramedVertex vertex, String... labels) {
		super.linkIn(vertex, labels);
	}

	@Override
	public void linkOut(FramedVertex vertex, String... labels) {
		super.linkOut(vertex, labels);
	}

	@Override
	public void unlinkBoth(FramedVertex vertex, String... labels) {
		super.unlinkBoth(vertex, labels);
	}

	@Override
	public void unlinkIn(FramedVertex vertex, String... labels) {
		super.unlinkIn(vertex, labels);
	}

	@Override
	public void unlinkOut(FramedVertex vertex, String... labels) {
		super.unlinkOut(vertex, labels);
	}

	@Override
	public <T extends FramedEdge> T addEdge(String label, FramedVertex inVertex, Class<T> kind) {
		return super.addEdge(label, inVertex, kind);
	}

	@Override
	public TEdge addEdge(String label, FramedVertex inVertex) {
		return super.addEdge(label, inVertex);
	}

	@Override
	public void remove() {
		super.remove();
	}

	@Override
	public VertexTraversal<?, ?, ?> traversal() {
		return super.traversal();
	};

	@Override
	public <K extends FramedVertex> FramedVertex setLinkBoth(Class<K> kind, String... labels) {
		return super.setLinkBoth(kind, labels);
	}

	@Override
	public void setLinkBoth(FramedVertex vertex, String... labels) {
		super.setLinkBoth(vertex, labels);
	}

	@Override
	public <K extends FramedVertex> FramedVertex setLinkIn(Class<K> kind, String... labels) {
		return super.setLinkIn(kind, labels);
	}

	@Override
	public void setLinkIn(FramedVertex vertex, String... labels) {
		super.setLinkIn(vertex, labels);
	}

	@Override
	public <K extends FramedVertex> FramedVertex setLinkOut(Class<K> kind, String... labels) {
		return super.setLinkOut(kind, labels);
	}

	@Override
	public void setLinkOut(FramedVertex vertex, String... labels) {
		super.setLinkOut(vertex, labels);
	}
	
	
	@Override
	public VertexTraversal<?, ?, ?> V() {
		return super.V();
	}
	
	@Override
	public VertexTraversal<?, ?, ?> v(Object... ids) {
		return super.v(ids);
	}
}
