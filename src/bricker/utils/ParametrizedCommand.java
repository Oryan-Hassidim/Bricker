package bricker.utils;

/**
 * an interface for a command that can be invoked with a parameter
 * 
 * @param <T> the type of the parameter
 */
public interface ParametrizedCommand<T> {
    /**
     * invokes the command with a parameter
     * 
     * @param param the parameter
     */
    void invoke(T param);
}