import containers.LambdaContainer;
import containers.LocalLambdaContainer;
import marshall.MarshallableInteger;
import node.interfaces.FunctionRepository;
import functionRepository.algorithmia.Algorithmia;
import stream.Pipeline;
import stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> output = new ArrayList<>();

        LambdaContainer local = new LocalLambdaContainer();
        FunctionRepository algorithmia = new Algorithmia("sim4SmnjN9o5CRPEKS4QxTJBWLg1");

        Stream<String> stream = new Stream<>(list());

        Pipeline<Integer> pipeline = stream
                .on(local)
                .map(String::length)
                .map(algorithmia.create("victcebesp/AsynchronousFunction", MarshallableInteger.class))
                .collectTo(output);


        Thread streamThread = pipeline.execute();

        output.forEach(System.out::println);
    }

    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }

}
