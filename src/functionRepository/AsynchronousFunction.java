package functionRepository;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.algorithmia.algo.Algorithm;
import marshall.Marshallable;

import java.io.Serializable;
import java.util.function.Function;

public class AsynchronousFunction <T extends Serializable> implements Function {

    private final Marshallable<T> marshallable;
    private final Algorithm algorithm;

    public AsynchronousFunction(Algorithm algorithm, Class marshableClass) {
        this.algorithm = algorithm;
        this.marshallable = instantiateMarshable(marshableClass);
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

    public Marshallable<T> marshallable() {
        return marshallable;
    }

    private Marshallable<T> instantiateMarshable(Class marshallableClass)  {
        Marshallable marshallable = null;
        try {
            marshallable = (Marshallable<T>)marshallableClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return marshallable;
    }

}
