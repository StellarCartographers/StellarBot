package space.tscg.util.dotenv;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Secret
{
    private String source;
    private String value;
    
    String source()
    {
        return source;
    }
    
    String get()
    {
        return value;
    }
    
    public static int getAsInt(String key)
    {
        return Env.MAP.containsKey(key) ? safeGetInt(Env.MAP.get(key).get()) : -1;
    }
    
    public static String get(String key)
    {
        return Env.MAP.containsKey(key) ? Env.MAP.get(key).get() : "";
    }
    
    private static int safeGetInt(String value)
    {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
