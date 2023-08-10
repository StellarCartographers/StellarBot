package space.tscg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BotLog
{
    private static final Logger LOGGER = LogManager.getLogger("TSCG");

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
    
    public static void fatal(String format, Object... args)
    {
        LOGGER.fatal(format, args);
    }
    
    public static void fatal(String message)
    {
        LOGGER.fatal(message);
    }
}
