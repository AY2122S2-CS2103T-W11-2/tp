package seedu.address.model.person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final HashMap<Prefix,String> keywords;

    public NameContainsKeywordsPredicate(HashMap<Prefix,String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {

        if (keywords.containsKey(PREFIX_NAME)) {
            String[] nameFragments = keywords.get(PREFIX_NAME).split(" ");

            boolean isContained = Stream.of(nameFragments)
                    .anyMatch(name -> StringUtil.containsWordIgnoreCase(person.getName().fullName, name));

            if (!isContained) {
                return false;
            }
        }

        if (keywords.containsKey(PREFIX_PHONE)) {
            Phone searchNumber = new Phone(keywords.get(PREFIX_PHONE));

            if (!person.getPhone().equals(searchNumber)) {
                return false;
            }
        }

        if (keywords.containsKey(PREFIX_EMAIL)) {
            Email searchEmail = new Email(keywords.get(PREFIX_EMAIL));

            if (!person.getEmail().equals(searchEmail)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
