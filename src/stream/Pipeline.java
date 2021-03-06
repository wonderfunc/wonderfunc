package stream;

import node.local.LocalSourceNode;
import node.interfaces.Node;
import thread.PipelineThread;

import java.io.Serializable;
import java.util.List;

public class Pipeline <T extends Serializable> {

    private List<Node> nodes;

    public Pipeline(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Thread execute() {
        Thread pipelineThread = new Thread(new PipelineThread<>((LocalSourceNode) nodes.get(0)));
        pipelineThread.start();
        return pipelineThread;
    }
}
