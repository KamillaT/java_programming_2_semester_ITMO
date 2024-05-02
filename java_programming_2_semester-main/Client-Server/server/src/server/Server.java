package server;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import exceptions.ConnectionErrorException;
import exceptions.OpeningServerSocketException;
import processing.CommandManager;
import processing.ServerProcessor;

import java.io.IOException;

public class Server {
    private final String sPort;
    private final int CONNECTION_TIMEOUT = 50 * 1000;

    public Server(String port) {
        this.sPort = port;
    }

    public void run() {
        try {
            int port = Integer.parseInt(sPort);
            Communicator communicator = new Communicator(port, CONNECTION_TIMEOUT);
            communicator.openServerSocket();
            while (true) {
                communicator.handleClientConnection();
                Receiver receiver = new Receiver(communicator.getClientSocket());
                Sender sender = new Sender(communicator.getClientSocket());
                ServerProcessor serverProcessor = new ServerProcessor(receiver, sender);
                CommandManager.invokeCommand();
                serverProcessor.decodeAndProcessCommand();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException | OpeningServerSocketException | ConnectionErrorException e) {
            throw new RuntimeException(e);
        }
    }
}