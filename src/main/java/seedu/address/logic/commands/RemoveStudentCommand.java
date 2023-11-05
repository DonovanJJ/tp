package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Class;
import seedu.address.model.module.ClassName;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Removes a Student identified by Name from a Class.
 */
public class RemoveStudentCommand extends Command {

    public static final String COMMAND_WORD = "remove /s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a student from a class.\n"
            + "Parameters: "
            + PREFIX_STUDENT + "STUDENT_INDEX"
            + PREFIX_CLASS + "CLASS_NAME"
            + "\n"
            + "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_CLASS + " cs2103t";

    public static final String MESSAGE_REMOVE_STUDENT_SUCCESS = "%s has been removed from %s ";

    private final Index studentIndex;
    private final ClassName studentClassName;

    /**
     * Command to remove Student based on its index in the UniqueStudentList in Class with ClassName
     * @param index Index of student in List
     * @param studentClassName ClassName of the Student's Class
     */
    public RemoveStudentCommand(Index index, ClassName studentClassName) {
        requireNonNull(studentClassName);
        requireNonNull(index);
        this.studentIndex = index;
        this.studentClassName = studentClassName;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Class studentClass = model.getClass(studentClassName);
        ObservableList<Student> studentList = model.getStudentListFromClass(studentClass);
        if (studentIndex.getZeroBased() >= studentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Student studentToDelete = model.getStudentFromStudentList(studentList, studentIndex);
        Name studentName = model.getStudentName(studentToDelete);
        model.deleteStudent(studentToDelete);
        model.deleteStudentFromClass(studentToDelete, studentClass);
        return new CommandResult(String.format(MESSAGE_REMOVE_STUDENT_SUCCESS, studentName,
                Messages.formatClass(studentClass)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveStudentCommand)) {
            return false;
        }

        RemoveStudentCommand otherRemoveStudentCommand = (RemoveStudentCommand) other;
        return studentClassName.equals(otherRemoveStudentCommand.studentClassName)
                && studentIndex.equals(otherRemoveStudentCommand.studentIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentClassName", studentClassName)
                .add("studentIndex", studentIndex)
                .toString();
    }

}
