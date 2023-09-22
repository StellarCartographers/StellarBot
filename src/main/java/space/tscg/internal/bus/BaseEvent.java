package space.tscg.internal.bus;

import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public interface BaseEvent
{
    default TaggedLogger logger()
    {
        return Logger.tag(this.getClass().getSimpleName());
    }
    
    default void post()
    {
        ServerEventManager.getInstance().postEvent(this);
    }
}
