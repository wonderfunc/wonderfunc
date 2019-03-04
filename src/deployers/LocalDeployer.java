package deployers;

import connection.StreamThread;
import operations.interfaces.Operation;
import stream.OutputTarget;
import stream.Source;

import java.util.List;

public class LocalDeployer implements WonderDeployer {

    @Override
    public Thread execute(List<Operation> operations, OutputTarget outputTarget) {
        Thread streamThread = new Thread(new StreamThread<>(outputTarget));
        streamThread.start();
        ((Source)operations.get(0)).relayAll();
        return streamThread;
    }
}
