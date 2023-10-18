package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Class;
import seedu.address.model.module.ClassName;
import seedu.address.model.student.UniqueStudentList;

/**
 * Parses input arguments and creates a new AddClassCommand object
 */
public class AddClassCommandParser implements Parser<AddClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClassCommand
     * and returns an AddClassCommand object for execution.
     * @throws ParseException if the user input does not follow the expected format
     */
    public AddClassCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLASS);
        ClassName className = ParserUtil.parseClassName(argMultimap.getValue(PREFIX_CLASS).get());
        UniqueStudentList emptyStudentList = new UniqueStudentList();
        Class c = new Class(className, emptyStudentList);

        return new AddClassCommand(c);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
