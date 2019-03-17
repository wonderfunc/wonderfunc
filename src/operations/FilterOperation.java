package operations;

import message.DataMessage;
import message.Message;
import operations.interfaces.Operation;
import operations.interfaces.Relay;
import operations.interfaces.Target;
import utils.JSONReader;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Predicate;

public class FilterOperation<T extends Serializable> implements Operation<T>, Relay<T>, Target<T> {

    private Predicate<T> predicate;
    private Target<T> next;

    public FilterOperation(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void next(Target<T> target) {
        this.next = target;
    }

    @Override
    public void put(Message<T> message) {
        if (message instanceof DataMessage) {
            DataMessage<T> dataMessage = (DataMessage<T>) message;
            if (predicate.test(dataMessage.data())){
                relay(message);
            }
        } else {
            relay(message);
        }
    }

    @Override
    public void relay(Message<T> message) {
        if (next != null) next.put(message);
        else sendToCollect(message);
    }

    private void sendToCollect(Message<T> message) {
        final String ip = JSONReader.get("collectorIP");
        final String port = JSONReader.get("collectorPort");
        try {
            Socket clientSocket = new Socket(ip, Integer.parseInt(port));
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 }
