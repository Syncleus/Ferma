package com.syncleus.ferma;

import com.syncleus.ferma.framefactories.FrameFactory;
import com.syncleus.ferma.typeresolvers.TypeResolver;
import com.tinkerpop.blueprints.ThreadedTransactionalGraph;
import com.tinkerpop.blueprints.TransactionalGraph;

import java.util.Collection;

public class DelegatingFramedThreadedTransactionalGraph<G extends ThreadedTransactionalGraph> extends DelegatingFramedTransactionalGraph<G> implements WrapperFramedThreadedTransactionalGraph<G> {
  public DelegatingFramedThreadedTransactionalGraph(G delegate, FrameFactory builder, TypeResolver defaultResolver) {
    super(delegate, builder, defaultResolver);
  }

  public DelegatingFramedThreadedTransactionalGraph(G delegate) {
    super(delegate);
  }

  public DelegatingFramedThreadedTransactionalGraph(G delegate, TypeResolver defaultResolver) {
    super(delegate, defaultResolver);
  }

  public DelegatingFramedThreadedTransactionalGraph(G delegate, boolean typeResolution, boolean annotationsSupported) {
    super(delegate, typeResolution, annotationsSupported);
  }

  public DelegatingFramedThreadedTransactionalGraph(G delegate, ReflectionCache reflections, boolean typeResolution, boolean annotationsSupported) {
    super(delegate, reflections, typeResolution, annotationsSupported);
  }

  public DelegatingFramedThreadedTransactionalGraph(G delegate, Collection<? extends Class<?>> types) {
    super(delegate, types);
  }

  public DelegatingFramedThreadedTransactionalGraph(G delegate, boolean typeResolution, Collection<? extends Class<?>> types) {
    super(delegate, typeResolution, types);
  }

  @Override
  public TransactionalGraph newTransaction() {
    return this.getBaseGraph().newTransaction();
  }
}
