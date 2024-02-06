package bricker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * an interface for objects that can log messages
 * 
 * @author Orayn Hassidim
 */
public interface Logger {

    /** the format of the time in the log. */
    public static final String TIME_FORMAT = "HH:mm:ss";
    /** the formatter of the time in the log. */
    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat(TIME_FORMAT);

    /**
     * logs a message
     * 
     * @param message the message to log
     */
    void log(String message);

    /**
     * logs a formatted message
     * 
     * @param message the message format to log
     * @param args    the arguments to format the message with
     */
    public default void logFormatted(String format, Object... args) {
        this.log(String.format(format, args));
    }

    /**
     * logs a formatted message with the "ERROR" prefix and the current time
     * 
     * @param message the message format to log
     * @param args    the arguments to format the message with
     */
    public default void logError(String message, Object... args) {
        this.logFormatted("[ERROR %s] %s", TIME_FORMATTER.format(new Date()), String.format(message, args));
    }

    /**
     * logs a formatted message with the "INFO" prefix and the current time
     * 
     * @param message the message format to log
     * @param args    the arguments to format the message with
     */
    public default void logInformation(String message, Object... args) {
        this.logFormatted("[INFO %s] %s", TIME_FORMATTER.format(new Date()), String.format(message, args));
    }

    /**
     * logs a formatted message with the "WARNING" prefix and the current time
     * 
     * @param message the message format to log
     * @param args    the arguments to format the message with
     */
    public default void logWarning(String message, Object... args) {
        this.logFormatted("[WARNING %s] %s", TIME_FORMATTER.format(new Date()), String.format(message, args));
    }
}
