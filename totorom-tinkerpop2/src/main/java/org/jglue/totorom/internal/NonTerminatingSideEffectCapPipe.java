package org.jglue.totorom.internal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.sideeffect.SideEffectPipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.AbstractMetaPipe;
import com.tinkerpop.pipes.util.FastNoSuchElementException;
import com.tinkerpop.pipes.util.MetaPipe;
import com.tinkerpop.pipes.util.PipeHelper;

public class NonTerminatingSideEffectCapPipe<S, T> extends AbstractMetaPipe<S, S> implements MetaPipe {

	private final SideEffectPipe<S, T> pipeToCap;
	
	private PipeFunction<T, ?> sideEffectFunction;

	public NonTerminatingSideEffectCapPipe(final SideEffectPipe<S, T> pipeToCap, final PipeFunction<T, ?> sideEffectFunction) {
		this.pipeToCap = pipeToCap;
		this.sideEffectFunction = sideEffectFunction;
	}

	public void setStarts(final Iterator<S> starts) {
		this.pipeToCap.setStarts(starts);
	}

	protected S processNextStart() {
		if (this.pipeToCap instanceof SideEffectPipe.LazySideEffectPipe) {
			S next = this.pipeToCap.next();
			sideEffectFunction.compute(this.pipeToCap.getSideEffect());
			return next;
		} else {

			try {
				return this.pipeToCap.next();
			} catch (final NoSuchElementException e) {
				sideEffectFunction.compute(this.pipeToCap.getSideEffect());
				throw FastNoSuchElementException.instance();
			}
		
		}
	}

	public List getCurrentPath() {
		if (this.pathEnabled) {
			final List list = this.pipeToCap.getCurrentPath();
			list.add(this.currentEnd);
			return list;
		} else {
			throw new RuntimeException(Pipe.NO_PATH_MESSAGE);
		}
	}

	public String toString() {
		return PipeHelper.makePipeString(this, this.pipeToCap);
	}

	public List<Pipe> getPipes() {
		return Arrays.asList((Pipe) this.pipeToCap);
	}

	public void reset() {
		super.reset();
	}
}