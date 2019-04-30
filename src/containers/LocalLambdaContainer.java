package containers;

import message.Message;
import nodes.interfaces.AsynchronousMapNode;
import nodes.interfaces.CollectNode;
import nodes.interfaces.SynchronousMapNode;
import nodes.local.LocalCollectNode;
import nodes.local.LocalFilterNode;
import nodes.local.LocalMapNode;
import repositories.AsynchronousFunction;
import stream.OutputTarget;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class LocalLambdaContainer implements LambdaContainer {
    @Override
    public LocalFilterNode createNodeFor(Predicate predicate) {
        return new LocalFilterNode<>(predicate);
    }

    @Override
    public SynchronousMapNode createNodeFor(Function function) {
        return new LocalMapNode<>(function);
    }

    @Override
    public AsynchronousMapNode createNodeFor(AsynchronousFunction function) {

        /*
            TODO
            Si se quisiera tener varios lambda repositories, se tendria que prguntar
            por el tipo de AsynchronousFunction que llega. En este caso AlgorithmiaFunction

            Proponer que el serialize y deserialize de alguna forma esté relacionado con la Asynchronous
            function que viene por parámetro.
         */

        return new AsynchronousMapNode(function) {
            @Override
            protected String serialize(List list) {
                /*
                    TODO
                    solve clash error to receive List<Message>
                    and avoid casting each in list to Message List<Message>
                 */
                return null;
            }

            @Override
            protected List<Message> deserialize(String output) {
                return null;
            }
        };
    }

    @Override
    public CollectNode createNodeFor(OutputTarget outputTarget) {
        return new LocalCollectNode(outputTarget);
    }
}
