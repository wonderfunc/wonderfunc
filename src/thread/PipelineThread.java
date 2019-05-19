package thread;

import node.local.LocalSourceNode;

import java.io.Serializable;

public class PipelineThread<T extends Serializable> implements Runnable {

    private final LocalSourceNode sourceNode;

    public PipelineThread(LocalSourceNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    @Override
    public void run() {
        sourceNode.relayAll();
    }

}
