package stream;

import nodes.SourceNode;
import nodes.interfaces.Node;

import java.io.Serializable;
import java.util.List;

public class Pipeline <T extends Serializable> {

    private List<Node> nodes;
    private Thread streamThread;

    public Pipeline(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Pipeline<T> deploy() {
        //TODO
        //should iterate in all nodes and depending on the node type (aws / hadoop)
        //deploy the lambda function inside
        //In local nodes, no deploy should be done
        return this;
    }

    public Thread execute() {
        //TODO
        //manage all collection within StreamThread
        //this.streamThread = new Thread(new StreamThread(outputTarget));
        //streamThread.start();
        ((SourceNode)nodes.get(0)).relayAll();
        return streamThread;
    }
}
