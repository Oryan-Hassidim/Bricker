package bricker.utils;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * <p>This class is used to register and get services for dependency injection.
 * It is a simple implementation of the Service Locator pattern.
 * It is used to avoid the use of static methods and to make the code more
 * testable.</p>
 * <p>Example:</p>
 * <pre>
 * Services.registerService(MyService.class, new MyService());
 * MyService service = Services.getService(MyService.class);
 * </pre>
 * 
 * @author Oryan Hassidim
 */
public class Services {
    private static Dictionary<Class<?>, Object> services = new Hashtable<Class<?>, Object>();

    public static <T> void registerService(Class<T> serviceType, T service) {
        services.put(serviceType, service);
    }

    public static <T> T getService(Class<T> serviceType) throws NullPointerException {
        return serviceType.cast(services.get(serviceType));
    }
}
