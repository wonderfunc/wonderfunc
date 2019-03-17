import containers.AWS;
import containers.Hadoop;
import containers.LambdaContainer;
import repositories.Algorithmia;
import stream.Pipeline;
import repositories.Repository;
import stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> output = new ArrayList<>();

        LambdaContainer hadoop = new Hadoop("");
        LambdaContainer aws = new AWS("");
        Repository algorithmia = new Algorithmia("clientId");

        //diferentes nodos dependiendo del contexto
        Pipeline pipeline = new Stream<>(list())
                .on(hadoop)
                .filter(e -> e.contains("e"))
                .map(String::length)
                .on(aws) //contexto para las functions y predicates en adelante
                .filter(length -> length > 5)
                .map(each -> each + 1)
                .collectTo(output);
                //.map(algorithmia.get("asdfa")) //repository


        pipeline.deploy(null).execute();

        output.forEach(System.out::println);
    }

    private static List<String> list() {
        return Arrays.asList("Hello this is a prove to check if everything went correctly".split(" "));
    }

}
