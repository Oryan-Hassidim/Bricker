package bricker.utils;

/**
 * A getter of a value.
 * 
 * @param <T> the type of the value
 * @author Orayn Hassidim
 */
public interface Getter<T> {
    /**
     * Gets the value.
     * 
     * @return the value
     */
    T get();
}
