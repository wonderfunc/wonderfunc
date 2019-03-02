package connection;

import message.Message;
import message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CollectTask implements Runnable {

    private final Socket clientSocket;
    private final StreamThread streamThread;

    public CollectTask(Socket clientSocket, StreamThread streamThread) {
        this.clientSocket = clientSocket;
        this.streamThread = streamThread;
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
        Message inputMessage;
        while ((inputMessage = (Message) inputStream.readObject()) != null) {
            if (inputMessage.type() == MessageType.ENDOFSTREAM) streamThread.end();
            else System.out.println(inputMessage);
        }
    }
}
