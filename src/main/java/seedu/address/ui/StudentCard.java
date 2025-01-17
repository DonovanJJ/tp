package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Memo: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Student person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label attendance;
    @FXML
    private Label totalAttendance;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to
     * display.
     */
    public StudentCard(Student person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        attendance.setText(person.getAttendanceStringRep());
        totalAttendance.setText(person.getTotalAttendanceStringRep());
    }
}
