package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.HashMap;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_APPLICATION);

        HashMap<Prefix, String> searchTerms = new HashMap<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String searchName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).toString();
            searchTerms.put(PREFIX_NAME, searchName);
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String searchPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()).toString();
            searchTerms.put(PREFIX_PHONE, searchPhone);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String searchEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()).toString();
            searchTerms.put(PREFIX_EMAIL, searchEmail);
        }

        // TODO find by application

        return new FindCommand(new NameContainsKeywordsPredicate(searchTerms));
    }

}
