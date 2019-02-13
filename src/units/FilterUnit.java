package units;

import interfaces.Relay;
import interfaces.Target;
import interfaces.Unit;
import json.JSONReader;

import java.io.*;
import java.net.Socket;
import java.util.function.Predicate;

public class FilterUnit<T extends Serializable> implements Target<T>, Unit<T>, Relay<T> {

    private Predicate<T> predicate;
    private Target<T> target;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public FilterUnit(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void next(Target<T> target) {
        this.target = target;
    }

    @Override
    public void put(T data) {
        if (predicate.test(data)) relay(data);
    }

    @Override
    public void relay(T data) {
        try {
            relayWithException(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void relayWithException(T data) throws IOException {
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

    private void relayToSource(T data) {
        out.println(data);
    }

    private void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

}
