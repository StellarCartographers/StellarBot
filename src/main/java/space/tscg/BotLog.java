package space.tscg;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BotLog
{
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger("TSCG");

    public static void info(String format, Object... args)
    {
        LOGGER.info(format, args);
    }
    
    public static void info(String message)
    {
        LOGGER.info(message);
    }
    
    public static void error(Throwable throwable, String format, Object... args)
    {
        LOGGER.error(format, args);
    }
    
    public static void error(String message)
    {
        LOGGER.error(message);
    }
    
    public static void warning(String format, Object... args)
    {
        LOGGER.warn(format, args);
    }
    
    public static void warning(String message)
    {
        LOGGER.warn(message);
    }
}
