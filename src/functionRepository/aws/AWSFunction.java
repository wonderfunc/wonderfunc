package functionRepository.aws;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import utils.JSONDecoder;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.function.Function;

public class AWSFunction <T extends Serializable, R extends Serializable> implements Function<T, R> {

    private final String FUNCTION_REF;
    private final AWSLambda AWS_LAMBDA;

    public AWSFunction(String functionRef, AWSLambda awsLambda) {
        this.FUNCTION_REF = functionRef;
        this.AWS_LAMBDA = awsLambda;
    }

    @Override
    public R apply(T t) {
        InvokeRequest request = new InvokeRequest()
                .withFunctionName(this.FUNCTION_REF)
                .withPayload("{" +
                        "\"string\": \" " + t +" \"" +
                        "}");
        InvokeResult result  = this.AWS_LAMBDA.invoke(request);
        ByteBuffer byteBuffer = result.getPayload();
        String rawJson = null;
        try {
            rawJson = new String(byteBuffer.array(), "UTF-8");
        }catch (Exception e) {

        }
        System.out.println(rawJson);
        return (R) JSONDecoder.extractFrom(rawJson, "body", "reversed_str");
    }
}
