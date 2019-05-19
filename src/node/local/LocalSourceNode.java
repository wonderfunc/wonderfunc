package node.local;

import message.DataMessage;
import message.EndOfStreamMessage;
import message.Message;
import node.interfaces.Node;
import node.interfaces.Relay;
import node.interfaces.SourceNode;
import node.interfaces.Target;

import java.io.Serializable;
import java.util.List;

public class LocalSourceNode<T extends Serializable> implements SourceNode<T> {

    private Target<T> target;
    private final List<T> inputList;

    public LocalSourceNode(List<T> nodes) {
        this.inputList = nodes;
    }

    @Override
    public void relayAll() {
        for (T inputData : inputList) relay(new DataMessage<>(inputData));
        relay(new EndOfStreamMessage());
    }

    @Override
    public void next(Target<T> target) {
        this.target = target;
    }

    @Override
    public void relay(Message<T> message) {
        target.push(message);
    }
}
