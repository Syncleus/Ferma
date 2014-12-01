/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/

/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import java.util.Comparator;

/**
 * Useful comparators when dealing with framed elements
 *
 */
public class Comparators {

    /**
     * Compare by Id.
     * 
     * @return
     */
    public static <N extends ElementFrame> Comparator<N> id() {
        return new Comparator<N>() {
            @Override
            public int compare(final N t, final N t1) {
                final Comparable c1 = t.getId();
                final Comparable c2 = t1.getId();

                return c1.compareTo(c2);
            }
        };
    }

    /**
     * Compare by id parsed as a long (Useful for tinkergraph)
     * 
     * @return
     */
    public static <N extends ElementFrame> Comparator<N> idAsLong() {
        return new Comparator<N>() {
            @Override
            public int compare(final N t, final N t1) {
                final Long c1 = Long.parseLong((String) t.getId());
                final Long c2 = Long.parseLong((String) t1.getId());

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
    public static <N extends ElementFrame> Comparator<N> property(final String property) {
        return new Comparator<N>() {
            @Override
            public int compare(final N t, final N t1) {
                final Comparable c1 = t.getProperty(property);
                final Comparable c2 = t1.getProperty(property);

                return c1.compareTo(c2);
            }
        };
    }

}
