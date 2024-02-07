package bricker.utils;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * <p>
 * This class is used to register and get services for dependency injection.
 * It is a simple implementation of the Service Locator pattern.
 * It is used to avoid the use of static methods and to make the code much more
 * testable.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * Services.registerService(MyService.class, new MyService());
 * MyService service = Services.getService(MyService.class);
 * </pre>
 * 
 * @author Oryan Hassidim
 */
public class Services {
    /** The services collection. */
    private static Dictionary<Class<?>, Object> services = new Hashtable<Class<?>, Object>();

    /**
     * Constructs a new Services.
     */
    public Services() {
        super();
    }

    /**
     * Registers a service.
     * 
     * @param <T>         the type of the service
     * @param serviceType the type of the service
     * @param service     the service
     * @throws NullPointerException if the service or the serviceType is null
     */
    public static <T> void registerService(Class<T> serviceType, T service) throws NullPointerException {
        if (service == null) {
            throw new NullPointerException("service");
        }
        if (serviceType == null) {
            throw new NullPointerException("serviceType");
        }
        services.put(serviceType, service);
    }

    /**
     * Gets a service.
     * 
     * @param <T>         the type of the service
     * @param serviceType the type of the service
     * @return the service
     * @throws NullPointerException if the service is not found
     */
    public static <T> T getService(Class<T> serviceType) throws NullPointerException {
        return serviceType.cast(services.get(serviceType));
    }
}
