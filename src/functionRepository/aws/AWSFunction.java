package functionRepository.aws;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import marshall.Marshalling;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.function.Function;

public class AWSFunction<T, R> implements Function<T, R> {

    private final String FUNCTION_ID;
    private final AWSLambda AWS_LAMBDA_CLIENT;
    private final Marshalling marshalling;


    public AWSFunction(String functionID, AWSLambda awsLambdaClient, Marshalling marshalling) {
        this.FUNCTION_ID = functionID;
        this.AWS_LAMBDA_CLIENT = awsLambdaClient;
        this.marshalling = marshalling;
    }

    @Override
    public R apply(T t) {
        InvokeRequest request = new InvokeRequest()
                .withFunctionName(this.FUNCTION_ID)
                .withPayload(marshalling.marshall( (Serializable) t));
        InvokeResult result  = this.AWS_LAMBDA_CLIENT.invoke(request);
        ByteBuffer byteBuffer = result.getPayload();
        String rawJson = null;
        try {
            rawJson = new String(byteBuffer.array(), "UTF-8");
        } catch (Exception e) {

        }
        return (R) marshalling.unmarshall(rawJson);
    }
}
