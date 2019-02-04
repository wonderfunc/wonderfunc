import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Stream<Integer> stream =
                Stream.source(list()).map(String::length);
    }

    private static List<String> list() {
        return new ArrayList<>();
    }

}
