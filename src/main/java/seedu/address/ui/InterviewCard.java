package seedu.address.ui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.interview.Interview;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Interview}.
 */
public class InterviewCard extends UiPart<Region> {

    private static final String FXML = "InterviewListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Interview interview;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FlowPane applications;

    /**
     * Creates a {@code InterviewCode} with the given {@code Interview} and index to display.
     */
    public InterviewCard(Interview interview, int displayedIndex) {
        super(FXML);
        this.interview = interview;
        id.setText(displayedIndex + ". ");

        String[] dateSplit = interview.getDate().value.split("-");
        String[] timeSplit = interview.getTime().value.split(":");
        LocalTime parsedTime = LocalTime.parse(timeSplit[0] + timeSplit[1], DateTimeFormatter.ofPattern("HHmm"));
        String formattedTime = parsedTime.format(DateTimeFormatter.ofPattern("hh:mma"));

        Person person = interview.getPerson();

        name.setText(person.getName().fullName);
        date.setText(dateSplit[0] + " " + dateSplit[1].substring(0, 1).toUpperCase()
                + dateSplit[1].substring(1) + " " + dateSplit[2]);
        time.setText("@ " + formattedTime);
        person.getApplications().stream()
                .sorted(Comparator.comparing(application -> application.getJob().jobId))
                .forEach(application -> applications.getChildren().add(new Label("Job " + application.getJob().jobId)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof InterviewCard)) {
            return false;
        }

        // state check
        InterviewCard card = (InterviewCard) other;
        return id.getText().equals(card.id.getText())
                && interview.equals(card.interview);
    }
}
