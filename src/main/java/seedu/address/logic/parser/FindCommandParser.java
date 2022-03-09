package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.lang.reflect.Array;
import java.util.*;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        HashMap<Prefix, String> searchStrings = new HashMap<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).toString();
            searchStrings.put(PREFIX_NAME, name);
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()).toString();
            searchStrings.put(PREFIX_PHONE, phone);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()).toString();
            searchStrings.put(PREFIX_EMAIL, email);
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()).toString();
            searchStrings.put(PREFIX_ADDRESS, address);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            StringBuilder tags = new StringBuilder();

            for (Tag t : tagSet) {
                tags.append(t.toString()).append(" ");
            }
            searchStrings.put(PREFIX_TAG, tags.toString().trim());
        }


        String[] nameKeywords = trimmedArgs.split("\\s+");

        // return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        return new FindCommand(new NameContainsKeywordsPredicate(searchStrings));
    }

}
