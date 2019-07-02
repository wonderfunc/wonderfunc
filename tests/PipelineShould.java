import containers.LocalNodeContainer;
import expressionExecutor.RemoteExpressionExecutor;
import functionRepository.algorithmia.Algorithmia;
import functionRepository.aws.AWSRepository;
import functionRepository.interfaces.FunctionRepository;
import marshall.CommentMarshalling;
import marshall.StringReverseMarshalling;
import org.junit.Test;
import stream.Stream;
import utils.CredentialProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PipelineShould {

    private final String ALGORITHMIA_API_KEY = "sim4SmnjN9o5CRPEKS4QxTJBWLg1";

    @Test
    public void returns_empty_list_when_input_list_is_empty_and_no_nodes_are_added() throws InterruptedException {

        List<String> output = new ArrayList<>();

        new Stream<>(emptyList()).collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void returns_empty_list_when_input_list_is_empty_and_filter_node_is_added() throws InterruptedException {

        List<String> output = new ArrayList<>();

        new Stream<>(emptyList())
                .filter(e -> e.contains("e"))
                .collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void returns_empty_list_when_input_list_is_empty_and_map_node_is_added() throws InterruptedException {

        List<Integer> output = new ArrayList<>();

        new Stream<>(emptyList())
                .map(String::length)
                .collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void returns_empty_list_when_input_list_is_empty_and_map_node_and_filter_node_is_added() throws InterruptedException {

        List<Integer> output = new ArrayList<>();

        new Stream<>(emptyList())
                .filter(e -> e.contains("e"))
                .map(String::length)
                .collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void given_local_lambda_container_returns_empty_list_when_input_list_is_empty_and_no_nodes_are_added() throws InterruptedException {

        List<String> output = new ArrayList<>();

        new Stream<>(emptyList())
                .on(new LocalNodeContainer())
                .collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void given_local_lambda_container_returns_empty_list_when_input_list_is_empty_and_filter_node_is_added() throws InterruptedException {

        List<String> output = new ArrayList<>();

        new Stream<>(emptyList())
                .on(new LocalNodeContainer())
                .filter(e -> e.contains("e"))
                .collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void given_local_lambda_container_returns_empty_list_when_input_list_is_empty_and_map_node_is_added() throws InterruptedException {

        List<Integer> output = new ArrayList<>();

        new Stream<>(emptyList())
                .on(new LocalNodeContainer())
                .map(String::length)
                .collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void given_local_lambda_container_returns_empty_list_when_input_list_is_empty_and_map_node_and_filter_node_is_added() throws InterruptedException {

        List<Integer> output = new ArrayList<>();

        new Stream<>(emptyList())
                .on(new LocalNodeContainer())
                .filter(e -> e.contains("e"))
                .map(String::length)
                .collectTo(output)
                .execute()
                .join();

        assertEquals(0, output.size());
    }

    @Test
    public void returns_equal_list_when_no_nodes_are_added() throws InterruptedException {

        List<String> output = new ArrayList<>();

        new Stream<>(list()).collectTo(output)
                .execute()
                .join();

        assertEquals(list().size(), output.size());
    }

    @Test
    public void returns_a_six_strings_list_when_filter_node_is_added() throws InterruptedException {

        List<String> output = new ArrayList<>();

        new Stream<>(list())
                .filter(e -> e.contains("e"))
                .collectTo(output)
                .execute()
                .join();

        assertEquals(6, output.size());
        assertThat(output, is(Arrays.asList("Hello", "prove", "check", "everything", "went", "correctly")));
    }

    @Test
    public void returns_a_six_integers_list_when_filter_and_map_node_are_added() throws InterruptedException {

        List<Integer> output = new ArrayList<>();

        new Stream<>(list())
                .filter(e -> e.contains("e"))
                .map(String::length)
                .collectTo(output)
                .execute()
                .join();

        assertEquals(6, output.size());
        assertThat(output, is(Arrays.asList(5, 5, 5, 10, 4, 9)));
    }

    @Test
    public void return_a_list_of_doubles_using_algorithmia_function_repository() throws InterruptedException {

        List<Double> output = new ArrayList<>();

        FunctionRepository algorithmia = new Algorithmia(ALGORITHMIA_API_KEY);
        Function<String, String> sentimentAnalysis =
                algorithmia.function("nlp/SentimentAnalysis/1.0.5", new CommentMarshalling());

        new Stream<>(comments())
                .on(new LocalNodeContainer())
                    .map(sentimentAnalysis)
                        .with(new RemoteExpressionExecutor(new CommentMarshalling()))
                .collectTo(output)
                .execute()
                .join();

        assertEquals(9, output.size());
        assertThat(output, is(Arrays.asList(-0.6114, 0.4201, 0.0, 0.4939, 0.4019, 0.6588, 0.2235, 0.0, -0.2755)));
    }

    @Test
    public void invoke_a_function_on_aws() throws InterruptedException {
        FunctionRepository aws =
                new AWSRepository(CredentialProvider
                        .awsCredentials("C:\\Users\\TDRS\\IdeaProjects\\wonderfunc\\creds\\rootkey.csv"))
                .onRegion("us-east-1");

        Function<String, String> awsReverseStrFunction = aws
                .function("from-java-callable-function", new StringReverseMarshalling());

        assertEquals("olleh", awsReverseStrFunction.apply("hello"));
    }

    @Test
    public void use_a_lambda_from_aws_in_a_stream() throws InterruptedException {
        FunctionRepository aws =
                new AWSRepository(CredentialProvider
                        .awsCredentials("C:\\Users\\TDRS\\IdeaProjects\\wonderfunc\\creds\\rootkey.csv"))
                        .onRegion("us-east-1");

        Function<String, String> awsReverseStrFunction = aws
                .function("from-java-callable-function", new StringReverseMarshalling());

        List<String> list = new ArrayList<>() {{
            add("Hello");
            add("Bye");
        }};

        List<String> result = new ArrayList<>();
        new Stream<>(list)
                .map(awsReverseStrFunction)
                .collectTo(result)
                .execute()
                .join();
        assertEquals(new ArrayList<>(){{
            add("olleH");
            add("eyB");
        }}, result);
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

    private static List<String> emptyList() {
        return new ArrayList<>();
    }

}
