package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

/**
 * Represents the total number of lessons a Student has attended.
 */
public class LessonsAttended {
    public static final String MESSAGE_CONSTRAINTS = "LessonsAttended should be an int";
    private Integer totalLessons;

    /**
     * Constructs a {@code LessonsAttended}.
     *
     * @param totalLessons Number of lessons the student attended
     */
    public LessonsAttended(Integer totalLessons) {
        requireNonNull(totalLessons);
        this.totalLessons = totalLessons;
    }

    /**
     * Constructs a {@code Lessons Attended}.
     * Starts with initial count of 0
     */
    public LessonsAttended() {
        this.totalLessons = 0;
    }

    public Integer getTotalLessons() {
        return this.totalLessons;
    }

    /**
     * Increases the number of lessons the student attended by 1.
     */
    public void incrementLessons() {
        this.totalLessons ++;
    }

    /**
     * Reduces the number of lessons the student attended by 1.
     */
    public void decrementLessons() {
        this.totalLessons --;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonsAttended)) {
            return false;
        }

        LessonsAttended otherAttendance = (LessonsAttended) other;
        return this.totalLessons.equals(otherAttendance.totalLessons);
    }
    @Override
    public String toString() {
        return this.totalLessons.toString();
    }
}
