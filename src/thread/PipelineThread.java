package thread;

import node.SourceNode;

import java.io.Serializable;

public class PipelineThread<T extends Serializable> implements Runnable {

    private final SourceNode sourceNode;

    public PipelineThread(SourceNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    @Override
    public void run() {
        sourceNode.relayAll();
    }

}
