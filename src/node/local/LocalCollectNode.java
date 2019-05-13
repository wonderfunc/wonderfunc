package node.local;

import message.DataMessage;
import message.Message;
import node.interfaces.CollectNode;
import stream.OutputTarget;

import java.io.Serializable;

public class LocalCollectNode<T extends Serializable> implements CollectNode {

    private final OutputTarget<T> outputTarget;

    public LocalCollectNode(OutputTarget outputTarget) {
        this.outputTarget = outputTarget;
    }

    @Override
    public void push(Message message) {
        if (message instanceof DataMessage)
            outputTarget.add(((DataMessage<T>)message).data());
    }
}
