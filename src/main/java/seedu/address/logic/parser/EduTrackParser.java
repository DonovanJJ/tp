package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.commands.RemoveStudentCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class EduTrackParser {

    /**
     * Used for initial separation of command word and args.
     */

    private static final Pattern BASIC_COMMAND_FORMAT1 = Pattern
            .compile("(?<commandWord>\\S+(?:\\s/\\S+)?)(?:\\s(?<arguments>.*))?");

    private static final Pattern BASIC_COMMAND_FORMAT2 = Pattern
            .compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Logger logger = LogsCenter.getLogger(EduTrackParser.class);

    /**
     * Identifies the class of object (if any) when using commands that require
     * additional parameters, such as adding a student (/s) or a class (/c).
     *
     * @param input The input string containing the user input.
     * @return A string representing the first argument of the command, including leading and trailing whitespace.
     */
    public static String extractObjectClass(String input) {
        // Split the input string by whitespace
        String[] parts = input.split("\\s");

        // Check if there are at least two parts (words) separated by whitespace
        if (parts.length >= 2) {
            // Return the second part (index 1)
            return " " + parts[1] + " ";
        } else {
            // Handle the case where there are not enough words
            System.out.println("There is no additional parameter to indicate class of object after command word");
            return null; // or throw an exception, return a default value, etc.
        }
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        String objectClass = "";
        final Matcher matcher;
        Matcher tempMatcher = BASIC_COMMAND_FORMAT1.matcher(userInput.trim());
        if (tempMatcher.matches()) {
            objectClass = extractObjectClass(userInput);
            matcher = tempMatcher;
        } else {
            matcher = BASIC_COMMAND_FORMAT2.matcher(userInput.trim());
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments") == null ? objectClass
                : objectClass + matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level
        // (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);
        System.out.println("Command word: " + commandWord);
        System.out.println("Arguments: " + arguments);
        switch (commandWord) {
        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddClassCommand.COMMAND_WORD:
            return new AddClassCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(arguments);

        case RemoveStudentCommand.COMMAND_WORD:
            return new RemoveStudentCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
