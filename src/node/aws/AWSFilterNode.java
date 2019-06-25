package node.aws;

import message.DataMessage;
import message.Message;
import node.interfaces.FilterNode;
import node.interfaces.Target;

import java.io.Serializable;
import java.util.function.Predicate;

public class AWSFilterNode<T extends Serializable> implements FilterNode<T> {

    public <T extends Serializable> AWSFilterNode(Predicate<T> predicate) {

    }

    @Override
    public boolean test(DataMessage<T> dataMessage) {
        return false;
    }

    @Override
    public void next(Target<T> target) {

    }

    @Override
    public void relay(Message<T> message) {

    }

    @Override
    public void push(Message<T> message) {

    }
}
