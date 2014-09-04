package org.jglue.totorom;

import java.util.Set;

/**
 * A framed vertex for use when you don't want to create a new frame class. Typically used in traversals.
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

}
