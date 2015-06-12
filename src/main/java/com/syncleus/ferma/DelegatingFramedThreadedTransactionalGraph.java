package com.syncleus.ferma;

import com.syncleus.ferma.framefactories.FrameFactory;
import com.syncleus.ferma.typeresolvers.TypeResolver;
import com.tinkerpop.blueprints.ThreadedTransactionalGraph;
import com.tinkerpop.blueprints.TransactionalGraph;

import java.util.Collection;

public class DelegatingFramedThreadedTransactionalGraph<G extends ThreadedTransactionalGraph> extends DelegatingFramedTransactionalGraph<G> implements WrapperFramedThreadedTransactionalGraph<G> {
  public DelegatingFramedThreadedTransactionalGraph(final G delegate, final FrameFactory builder, final TypeResolver defaultResolver) {
    super(delegate, builder, defaultResolver);
  }

  public DelegatingFramedThreadedTransactionalGraph(final G delegate) {
    super(delegate);
  }

  public DelegatingFramedThreadedTransactionalGraph(final G delegate, final TypeResolver defaultResolver) {
    super(delegate, defaultResolver);
  }

  public DelegatingFramedThreadedTransactionalGraph(final G delegate, final boolean typeResolution, final boolean annotationsSupported) {
    super(delegate, typeResolution, annotationsSupported);
  }

  public DelegatingFramedThreadedTransactionalGraph(final G delegate, final ReflectionCache reflections, final boolean typeResolution, final boolean annotationsSupported) {
    super(delegate, reflections, typeResolution, annotationsSupported);
  }

  public DelegatingFramedThreadedTransactionalGraph(final G delegate, final Collection<? extends Class<?>> types) {
    super(delegate, types);
  }

  public DelegatingFramedThreadedTransactionalGraph(final G delegate, final boolean typeResolution, final Collection<? extends Class<?>> types) {
    super(delegate, typeResolution, types);
  }

  @Override
  public TransactionalGraph newTransaction() {
    return this.getBaseGraph().newTransaction();
  }
}
