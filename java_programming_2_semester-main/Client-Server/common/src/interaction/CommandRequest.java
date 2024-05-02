package interaction;

import java.io.Serial;
import java.io.Serializable;

public class CommandRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String command;
    private final Serializable object;
    private final String argument;

    public CommandRequest(String command, Serializable object, String argument) {
        this.command = command;
        this.object = object;
        this.argument = argument;
    }

    public CommandRequest(String command, String argument) {
        this(command, null, argument);
    }

    public CommandRequest(String command) {
        this(command, null, "");
    }

    public CommandRequest(String command, Serializable object) {
        this(command, object, "");
    }

    public String getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public String getArgument() {
        return argument;
    }

    public boolean isEmpty() {
        return command.isEmpty() && argument.isEmpty() && object == null;
    }
}
