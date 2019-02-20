package connection;

import json.JSONReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Collector implements Runnable {

    @Override
    public void run() {
        try {
            startCollector();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startCollector() throws Exception {
        int port = Integer.parseInt(JSONReader.read("collectorPort"));
        ServerSocket serverSocket = new ServerSocket(port);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new CollectTask(clientSocket)).start();
        }

    }

}
