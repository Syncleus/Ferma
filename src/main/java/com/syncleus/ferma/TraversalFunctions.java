package com.syncleus.ferma;

public class TraversalFunctions {
	public static <A> TraversalFunction<A, A> identity() {
		return new TraversalFunction<A, A>() {

			@Override
			public A compute(A argument) {
				return argument;
			}
		};
	}
}
