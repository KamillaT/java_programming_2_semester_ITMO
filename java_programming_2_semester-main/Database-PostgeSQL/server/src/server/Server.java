package server;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import database.AuthorizationManager;
import exceptions.OpeningServerSocketException;
import processing.CommandManager;
import processing.ServerCommandProcessor;
import processing.ServerProcessor;
import utility.ConsolePrinter;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    final int port;
    private final AuthorizationManager authorizationManager;
    private AtomicBoolean runFlag = new AtomicBoolean(false);
    ExecutorService acceptingExecutor = Executors.newFixedThreadPool(4);
    ExecutorService responseCreationExecutor = Executors.newFixedThreadPool(4);

    public Server(String port) {
        this.port = Integer.parseInt(port);
        this.authorizationManager = new AuthorizationManager(ServerCommandProcessor.getDatabaseManager());
    }

    private static Scanner scanner = new Scanner(System.in);

    private void prepareResponse(Communicator communicator) {
        responseCreationExecutor.execute(() -> {
            try {
                Receiver receiver = new Receiver(communicator.getClientSocket());
                Sender sender = new Sender(communicator.getClientSocket());
                ServerProcessor serverProcessor = new ServerProcessor(receiver, sender, authorizationManager);
                CommandManager.invokeCommand();
                serverProcessor.decodeAndProcessCommand();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleClient(Communicator communicator) {
        try {
            Thread.sleep(1000);
            communicator.handleClientConnection();
            prepareResponse(communicator);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void handleClientLoop(Communicator communicator) {
        while (runFlag.get()) {
            handleClient(communicator);
        }
    }

    private Communicator openSocket() throws OpeningServerSocketException {
        Communicator communicator = new Communicator(port);
        communicator.openServerSocket();

        return communicator;
    }

    private void startHandling() {
        try {
            Communicator communicator = openSocket();

            acceptingExecutor.execute(() -> handleClientLoop(communicator));
            acceptingExecutor.execute(() -> handleClientLoop(communicator));
            acceptingExecutor.execute(() -> handleClientLoop(communicator));
            acceptingExecutor.execute(() -> handleClientLoop(communicator));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void run() {
        runFlag.set(true);

        acceptingExecutor.execute(this::startHandling);

        while (runFlag.get()) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.equals("save")) {
                    ConsolePrinter.printResult("Collection was successfully saved!");
                }
            }
        }
    }
}
