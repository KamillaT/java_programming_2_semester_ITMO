package interaction;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String command;
    private final Serializable object;
    private final String argument;

    private final String login;
    private final String password;

    public Request(String command, Serializable object, String argument, String login, String password) {
        this.command = command;
        this.object = object;
        this.argument = argument;
        this.login = login;
        this.password = password;
    }

    public Request(String command, String argument, String login, String password) {
        this(command, null, argument, login, password);
    }

    public Request(String command, String login, String password) {
        this(command, null, "", login, password);
    }

    public Request(String command, Serializable object, String login, String password) {
        this(command, object, "", login, password);
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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEmpty() {
        return command.isEmpty() && argument.isEmpty() && object == null;
    }
}
