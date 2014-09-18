package org.jglue.totorom.internal;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.jglue.totorom.Path;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.transform.TransformPipe;

public class PathPipe<S> extends AbstractPipe<S, List> implements TransformPipe<S, List> {

    private final PipeFunction[] pathFunctions;

    public PathPipe(final PipeFunction... pathFunctions) {
        if (pathFunctions.length == 0) {
            this.pathFunctions = null;
        } else {
            this.pathFunctions = pathFunctions;
        }
    }

    public void setStarts(final Iterator<S> starts) {
        super.setStarts(starts);
        this.enablePath(true);
    }

    public List processNextStart() {
        if (this.starts instanceof Pipe) {
            this.starts.next();
            final List path = ((Pipe) this.starts).getCurrentPath();
            if (null == this.pathFunctions) {
                return path;
            } else {
                final List closedPath = new Path();
                int nextFunction = 0;
                for (final Object object : path) {
                    closedPath.add(this.pathFunctions[nextFunction].compute(object));
                    nextFunction = (nextFunction + 1) % this.pathFunctions.length;
                }
                return closedPath;
            }
        } else {
            throw new NoSuchElementException("The start of this pipe was not a pipe");
        }
    }


}