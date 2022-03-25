package seedu.address.logic.commands;

import seedu.address.model.Model;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from import");
    }
}
