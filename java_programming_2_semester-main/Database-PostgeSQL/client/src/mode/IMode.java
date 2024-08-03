package mode;

import processing.CommandHandler;

public interface IMode {

    /**
     * Executes the mode-specific functionality.
     */
    void executeMode(CommandHandler commandHandler);

}
