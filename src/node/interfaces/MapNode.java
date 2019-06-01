package node.interfaces;

import expressionExecutor.interfaces.ExpressionExecutor;
import functionRepository.interfaces.Dispatcher;

import java.io.Serializable;
import java.util.function.Function;

public interface MapNode <T extends Serializable, R extends Serializable> extends Node<R>, Target<T>, Relay<R>, Dispatcher<R> {
    void setExpressionExecutor(ExpressionExecutor<T, R> expressionExecutor);
    Function<T, R> function();
}
