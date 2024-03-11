package commands;

public class CommandManager {
    private static Receiver receiver = new Receiver(new Invoker());

    /**
     * Starts the command execution process by registering commands with the invoker.
     */
    public static void commandStarter() {
        Invoker.register("help", new HelpCommand(receiver));
        Invoker.register("info", new InfoCommand(receiver));
        Invoker.register("show", new ShowCommand(receiver));
        Invoker.register("add", new AddCommand(receiver));
        Invoker.register("update", new UpdateCommand(receiver));
        Invoker.register("remove_by_id", new RemoveByIdCommand(receiver));
        Invoker.register("clear", new ClearCommand(receiver));
        Invoker.register("save", new SaveCommand(receiver));
        Invoker.register("execute_script", new ExecuteScriptCommand(receiver));
        Invoker.register("exit", new ExitCommand(receiver));
        Invoker.register("remove_head", new RemoveHeadCommand(receiver));
        Invoker.register("add_if_min", new AddIfMinCommand(receiver));
        Invoker.register("history", new HistoryCommand(receiver));
        Invoker.register("remove_any_by_price", new RemoveAnyByPriceCommand(receiver));
        Invoker.register("print_descending", new PrintDescendingCommand(receiver));
        Invoker.register("print_field_descending_price", new PrintFieldDescendingPriceCommand(receiver));
    }
}
