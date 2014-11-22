package com.syncleus.ferma;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tinkerpop.blueprints.Edge;

/**
 * The base class that all edge frames must extend.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */
public abstract class FramedEdge extends FramedElement {

	public Edge element() {
		return (Edge) super.element();
	};

	/**
	 * @return The label associated with this edge
	 */
	public String getLabel() {
		return element().getLabel();
	}

	/**
	 * @return The in vertex for this edge.
	 */
	public VertexTraversal<?, ?, ?> inV() {
		return new TraversalImpl(graph(), this).castToEdges().inV();
	}

	/**
	 * @return The out vertex of this edge.
	 */
	public VertexTraversal<?, ?, ?> outV() {
		return new TraversalImpl(graph(), this).castToEdges().outV();
	}

	/**
	 * @return The vertices for this edge.
	 */
	public VertexTraversal<?, ?, ?> bothV() {
		return new TraversalImpl(graph(), this).castToEdges().bothV();
	}

	/**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	public EdgeTraversal<?, ?, ?> traversal() {
		return new TraversalImpl(graph(), this).castToEdges();
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		if (getId() instanceof Number) {
			json.addProperty("id", getId(Number.class));
		}
		if (getId() instanceof String) {
			json.addProperty("id", getId(String.class));
		}
		json.addProperty("elementClass", "edge");
		json.addProperty("label", getLabel());
		for (String key : getPropertyKeys()) {

			Object value = getProperty(key);
			if (value instanceof Number) {
				json.addProperty(key, (Number) value);
			} else if (value instanceof String) {
				json.addProperty(key, (String) value);
			}
		}
		json.add("outV", outV().next().toJson());
		json.add("inV", inV().next().toJson());
		return json;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(toJson());
	}
	
	/**
	 * Reframe this element as a different type of frame.
	 * @param kind The new kind of frame.
	 * @return The new frame
	 */
	protected <T extends FramedEdge> T reframe(Class<T> kind) {
		return graph().frameElement(element(), kind);
	}
}
