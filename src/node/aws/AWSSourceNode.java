package node.aws;

import message.Message;
import node.interfaces.SourceNode;
import node.interfaces.Target;

import java.io.Serializable;

public class AWSSourceNode<T extends Serializable> implements SourceNode<T> {

    @Override
    public void relayAll() {

    }

    @Override
    public void next(Target<T> target) {

    }

    @Override
    public void relay(Message<T> message) {

    }
}
