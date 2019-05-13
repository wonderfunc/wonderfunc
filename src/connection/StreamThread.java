package connection;

import stream.OutputTarget;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.SocketException;

public class StreamThread<T extends Serializable> implements Runnable {

    private ServerSocket serverSocket;
    private OutputTarget<T> outputTarget;

    public StreamThread(OutputTarget<T> outputTarget) {
        this.outputTarget = outputTarget;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(8080);
            while (true)
                new Thread(new CollectTask(serverSocket.accept(), this))
                        .start();
        } catch (SocketException ignored){

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addToOutPutTarget(T data){
        outputTarget.add(data);
    }

    public synchronized void closeStream() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
