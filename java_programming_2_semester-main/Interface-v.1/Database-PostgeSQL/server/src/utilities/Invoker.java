package utilities;

import interaction.Request;
import processing.specificcommands.AbstractCommand;

import java.util.HashMap;

public class Invoker {
    private final static HashMap<String, AbstractCommand> commands = new HashMap<>();

    public static void register(String name, AbstractCommand command) {
        commands.put(name, command);
    }

    public static HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }

    public static String executeCommand(Request request, Integer userId) {
        String name = request.getCommand();
        AbstractCommand command = commands.get(name);
        String argument = request.getArgument();
        Object object = request.getObject();
        return  command.execute(argument, object, userId);
    }
}
