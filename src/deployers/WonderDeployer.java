package deployers;

import operations.interfaces.Operation;
import stream.OutputTarget;

import java.util.List;

public interface WonderDeployer {

    Thread execute(List<Operation> operations, OutputTarget outputTarget);
}
