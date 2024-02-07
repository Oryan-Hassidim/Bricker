package bricker.utils;

/**
 * A basic logger that logs to the console
 * @see Logger
 * @see LoggerBase
 * @author Oryan Hassidim
 */
public class BasicLogger implements Logger {

    /**
     * Constructs a new BasicLogger.
     */
    public BasicLogger() {
        super();
    }

    /**
     * Logs the given message to the console.
     * @param message the message to log
     */
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
