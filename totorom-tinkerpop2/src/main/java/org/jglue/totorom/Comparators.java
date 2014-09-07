package org.jglue.totorom;

import java.util.Comparator;

/**
 * Usefule comparators when dealiong with framed elements
 * 
 * @author bryn
 *
 */
public class Comparators {

	/**
	 * Compare by Id.
	 * 
	 * @return
	 */
	public static <N extends FramedElement> Comparator<N> id() {
		return new Comparator<N>() {
			@Override
			public int compare(N o1, N o2) {
				Comparable c1 = o1.getId();
				Comparable c2 = o2.getId();

				return c1.compareTo(c2);
			}
		};
	}

	/**
	 * Compare by id parsed as a long (Useful for tinkergraph)
	 * 
	 * @return
	 */
	public static <N extends FramedElement> Comparator<N> idAsLong() {
		return new Comparator<N>() {
			@Override
			public int compare(N o1, N o2) {
				Long c1 = Long.parseLong((String) o1.getId());
				Long c2 = Long.parseLong((String) o2.getId());

				return c1.compareTo(c2);
			}
		};
	}

	/**
	 * Compare by property. Note that no value may be null.
	 * 
	 * @param property
	 *            The property to compare by.
	 * @return The result of comparing the property.
	 */
	public static <N extends FramedElement> Comparator<N> property(final String property) {
		return new Comparator<N>() {
			@Override
			public int compare(N o1, N o2) {
				Comparable c1 = o1.getProperty(property);
				Comparable c2 = o2.getProperty(property);

				return c1.compareTo(c2);
			}
		};
	}

}
