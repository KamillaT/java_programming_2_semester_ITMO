package connector;

import interaction.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver {
    private final Socket socket;
    public Receiver(Socket socket) {
        this.socket = socket;
    }
    public Request receive() throws IOException, ClassNotFoundException {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object object = objectInputStream.readObject();
        return (Request) object;
    }
}
