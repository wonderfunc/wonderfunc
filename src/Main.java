import containers.LocalNodeContainer;
import containers.NodeContainer;
import expressionExecutor.RemoteExpressionExecutor;
import functionRepository.algorithmia.Algorithmia;
import functionRepository.interfaces.FunctionRepository;
import marshall.CommentMarshalling;
import stream.Pipeline;
import stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //pipelineWithoutAlgorithmia();
        useCase();
    }

    private static void pipelineWithoutAlgorithmia() throws InterruptedException {
        List<Integer> output = new ArrayList<>();

        NodeContainer local = new LocalNodeContainer();

        Stream<String> stream = new Stream<>(list());

        Pipeline<Integer> pipeline = stream
                .on(local)
                    .filter(s -> s.contains("y"))
                    .map(String::length)
                .collectTo(output);


        Thread pipelineThread = pipeline.execute();

        pipelineThread.join();

        output.forEach(System.out::println);
    }

    @SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
    private static void useCase() throws InterruptedException {
        List<Boolean> output = new ArrayList<>();

        FunctionRepository algorithmia = new Algorithmia("sim4SmnjN9o5CRPEKS4QxTJBWLg1");

        Function<String, String> sentimentAnalysis = algorithmia.function("nlp/SentimentAnalysis/1.0.5");

        Stream<String> stream = new Stream<>(list());

        Pipeline<Boolean> pipeline = stream
                .on(new LocalNodeContainer())
                    .map(s -> sentimentAnalysis.apply(s))
                        .with(new RemoteExpressionExecutor(new CommentMarshalling()))
                    .map(d -> d <= 0)
                .collectTo(output);

        Thread pipelineThread = pipeline.execute();

        pipelineThread.join();

        for (int i = 0; i < output.size(); i++) if (output.get(i)) System.out.println(list().get(i));

        System.exit(0);
    }

    private static List<String> list() {
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
