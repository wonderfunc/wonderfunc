package stream;

import deployers.WonderDeployer;
import operations.interfaces.Operation;

import java.io.Serializable;
import java.util.List;

public class Pipeline <T extends Serializable> {

    private List<Operation> operations;
    private OutputTarget<T> outputTarget;

    public Pipeline(List<Operation> operations, OutputTarget<T> outputTarget) {
        this.operations = operations;
        this.outputTarget = outputTarget;
    }

    public void deploy(WonderDeployer wonderDeployer) {
        wonderDeployer.execute(operations, outputTarget);
    }
}
