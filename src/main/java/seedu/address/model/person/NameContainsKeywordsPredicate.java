package seedu.address.model.person;

import java.util.*;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.tag.Tag;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final HashMap<Prefix, String> keywords;

    public NameContainsKeywordsPredicate(HashMap<Prefix, String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {

        if (this.keywords.containsKey(PREFIX_NAME)) {
            String[] nameFragments = this.keywords.get(PREFIX_NAME).split(" ");

            for (String frag : nameFragments) {
                boolean lmao = StringUtil.containsWordIgnoreCase(person.getName().fullName, frag);

                if (!lmao) {
                    return false;
                }
            }
        }

        if (this.keywords.containsKey(PREFIX_PHONE)) {
            String phone = this.keywords.get(PREFIX_PHONE);

            if (!person.getPhone().toString().equals(phone)) {
                return false;
            }
        }

        if (this.keywords.containsKey(PREFIX_EMAIL)) {
            String email = this.keywords.get(PREFIX_EMAIL);

            if (!person.getEmail().toString().contains(email)) {
                return false;
            }
        }

        if (this.keywords.containsKey(PREFIX_ADDRESS)) {
            String[] addrFrags = this.keywords.get(PREFIX_ADDRESS).split(" ");

            for (String frag : addrFrags) {
                boolean lmao = StringUtil.containsWordIgnoreCase(person.getAddress().toString(), frag);

                if (!lmao) {
                    return false;
                }
            }
        }

        if (this.keywords.containsKey(PREFIX_TAG)) {

            String[] tagList = this.keywords.get(PREFIX_TAG).split(" ");
            Set<Tag> search = new HashSet<>();

            for (String frag : tagList) {
                String tt = frag.replaceAll("[^a-zA-Z0-9]", "");
                search.add(new Tag(tt));
            }

            Set<Tag> tagSet = person.getTags();

            if (!tagSet.containsAll(search)) {
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
