package functionRepository;

import functionRepository.interfaces.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LambdaRepositoryExecutor implements Runnable {

    private final String inputJSON;
    private final Function<String, String> function;
    private List<Listener> listeners;

    public LambdaRepositoryExecutor(String inputJSON, Function<String, String> function) {
        this.inputJSON = inputJSON;
        this.function = function;
        this.listeners = new ArrayList<>();
    }

    public LambdaRepositoryExecutor addOnFinishListener(Listener listener){
        listeners.add(listener);
        return this;
    }

    @Override
    public void run() {
        final String output = function.apply(inputJSON);
        listeners.forEach(h -> h.onFinish(output));
    }

}
