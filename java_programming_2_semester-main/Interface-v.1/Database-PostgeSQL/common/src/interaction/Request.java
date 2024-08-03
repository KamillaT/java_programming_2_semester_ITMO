package interaction;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String command;
    private final Serializable object;
    private final String argument;

    private final String login;
    private final String password;

    private final Locale locale;

    public Request(String command, Serializable object, String argument, String login, String password, Locale locale) {
        this.command = command;
        this.object = object;
        this.argument = argument;
        this.login = login;
        this.password = password;
        this.locale = locale;
    }

    public Request(String command, String argument, String login, String password, Locale locale) {
        this(command, null, argument, login, password, locale);
    }

    public Request(String command, String login, String password, Locale locale) {
        this(command, null, "", login, password, locale);
    }

    public Request(String command, Serializable object, String login, String password, Locale locale) {
        this(command, object, "", login, password, locale);
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

    public Locale getLocale() {return locale;}

    public boolean isEmpty() {
        return command.isEmpty() && argument.isEmpty() && object == null;
    }
}
