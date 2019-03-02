package connection;

import utils.JSONReader;

import java.io.IOException;
import java.net.ServerSocket;

public class StreamThread implements Runnable {

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(JSONReader.get("collectorPort")));
            while (true)
                new Thread(new CollectTask(serverSocket.accept(), this)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void end() {
        try {
            serverSocket.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
