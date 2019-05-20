import containers.LambdaContainer;
import containers.LocalLambdaContainer;
import marshall.MarshallableComment;
import marshall.MarshallableInteger;
import functionRepository.interfaces.FunctionRepository;
import functionRepository.algorithmia.Algorithmia;
import stream.Pipeline;
import stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        pipelineWithAlgorithmia();
        //pipelineWithoutAlgorithmia();
    }

    private static void pipelineWithAlgorithmia() throws InterruptedException {
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

        pipelineThread.join();

        output.forEach(System.out::println);

    }

    private static void pipelineWithoutAlgorithmia() throws InterruptedException {
        List<Integer> output = new ArrayList<>();

        LambdaContainer local = new LocalLambdaContainer();

        Stream<String> stream = new Stream<>(list());

        Pipeline<Integer> pipeline = stream
                .on(local)
                .filter(s -> s.contains("i"))
                .map(String::length)
                .collectTo(output);


        Thread pipelineThread = pipeline.execute();

        pipelineThread.join();

        output.forEach(System.out::println);
    }

    private static void useCase() throws InterruptedException {
        List<Boolean> output = new ArrayList<>();

        LambdaContainer local = new LocalLambdaContainer();
        FunctionRepository algorithmia = new Algorithmia("sim4SmnjN9o5CRPEKS4QxTJBWLg1");

        Stream<String> stream = new Stream<>(comments());

        Pipeline<Integer> pipeline = stream
                .on(local)
                .map(algorithmia.create("nlp/SentimentAnalysis/1.0.5", MarshallableComment.class))
                .map(d -> (Double) d <= 0)
                .collectTo(output);

        Thread pipelineThread = pipeline.execute();

        pipelineThread.join();

        for (int i = 0; i < output.size(); i++) if (output.get(i)) System.out.println(comments().get(i));

        System.exit(0);
    }

    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }

    private static List<String> comments() {
        return Arrays.asList("I hate it!",
                "I really like the final design. It is very ergonomic",
                "This product doesn't work at all...",
                "I enjoy using this product most of the time",
                "I wish I had known this before",
                "This is awesome!",
                "I will never stop using this",
                "This is pointless",
                "I don't like the way this works...");
    }

}
