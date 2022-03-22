package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.Type.PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Type;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class DeletePersonCommand extends DeleteCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " [p] : Deletes the person identified by the index number used in the displayed person list,\n"
            + "Or deletes by identifying the user with their name\n"
            + "Parameters: INDEX (must be a positive integer),\n"
            + "Example: " + COMMAND_WORD + " [p] 1\n"
            + "Or, Parameters: NAME (must be a string), \n"
            + "Example: " + COMMAND_WORD + " [p] Jeremy\n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;
    private final String targetApplicant;

    /**
     * Constructor for DeletePersonCommand.
     * @param targetIndex
     */
    public DeletePersonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetApplicant = "";
    }

    /**
     * Constructor to faciliate deletion of Job Applicant via name.
     * @param targetApplicant the name of the user
     */
    public DeletePersonCommand(String targetApplicant, Index targetIndex) {
        this.targetApplicant = targetApplicant;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // send to method that deletes applicant via name
        if (!targetApplicant.isEmpty()) {
            return deleteByName(model, lastShownList, targetApplicant);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete), PERSON);
    }

    /**
     * Method that handles deletion of a person from the list via name.
     * Need to find the position of the person in the list who matches the
     * targetName
     * @param model
     * @param lastShownList A List of type Person
     * @param targetApplicant
     * @return CommandResult
     */
    private CommandResult deleteByName(Model model, List<Person> lastShownList, String targetApplicant)
            throws CommandException {

        int pos = 0;
        int len = lastShownList.size();

        for (Person person : lastShownList) {
            String name = person.getName().getFullName().toLowerCase();

            if (name.equals(targetApplicant.toLowerCase().trim())) {
                break;
            } else {
                ++pos;
                continue;
            }
        }

        // have gone past the limits of given list
        if (pos >= len) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME_PROVIDED);
        }

        // person to be deleted
        Person personToDelete = lastShownList.get(pos);
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete), getType());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePersonCommand) other).targetIndex)); // state check
    }

    @Override
    public Type getType() {
        return PERSON;
    }
}


