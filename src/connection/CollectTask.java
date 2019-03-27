package connection;

import message.DataMessage;
import message.EndOfStreamMessage;
import message.Message;

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
        Message inputMessage = (Message) inputStream.readObject();
        if (inputMessage instanceof EndOfStreamMessage) streamThread.closeStream();
        else streamThread.addToOutPutTarget(((DataMessage)inputMessage).data());
    }
}
