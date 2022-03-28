package utils;

public class ArgumentsParser {

    private static final int AVAILABLE_ARGUMENTS_NUMBER = 1;
    private static final int FILE_SEPARATE_ELEMENTS_NUMBER = 2;
    private static final String SEPARATOR = "\\.";
    private static final String RIGHT_FILE_TYPE = "txt";

    public static boolean isArgumentsOk(String[] args) {
        if (AVAILABLE_ARGUMENTS_NUMBER != args.length) {
            return false;
        }
        if (FILE_SEPARATE_ELEMENTS_NUMBER != args[0].split(SEPARATOR).length) {
            return false;
        }
        return args[0].split(SEPARATOR)[1].equals(RIGHT_FILE_TYPE);
    }
}
