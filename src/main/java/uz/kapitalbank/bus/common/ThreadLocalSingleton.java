package uz.kapitalbank.bus.common;

import uz.kapitalbank.bus.common.models.Lang;
import uz.kapitalbank.bus.user.User;

/**
 * @author Rustam Khalmatov
 */

public class ThreadLocalSingleton {
    private final static ThreadLocalSingleton INSTANCE = new ThreadLocalSingleton();
    private final static ThreadLocal<User> USER = ThreadLocal.withInitial(User::new);
    private final static ThreadLocal<Lang> LANG_LOCAL = ThreadLocal.withInitial(() -> Lang.RU);
    private final static ThreadLocal<String> LOG_INTERNAL_ID = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> IP_ADDRESS = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<Boolean> CACHEABLE = ThreadLocal.withInitial(() -> Boolean.FALSE);
    private final static ThreadLocal<String> EXTERNAL_ID = ThreadLocal.withInitial(String::new);

    private ThreadLocalSingleton() {
    }

    public static ThreadLocalSingleton getInstance() {
        return INSTANCE;
    }

    public static void remove() {
        ThreadLocalSingleton.USER.remove();
        ThreadLocalSingleton.LANG_LOCAL.remove();
    }

    public static User getUser() {
        return USER.get();
    }

    public static void setUser(User user) {
        ThreadLocalSingleton.USER.set(user);
    }

    public static Lang getLang() {
        return ThreadLocalSingleton.LANG_LOCAL.get();
    }

    public static void setLang(Lang lang) {
        ThreadLocalSingleton.LANG_LOCAL.set(lang);
    }

    public static String getLogInternalId() {
        return ThreadLocalSingleton.LOG_INTERNAL_ID.get();
    }

    public static void setLogInternalId(String internalId) {
        ThreadLocalSingleton.LOG_INTERNAL_ID.set(internalId);
    }

    public static String getIpAddress() {
        return ThreadLocalSingleton.IP_ADDRESS.get();
    }

    public static void setIpAddress(String ipAddress) {
        ThreadLocalSingleton.IP_ADDRESS.set(ipAddress);
    }

    public static void setCacheable(boolean cacheable) {
        CACHEABLE.set(cacheable);
    }

    public static boolean getCacheable() {
        return CACHEABLE.get();
    }


    public static void setExternalId(String externalId) {
        ThreadLocalSingleton.EXTERNAL_ID.set(externalId);
    }

    public static String getExternalId(){
        return ThreadLocalSingleton.EXTERNAL_ID.get();
    }

}
