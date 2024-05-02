package processing;

import processing.specificcommands.*;
import utilities.Invoker;

public class CommandManager {
    public static void invokeCommand() {
        Invoker.register("help", new HelpCommand());
        Invoker.register("info", new InfoCommand());
        Invoker.register("show", new ShowCommand());
        Invoker.register("add", new AddCommand());
        Invoker.register("update", new UpdateCommand());
        Invoker.register("remove_by_id", new RemoveByIdCommand());
        Invoker.register("clear", new ClearCommand());
        Invoker.register("execute_script", new ExecuteScriptCommand());
        Invoker.register("exit", new ExitCommand());
        Invoker.register("remove_head", new RemoveHeadCommand());
        Invoker.register("add_if_min", new AddIfMinCommand());
        Invoker.register("history", new HistoryCommand());
        Invoker.register("remove_any_by_price", new RemoveAnyByPriceCommand());
        Invoker.register("print_descending", new PrintDescendingCommand());
        Invoker.register("print_field_descending_price", new PrintFieldDescendingPriceCommand());
    }
}

