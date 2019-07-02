package functionRepository.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import functionRepository.interfaces.FunctionRepository;
import marshall.Marshalling;
import utils.AWSCredentials;

import java.io.Serializable;
import java.util.function.Function;

public class AWSRepository implements FunctionRepository {

    private final String ACCESS_KEY_ID;
    private final String SECRET_ACCESS_KEY;
    private Regions region;

    public AWSRepository(AWSCredentials awsCredentials) {
        this.ACCESS_KEY_ID = awsCredentials.getACCESS_KEY_ID();
        this.SECRET_ACCESS_KEY = awsCredentials.getSECRET_ACCESS_KEY();
    }

    public AWSRepository onRegion(String regionName) {
        this.region = Regions.fromName(regionName);
        return this;
    }

    @Override
    public <T extends Serializable, O extends Serializable> Function<T, O> function(String functionName, Marshalling marshalling) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(this.ACCESS_KEY_ID, this.SECRET_ACCESS_KEY);
        AWSLambda client = AWSLambdaClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
        return new AWSFunction<>(functionName, client, marshalling);
    }
}
