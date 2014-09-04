package org.jglue.totorom;

import java.util.Iterator;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * The base class that all vertex frames must extend.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */
public abstract class FramedVertex extends FramedElement {

	protected Vertex element() {
		return (Vertex) super.element();
	};

	protected <T extends FramedEdge> T addEdge(String label, FramedVertex inVertex, Class<T> kind) {

		Edge edge = element().addEdge(label, inVertex.element());
		T framedEdge = graph().frameNewElement(edge, kind);
		framedEdge.init();
		return framedEdge;
	}

	protected VertexTraversal<?, ?> out(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().out(branchFactor, labels);
	}

	protected VertexTraversal<?, ?> out(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().out(labels);
	}

	protected VertexTraversal<?, ?> in(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().in(branchFactor, labels);
	}

	protected VertexTraversal<?, ?> in(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().in(labels);
	}

	protected VertexTraversal<?, ?> both(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().both(branchFactor, labels);
	}

	protected VertexTraversal<?, ?> both(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().both(labels);
	}

	protected EdgeTraversal<?, ?> outE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().outE(branchFactor, labels);
	}

	protected EdgeTraversal<?, ?> outE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().outE(labels);
	}

	protected EdgeTraversal<?, ?> inE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().inE(branchFactor, labels);
	}

	protected EdgeTraversal<?, ?> inE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().inE(labels);
	}

	protected EdgeTraversal<?, ?> bothE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().bothE(branchFactor, labels);
	}

	protected EdgeTraversal<?, ?> bothE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().bothE(labels);
	}

	protected void linkOut(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkOut(label, vertex).iterate();
		}
	}

	protected void linkIn(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkIn(label, vertex).iterate();
		}
	}

	protected void linkBoth(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkBoth(label, vertex).iterate();
		}
	}

	protected void unlinkOut(FramedVertex vertex, String... labels) {
		EdgeTraversal<?, ?> pipeline = outE(labels).as("e");
		if (vertex != null) {
			pipeline = pipeline.inV().retain(Lists.newArrayList(vertex)).back("e").castToEdges();
		}
		pipeline.remove();
	}

	protected void unlinkIn(FramedVertex vertex, String... labels) {
		EdgeTraversal<?, ?> pipeline = inE(labels).as("e");
		if (vertex != null) {
			pipeline = pipeline.outV().retain(Lists.newArrayList(vertex)).back("e").castToEdges();
		}
		pipeline.remove();
	}

	protected void unlinkBoth(FramedVertex vertex, String... labels) {
		EdgeTraversal<?, ?> pipeline = bothE(labels).as("e");
		if (vertex != null) {
			pipeline = pipeline.bothV().retain(Lists.newArrayList(vertex)).back("e").castToEdges();
		}
		pipeline.remove();
	}

	protected void setLinkIn(FramedVertex vertex, String... labels) {
		unlinkIn(null, labels);
		linkIn(vertex, labels);
	}

	protected void setLinkOut(FramedVertex vertex, String... labels) {
		unlinkOut(null, labels);
		linkOut(vertex, labels);
	}

	protected void setLinkBoth(FramedVertex vertex, String... labels) {
		unlinkBoth(null, labels);
		linkBoth(vertex, labels);
	}

	protected <K extends FramedVertex> FramedVertex setLinkOut(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkOut(vertex, labels);
		return vertex;
	}

	protected <K extends FramedVertex> FramedVertex setLinkIn(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkIn(vertex, labels);
		return vertex;
	}

	protected <K extends FramedVertex> FramedVertex setLinkBoth(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkBoth(vertex, labels);
		return vertex;
	}

	/**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	protected VertexTraversal<?, ?> traversal() {
		return new TraversalImpl(graph(), this).castToVertices();
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		if (getId() instanceof Number) {
			json.addProperty("id", getId(Number.class));
		}
		if (getId() instanceof String) {
			json.addProperty("id", getId(String.class));
		}
		json.addProperty("elementClass", "vertex");
		for (String key : getPropertyKeys()) {

			Object value = getProperty(key);
			if (getId() instanceof Number) {
				json.addProperty(key, (Number) value);
			} else if (getId() instanceof String) {
				json.addProperty(key, (String) value);
			}
		}
		return json;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(toJson());
	}
}
