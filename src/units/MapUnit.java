package units;

import interfaces.Unit;
import interfaces.Relay;
import interfaces.Target;
import json.JSONReader;

import java.io.*;
import java.net.Socket;
import java.util.function.Function;

public class MapUnit<T extends Serializable, R extends Serializable> implements Target<T>, Unit<R>, Relay<R> {
    private Function<T, R> function;
    private Target<R> target;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public MapUnit(Function<T, R> function) {
        this.function = function;
    }

    @Override
    public void next(Target<R> target) {
        this.target = target;
    }

    @Override
    public void put(T data) {
        relay(function.apply(data));
    }

    @Override
    public void relay(R data) {
        try {
            relayWithException(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void relayWithException(R data) throws IOException {
        if (target != null) target.put(data);
        else {
            startConnection();
            relayToSource(data);
            stopConnection();
        }
    }

    private void startConnection() throws IOException {
        String sourceUnitIP = JSONReader.read("sourceUnitIP");
        int port = Integer.parseInt(JSONReader.read("sourUnitPort"));

        clientSocket = new Socket(sourceUnitIP, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void relayToSource(R data) {
        out.println(data);
    }

    private void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
