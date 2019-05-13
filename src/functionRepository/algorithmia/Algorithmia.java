package functionRepository.algorithmia;

import com.algorithmia.AlgorithmiaClient;
import functionRepository.AsynchronousFunction;
import node.interfaces.FunctionRepository;

import static com.algorithmia.Algorithmia.client;

public class Algorithmia implements FunctionRepository {

    private final AlgorithmiaClient client;

    public Algorithmia(String APIKey) {
        this.client = client(APIKey);
    }

    @Override
    public AsynchronousFunction create(String functionID, Class marshable) {
        return new AsynchronousFunction<Integer>(client.algo(functionID), marshable);
    }

}
