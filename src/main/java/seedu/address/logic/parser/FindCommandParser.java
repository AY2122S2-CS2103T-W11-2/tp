package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.HashMap;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;

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
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_APPLICATION);

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

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String searchAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()).toString();
            searchTerms.put(PREFIX_ADDRESS, searchAddress);
        }


        String[] nameKeywords = new String[5];
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
