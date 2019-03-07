package deployers;

import operations.interfaces.Operation;
import stream.OutputTarget;

import java.util.List;

public interface WonderDeployer {

    void execute();
    void deploy(List<Operation> operations, OutputTarget outputTarget);
}
