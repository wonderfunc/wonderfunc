import stream.Stream;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        List<Integer> output = new ArrayList<>();

        Thread thread = Stream.source(list())
                .filter(e -> e.contains("e"))
                .map(String::length)
                .collectTo(output);

        thread.wait();
    }

    private static List<String> list() {
        return new ArrayList<>();
    }

}
