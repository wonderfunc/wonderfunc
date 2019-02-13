import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> output = Stream.source(list())
                .map(String::length)
                .filter(e -> e > 4)
                .collect();
    }

    private static List<String> list() {
        return Arrays.asList("hello world this is an example input".split(" "));
    }

}
