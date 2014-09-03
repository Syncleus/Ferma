package org.jglue.totorom;

import java.util.Comparator;

public class Comparators {

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
	
	public static <N extends FramedElement> Comparator<N> idAsLong() {
		return new Comparator<N>() {
			@Override
			public int compare(N o1, N o2) {
				Long c1 = Long.parseLong((String)o1.getId());
				Long c2 = Long.parseLong((String)o2.getId());
				
				return c1.compareTo(c2);
			}
		};
	}
	
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
