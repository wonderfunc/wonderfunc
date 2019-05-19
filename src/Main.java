import containers.LambdaContainer;
import containers.LocalLambdaContainer;
import marshall.MarshallableInteger;
import functionRepository.interfaces.FunctionRepository;
import functionRepository.algorithmia.Algorithmia;
import stream.Pipeline;
import stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        pipelineWithAlgorithmia();
        //pipelineWithoutAlgorithmia();
    }

    private static void pipelineWithAlgorithmia() {
        List<Integer> output = new ArrayList<>();

        LambdaContainer local = new LocalLambdaContainer();
        FunctionRepository algorithmia = new Algorithmia("sim4SmnjN9o5CRPEKS4QxTJBWLg1");

        Stream<String> stream = new Stream<>(list());

        Pipeline<Integer> pipeline = stream
                .on(local)
                .map(String::length)
                .map(algorithmia.create("victcebesp/AsynchronousFunction", MarshallableInteger.class))
                .collectTo(output);

        System.out.println("created");

        Thread pipelineThread = pipeline.execute();

        try {
            pipelineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        output.forEach(System.out::println);

    }

    private static void pipelineWithoutAlgorithmia() {
        List<Integer> output = new ArrayList<>();

        LambdaContainer local = new LocalLambdaContainer();

        Stream<String> stream = new Stream<>(list());

        Pipeline<Integer> pipeline = stream
                .on(local)
                .filter(s -> s.contains("i"))
                .map(String::length)
                .collectTo(output);


        Thread streamThread = pipeline.execute();

        output.forEach(System.out::println);
    }

    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }

}
