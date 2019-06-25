package node.aws;

import message.Message;
import node.interfaces.CollectNode;
import stream.OutputTarget;

import java.io.Serializable;

public class AWSCollectNode<T extends Serializable> implements CollectNode<T> {

    public <T extends Serializable> AWSCollectNode(OutputTarget<T> outputTarget) {
    }

    @Override
    public void push(Message<T> message) {

    }
}
