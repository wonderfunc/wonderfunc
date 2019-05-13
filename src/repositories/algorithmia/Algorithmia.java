package repositories.algorithmia;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.Algorithm;
import repositories.AsynchronousFunction;
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
        return new AsynchronousFunction<Integer>() {
            @Override
            synchronized public String marshall(Integer messageData) {
                return messageData.toString();
            }

            @Override
            synchronized public Integer unmarshall(String messageData) {
                return Integer.parseInt(messageData);
            }

            @Override
            public Object apply(Object o) {

                String result = "";
                try {
                    result = algorithm.pipe(o).asJsonString();
                } catch (APIException | AlgorithmException e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
    }


}
