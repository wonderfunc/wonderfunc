package deployers.localdeployer;

import connection.StreamThread;
import deployers.WonderDeployer;
import operations.interfaces.Operation;
import stream.OutputTarget;
import stream.Source;

import java.util.List;

public class LocalDeployer implements WonderDeployer {

    private Thread streamThread;
    private List<Operation> operations;
    private OutputTarget outputTarget;

    @Override
    public void deploy(List<Operation> operations, OutputTarget outputTarget) {
        this.streamThread = new Thread(new StreamThread<>(outputTarget));
        this.operations = operations;
        this.outputTarget = outputTarget;
    }

    @Override
    public void execute() {
        streamThread.start();
        ((Source)operations.get(0)).relayAll();
    }
}
