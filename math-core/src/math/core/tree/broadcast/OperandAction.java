package math.core.tree.broadcast;

import java.util.function.Function;

import math.core.tree.OperandNode;

@FunctionalInterface
public interface OperandAction<R> extends Function<OperandNode, R> {
	public R apply(OperandNode t);
}
