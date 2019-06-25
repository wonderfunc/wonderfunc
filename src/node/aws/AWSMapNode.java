package node.aws;

import expressionExecutor.interfaces.ExpressionExecutor;
import message.Message;
import node.interfaces.MapNode;
import node.interfaces.Target;

import java.io.Serializable;
import java.util.function.Function;

public class AWSMapNode<T extends Serializable, R extends Serializable> implements MapNode<T, R> {

    public <T extends Serializable> AWSMapNode(Function<T, R> function) {

    }

    @Override
    public void setExpressionExecutor(ExpressionExecutor<T, R> expressionExecutor) {

    }

    @Override
    public Function<T, R> function() {
        return null;
    }

    @Override
    public void dispatch(Message<R> output) {

    }

    @Override
    public void next(Target<R> target) {

    }

    @Override
    public void relay(Message<R> message) {

    }

    @Override
    public void push(Message<T> message) {

    }
}
