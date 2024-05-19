package server;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import exceptions.ConnectionErrorException;
import exceptions.OpeningServerSocketException;
import file.CSVProcess;
import processing.CommandManager;
import processing.ServerProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final String sPort;
    private final int CONNECTION_TIMEOUT = 50 * 1000;
    private static Scanner scanner = new Scanner(System.in);

    public Server(String port) {
        this.sPort = port;
    }

    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                int port = Integer.parseInt(sPort);
                Communicator communicator = new Communicator(port, CONNECTION_TIMEOUT);
                communicator.openServerSocket();
                while (true) {
                    Thread.sleep(1000);
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        while (true) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.equals("save")) {
                    CSVProcess.writeCollection();
                    ConsolePrinter.printResult("Collection was successfully saved!");}
            }
        }
    }
}