package connection;

import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CollectTask implements Runnable {

    private final Socket clientSocket;

    public CollectTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            readInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readInput() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
        Message inputLine;
        while ((inputLine = (Message) inputStream.readObject()) != null) System.out.println(inputLine);
    }
}
