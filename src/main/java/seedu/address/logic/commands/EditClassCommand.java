package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLASSES;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.Memo;
import seedu.address.model.module.Class;
import seedu.address.model.module.ClassName;
import seedu.address.model.module.Schedule;
import seedu.address.model.student.UniqueStudentList;

/**
 * Edits the details of an existing class in EduTrack.
 */
public class EditClassCommand extends Command {
    public static final String COMMAND_WORD = "edit /c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the class identified "
            + "by the index number used in the displayed class list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_MEMO + "NOTE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "cs2100 "
            + PREFIX_MEMO + "prepare material";

    public static final String MESSAGE_EDIT_CLASS_SUCCESS = "Edited Class: %1$s\n"
            + "Schedule: %2$s\n"
            + "Memo: %3$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CLASS = "Class name already exists";

    private final Index index;
    private final EditClassDescriptor editClassDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editClassDescriptor details to edit the person with
     */
    public EditClassCommand(Index index, EditClassDescriptor editClassDescriptor) {
        requireNonNull(index);
        requireNonNull(editClassDescriptor);

        this.index = index;
        this.editClassDescriptor = new EditClassDescriptor(editClassDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Class> lastShownList = model.getFilteredClassList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLASS_DISPLAYED_INDEX);
        }

        Class classToEdit = lastShownList.get(index.getZeroBased());
        UniqueStudentList currentStudentList = classToEdit.getUniqueStudentList();

        Class editedClass = createEditedClass(classToEdit, editClassDescriptor, currentStudentList);

        if (!classToEdit.isSameClass(editedClass) && model.hasClass(editedClass)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLASS);
        }

        model.setClass(index, editedClass);
        model.updateFilteredClassList(PREDICATE_SHOW_ALL_CLASSES);
        return new CommandResult(String.format(MESSAGE_EDIT_CLASS_SUCCESS, Messages.formatClass(editedClass),
                editedClass.getClassSchedule(), editedClass.getClassMemo()));
    }

    /**
     * Creates and returns a {@code Class} with the details of {@code classToEdit}
     * edited with {@code editClassDescriptor}.
     */
    private static Class createEditedClass(Class classToEdit, EditClassDescriptor editClassDescriptor,
                                           UniqueStudentList studentList) {
        assert classToEdit != null;

        ClassName updatedClassName = editClassDescriptor.getClassName().orElse(classToEdit.getClassName());
        Memo memo = editClassDescriptor.getClassNote().orElse(classToEdit.getClassMemo());
        Schedule schedule = editClassDescriptor.getClassSchedule().orElse(classToEdit.getClassSchedule());

        return new Class(updatedClassName, studentList, memo, schedule);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditClassCommand)) {
            return false;
        }

        EditClassCommand otherEditCommand = (EditClassCommand) other;
        return index.equals(otherEditCommand.index)
                && editClassDescriptor.equals(otherEditCommand.editClassDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editClassDescriptor", editClassDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the class with. Each non-empty field value will
     * replace the corresponding field value of the class.
     */
    public static class EditClassDescriptor {
        private ClassName className;
        private Memo classMemo;
        private Schedule classSchedule;

        public EditClassDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditClassDescriptor(EditClassDescriptor toCopy) {
            setClassName(toCopy.className);
            setClassNote(toCopy.classMemo);
            setClassSchedule(toCopy.classSchedule);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(className, classMemo, classSchedule);
        }

        public void setClassName(ClassName className) {
            this.className = className;
        }

        public Optional<ClassName> getClassName() {
            return Optional.ofNullable(className);
        }

        public void setClassNote(Memo classMemo) {
            this.classMemo = classMemo;
        }

        public Optional<Memo> getClassNote() {
            return Optional.ofNullable(classMemo);
        }

        public void setClassSchedule(Schedule classSchedule) {
            this.classSchedule = classSchedule;
        }

        public Optional<Schedule> getClassSchedule() {
            return Optional.ofNullable(classSchedule);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditClassDescriptor)) {
                return false;
            }

            EditClassDescriptor otherEditClassDescriptor = (EditClassDescriptor) other;
            return Objects.equals(className, otherEditClassDescriptor.className)
                    && Objects.equals(classMemo, otherEditClassDescriptor.classMemo)
                    && Objects.equals(classSchedule, otherEditClassDescriptor.classSchedule);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("className", className)
                    .add("classSchedule", classSchedule)
                    .add("classMemo", classMemo)
                    .toString();
        }
    }
}