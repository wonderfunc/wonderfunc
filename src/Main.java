import containers.AWS;
import containers.Hadoop;
import containers.LambdaContainer;
import containers.LocalLambdaContainer;
import repositories.FunctionRepository;
import repositories.algorithmia.Algorithmia;
import stream.Pipeline;
import stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> output = new ArrayList<>();

        LambdaContainer hadoop = new Hadoop("");
        LambdaContainer aws = new AWS("");
        LambdaContainer local = new LocalLambdaContainer();
        FunctionRepository algorithmia = new Algorithmia("clientId");

        Stream<String> stream = new Stream<>(list());

        Pipeline<Integer> pipeline = stream
                //.on(hadoop)
                .filter(e -> e.contains("e"))
                .map(String::length)
                //.on(aws) //contexto para las functions y predicates en adelante
                //.filter(length -> length > 5)
                //.map(each -> each + 1)
                .map(algorithmia.create("asdfa")) //repository
                .collectTo(output);


        Thread streamThread = pipeline.execute();

        output.forEach(System.out::println);
    }

    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }

}
