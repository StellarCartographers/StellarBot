package space.tscg.util.text;

import java.time.Instant;

public class TimeFormat
{
    public static String Default()
    {
        return new TimeFormat().getDefault();
    }

    public static String ShortTime()
    {
        return new TimeFormat().getShortTime();
    }

    public static String LongTime()
    {
        return new TimeFormat().getLongTime();
    }

    public static String ShortDate()
    {
        return new TimeFormat().getShortDate();
    }

    public static String LongDate()
    {
        return new TimeFormat().getLongDate();
    }

    public static String ShortDateTime()
    {
        return new TimeFormat().getShortDateTime();
    }

    public static String LongDateTime()
    {
        return new TimeFormat().getLongDateTime();
    }

    public static String RelativeTime()
    {
        return new TimeFormat().getRelativeTime();
    }
    
    private long         epoch;

    private final String template      = "<t:%d%s>";
    private final String shortTime     = ":t";
    private final String longTime      = ":T";
    private final String shortDate     = ":d";
    private final String longDate      = ":D";
    private final String shortDateTime = ":f";
    private final String longDateTime  = ":F";
    private final String relativeTime  = ":R";

    private TimeFormat()
    {
        this.epoch = Instant.now().getEpochSecond();
    }

    private String getDefault()
    {
        return template.formatted(this.epoch, "");
    }

    private String getShortTime()
    {
        return template.formatted(this.epoch, shortTime);
    }

    private String getLongTime()
    {
        return template.formatted(this.epoch, longTime);
    }

    private String getShortDate()
    {
        return template.formatted(this.epoch, shortDate);
    }

    private String getLongDate()
    {
        return template.formatted(this.epoch, longDate);
    }

    private String getShortDateTime()
    {
        return template.formatted(this.epoch, shortDateTime);
    }

    private String getLongDateTime()
    {
        return template.formatted(this.epoch, longDateTime);
    }

    private String getRelativeTime()
    {
        return template.formatted(this.epoch, relativeTime);
    }
}
