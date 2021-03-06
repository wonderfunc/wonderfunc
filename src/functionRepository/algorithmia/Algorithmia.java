package functionRepository.algorithmia;

import com.algorithmia.AlgorithmiaClient;
import functionRepository.interfaces.FunctionRepository;

import java.io.Serializable;
import java.util.function.Function;

import static com.algorithmia.Algorithmia.client;

public class Algorithmia implements FunctionRepository {

    private final AlgorithmiaClient client;

    public Algorithmia(String APIKey) {
        this.client = client(APIKey);
    }

    @Override
    public <T extends Serializable, O extends Serializable> Function<T, O> function(String functionID) {
        return new AlgorithmiaFunction<>(client.algo(functionID));
    }
}
