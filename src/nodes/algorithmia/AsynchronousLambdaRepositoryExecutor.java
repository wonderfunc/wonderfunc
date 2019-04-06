package nodes.algorithmia;

import nodes.interfaces.AsynchronousMapNode;

import java.util.function.Function;

public class AsynchronousLambdaRepositoryExecutor implements Runnable {

    private final String inputJSON;
    private final AsynchronousMapNode asynchronousMapNode;
    private final Function<String, String> function;

    public AsynchronousLambdaRepositoryExecutor(String inputJSON, AsynchronousMapNode asynchronousMapNode, Function<String, String> function) {
        this.inputJSON = inputJSON;
        this.asynchronousMapNode = asynchronousMapNode;
        this.function = function;
    }

    @Override
    public void run() {
        String output = function.apply(inputJSON);
        asynchronousMapNode.relayAll(output);
    }
}
