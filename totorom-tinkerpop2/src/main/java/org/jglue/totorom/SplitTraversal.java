package org.jglue.totorom;

public interface SplitTraversal<T> {



	/**
	 * Add an ExhaustMergePipe to the end of the pipeline. The one-step previous
	 * MetaPipe in the pipeline's pipes are used as the internal pipes. The
	 * pipes' emitted objects are merged where the first pipe's objects are
	 * exhausted, then the second, etc.
	 *
	 * @return the extended Pipeline
	 */
	public abstract T exhaustMerge();

	/**
	 * Add a FairMergePipe to the end of the Pipeline. The one-step previous
	 * MetaPipe in the pipeline's pipes are used as the internal pipes. The
	 * pipes' emitted objects are merged in a round robin fashion.
	 *
	 * @return the extended Pipeline
	 */
	public abstract T fairMerge();
	
}
