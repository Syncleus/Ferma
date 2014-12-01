/**
 * Copyright 2014-Infinity Bryn Cooke
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * This project is derived from code in the Tinkerpop project under the following licenses:
 *
 * Tinkerpop3
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Tinkerpop2
 * Copyright (c) 2009-Infinity, TinkerPop [http://tinkerpop.com]
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the TinkerPop nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL TINKERPOP BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jglue.totorom;

import org.junit.Test;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;

public class TestPerformance {
	private int iterations = 1;
	private static final MetricRegistry metrics = new MetricRegistry();

	public static void main(String[] args) {
		TestPerformance test = new TestPerformance();
		test.iterations = 100000;
		for (int count = 0; count < 10; count++) {
			test.testTraversalPerformance();
			test.testIndexPerformance();
			test.testFilterPerformance();
		}
	}

	@Test
	public void testTraversalPerformance() {

		TinkerGraph t = TinkerGraphFactory.createTinkerGraph();

		FramedGraph f = new FramedGraph(t);

		Timer timer = metrics.timer("gremlin");

		Context time = timer.time();
		for (int count = 0; count < iterations; count++) {
			GremlinPipeline g = new GremlinPipeline(t);
			g.V().both().both().both().toList();
		}
		long nanoseconds = time.stop();
		System.out.println("Iterate over all GremlinPipeline " + nanoseconds / 1000000);
		time = timer.time();
		for (int count = 0; count < iterations; count++) {
			f.V().both().both().both().toList();
		}
		nanoseconds = time.stop();
		System.out.println("Iterate over all Totorom " + nanoseconds / 1000000);
	}

	@Test
	public void testIndexPerformance() {

		TinkerGraph t = TinkerGraphFactory.createTinkerGraph();
		t.createKeyIndex("name", Vertex.class);

		FramedGraph f = new FramedGraph(t);

		Timer timer = metrics.timer("gremlin");

		Vertex lastVertex = null;
		for (int count = 0; count < iterations; count++) {
			Vertex v = t.addVertex(null);
			v.setProperty("name", "name" + count);
			if (lastVertex != null) {
				v.addEdge("test", lastVertex);
			}
			lastVertex = v;

		}

		Context time = timer.time();
		for (int count = 0; count < iterations; count++) {
			GremlinPipeline g = new GremlinPipeline(t);
			g.V("name", "name" + count).both().both().both().toList();
		}
		long nanoseconds = time.stop();
		System.out.println("Iterate with index lookup GremlinPipeline " + nanoseconds / 1000000);
		time = timer.time();
		for (int count = 0; count < iterations; count++) {
			f.V().has("name", "name" + count).both().both().both().toList();
		}
		nanoseconds = time.stop();
		System.out.println("Iterate with index lookup Totorom " + nanoseconds / 1000000);

	}

	@Test
	public void testFilterPerformance() {

		TinkerGraph t = TinkerGraphFactory.createTinkerGraph();

		FramedGraph f = new FramedGraph(t);

		Timer timer = metrics.timer("gremlin");
		Context time = timer.time();
		for (int count = 0; count < iterations; count++) {
			GremlinPipeline g = new GremlinPipeline(t);
			g.V().both().both().both().filter(new PipeFunction<Vertex, Boolean>() {

				@Override
				public Boolean compute(Vertex argument) {

					return "java".equals(argument.getProperty("lang"));
				}

			}).toList();
		}
		long nanoseconds = time.stop();
		System.out.println("Iterate with filter GremlinPipeline " + nanoseconds / 1000000);

		time = timer.time();
		for (int count = 0; count < iterations; count++) {
			f.V().both().both().both().filter(new TraversalFunction<TVertex, Boolean>() {

				@Override
				public Boolean compute(TVertex argument) {
					return "java".equals(argument.getProperty("name"));
				}
			}).toList();
		}
		nanoseconds = time.stop();
		System.out.println("Iterate with filter Totorom " + nanoseconds / 1000000);

	}
}
