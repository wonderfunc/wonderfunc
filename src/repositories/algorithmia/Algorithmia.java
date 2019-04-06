package repositories.algorithmia;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.Algorithm;
import repositories.FunctionRepository;

import static com.algorithmia.Algorithmia.client;

public class Algorithmia implements FunctionRepository {

    private final AlgorithmiaClient client;

    public Algorithmia(String APIKey) {
        this.client = client(APIKey);
    }

    @Override
    public AsynchronousFunction create(String functionID) {
        return functionOf(client.algo(functionID));
    }

    private AsynchronousFunction functionOf(Algorithm algorithm) {
        return s -> {
            String result = "";
            try {
                result = algorithm.pipe(s).toString();
            } catch (APIException e) {
                e.printStackTrace();
            }
            return result;
        };
    }


}
