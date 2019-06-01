package functionRepository.algorithmia;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.algorithmia.algo.Algorithm;

import java.io.Serializable;
import java.util.function.Function;

public class AlgorithmiaFunction<T extends Serializable, R extends Serializable> implements Function<T, R> {

    private final Algorithm algorithm;

    public AlgorithmiaFunction(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public R apply(T o) {

        String result = "";
        try {
            result = algorithm.pipeJson((String)o).asJsonString();
        } catch (APIException | AlgorithmException e) {
            e.printStackTrace();
        }
        return (R) result;
    }

}
