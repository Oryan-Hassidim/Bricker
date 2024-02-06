package bricker.utils;

/**
 * A basic logger that logs to the console
 * @see Logger
 * @see LoggerBase
 * @author Oryan Hassidim
 */
public class BasicLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
