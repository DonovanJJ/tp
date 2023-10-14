package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EduTrack;
import seedu.address.model.ReadOnlyEduTrack;
import seedu.address.model.module.Class;
import seedu.address.model.student.Student;

/**
 * An Immutable EduTrack that is serializable to JSON format.
 */
@JsonRootName(value = "edutrack")
class JsonSerializableEduTrack {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    public static final String MESSAGE_DUPLICATE_CLASS = "Class list contains duplicate classes.";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    private final List<JsonAdaptedClass> classes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEduTrack} with the given students.
     */
    @JsonCreator
    public JsonSerializableEduTrack(@JsonProperty("students") List<JsonAdaptedStudent> students,
                                    @JsonProperty("classes") List<JsonAdaptedClass> classes) {
        this.students.addAll(students);
        this.classes.addAll(classes);
    }

    /**
     * Converts a given {@code ReadOnlyEduTrack} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEduTrack}.
     */
    public JsonSerializableEduTrack(ReadOnlyEduTrack source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
        classes.addAll(source.getClassList().stream().map(JsonAdaptedClass::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code EduTrack} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EduTrack toModelType() throws IllegalValueException {
        EduTrack eduTrack = new EduTrack();
        for (JsonAdaptedClass jsonAdaptedClass : classes) {
            Class c = jsonAdaptedClass.toModelType();
            if (eduTrack.hasClass(c)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLASS);
            }
            eduTrack.addClass(c);
        }
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (eduTrack.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            eduTrack.addStudent(student);
        }
        return eduTrack;
    }

}