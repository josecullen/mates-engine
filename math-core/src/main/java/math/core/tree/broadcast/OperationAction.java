package math.core.tree.broadcast;

import java.util.function.Function;

import math.core.tree.OperationNode;
@FunctionalInterface
public interface OperationAction<R> extends Function<OperationNode, R> {
	public R apply(OperationNode<?> t);
}
