import deployers.localdeployer.LocalDeployer;
import stream.Pipeline;
import stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> output = new ArrayList<>();

        Stream<Integer> stream = new Stream<>(list())
                .filter(e -> e.contains("e"))
                .map(String::length);

        Pipeline pipeline = stream
                .collectTo(output);

        pipeline.deploy(new LocalDeployer()).execute();

        output.forEach(System.out::println);
    }

    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }

}
