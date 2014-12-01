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

	public <T extends FramedEdge> T reframe(Class<T> kind) {
		return graph().frameElement(element(), kind);
	}
	
}
