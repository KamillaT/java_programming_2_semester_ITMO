package processing;

import connector.Receiver;
import connector.Sender;
import data.UserInfo;
import database.AuthorizationManager;
import interaction.Request;
import interaction.Response;
import utilities.Invoker;
import utility.ConsolePrinter;

import java.io.IOException;

public class ServerProcessor {
    private final Receiver receiver;
    private final Sender sender;
    private final AuthorizationManager authorizationManager;

    public ServerProcessor(Receiver receiver, Sender sender, AuthorizationManager authorizationManager) {
        this.receiver = receiver;
        this.sender = sender;
        this.authorizationManager = authorizationManager;
    }

    public void decodeAndProcessCommand() throws IOException, ClassNotFoundException {
        Response response;
        Request request = receiver.receive();
        UserInfo userInfo = new UserInfo(request.getLogin(), request.getPassword());
        Integer userId = authorizationManager.tryLogin(userInfo);

        ConsolePrinter.printResult(request);

        if (userId < 0) {
            response = new Response("Unable to login to database!");
        } else {
            response = new Response(Invoker.executeCommand(request, userId));
        }

        new Thread(() ->
        {
            try {
                sender.send(response);
            } catch (IOException e) {
                ConsolePrinter.printError("Unable to send result with reason: " + e.getMessage());
            }
        }
        ).start();

    }
}
