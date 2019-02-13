package units;

import interfaces.Relay;
import interfaces.Target;
import interfaces.Unit;
import json.JSONReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SourceUnit<I extends Serializable> implements Relay<I>, Unit<I> {

    private final Iterable<I> iterable;
    private Target<I> target;

    public SourceUnit(Iterable<I> iterable) {
        this.iterable = iterable;
    }

    @Override
    public void next(Target<I> target) {
        this.target = target;
    }

    public void relayAll() {
        try {
            for(I data : iterable) relay(data);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void relay(I data) {
        target.put(data);
    }

    private void start() throws IOException {

        int port = Integer.parseInt(JSONReader.read("sourUnitPort"));

        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) out.println(inputLine);
    }
}
