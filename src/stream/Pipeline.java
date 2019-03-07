package stream;

import deployers.WonderDeployer;
import operations.interfaces.Operation;

import java.io.Serializable;
import java.util.List;

public class Pipeline <T extends Serializable> {

    private List<Operation> operations;
    private OutputTarget<T> outputTarget;
    private WonderDeployer wonderDeployer;

    public Pipeline(List<Operation> operations, OutputTarget<T> outputTarget) {
        this.operations = operations;
        this.outputTarget = outputTarget;
    }

    public Pipeline<T> deploy(WonderDeployer wonderDeployer) {
        this.wonderDeployer = wonderDeployer;
        wonderDeployer.deploy(operations, outputTarget);
        return this;
    }

    public Pipeline<T> execute() {
        wonderDeployer.execute();
        return this;
    }
}
