package stream;

import connection.CollectTask;
import operations.FilterOperation;
import operations.MapOperation;
import operations.interfaces.Operation;
import utils.JSONReader;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stream<T extends Serializable> {
    private Source source;
    private List<Operation> operations = new ArrayList<>();
    private OutputTarget target;

    public Stream(List<T> list) {
        this.source = new Source<>(list);
        operations.add(source);
    }

    @SuppressWarnings("unchecked")
    public Stream<T> filter(Predicate<T> predicate) {
        final FilterOperation<T> next = new FilterOperation<>(predicate);
        operations.get(operations.size() - 1).next(next);
        operations.add(next);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> Stream<R> map(Function<T, R> function) {
        final MapOperation<T, R> next = new MapOperation<>(function);
        operations.get(operations.size() - 1).next(next);
        operations.add(next);
        return (Stream<R>) this;
    }

    public Thread collectTo(List<T> list) {
        this.target = new OutputTarget<>(list);
        StreamThread streamThread = new StreamThread();
        source.relayAll();
        streamThread.start();
        return streamThread;
    }

    private class StreamThread extends Thread {


        @Override
        public synchronized void start() {
            super.start();

            try {
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(JSONReader.get("collectorPort")));
                while (true)
                    new Thread(new CollectTask(serverSocket.accept())).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
