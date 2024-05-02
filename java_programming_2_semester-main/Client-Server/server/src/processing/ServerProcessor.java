package processing;

import interaction.Request;
import interaction.Response;
import connector.Sender;
import connector.Receiver;
import utilities.Invoker;
import utility.ConsolePrinter;

import java.io.IOException;

public class ServerProcessor {
    private final Receiver receiver;
    private final Sender sender;

    public ServerProcessor(Receiver receiver, Sender sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public void decodeAndProcessCommand() throws IOException, ClassNotFoundException {
        Request request = receiver.receive();
        ConsolePrinter.printResult(request);
        Response response = new Response(Invoker.executeCommand(request));
        sender.send(response);
    }
}
