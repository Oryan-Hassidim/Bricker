package bricker.utils;

import java.util.Dictionary;
import java.util.Hashtable;

public class Services {
    private static Dictionary<Class<?>, Object> services = new Hashtable<Class<?>, Object>();

    public static <T> void registerService(Class<T> serviceType, T service) {
        services.put(serviceType, service);
    }

    public static <T> T getService(Class<T> serviceType) throws NullPointerException {
        return serviceType.cast(services.get(serviceType));
    }
}
