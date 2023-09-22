package space.tscg.internal.bus;

import java.util.LinkedHashMap;
import java.util.Map;

import org.atteo.classindex.ClassIndex;
import org.tinylog.Logger;

import io.javalin.http.Context;
import panda.std.Blank;
import panda.std.Result;
import space.tscg.common.json.Json;
import space.tscg.internal.bus.index.ServerEvent;

public class ServerEventsProxy
{
    private final Map<String, Class<? extends BaseEvent>> eventsIndex = new LinkedHashMap<>();
    
    @SuppressWarnings("unchecked")
    public ServerEventsProxy()
    {
        ClassIndex.getAnnotated(ServerEvent.class).forEach(cls -> {
            var eventclassName = cls.getSimpleName();
            eventclassName = eventclassName.replace("Event", "").toLowerCase();
            if(BaseEvent.class.isAssignableFrom(cls))
                this.eventsIndex.put(eventclassName, (Class<? extends BaseEvent>) cls);
        });
    }
    
    public void logIndexKeys()
    {
        Logger.info(String.join(", ", this.eventsIndex.keySet()));
    }
    
    public boolean isValidEvent(String eventKey)
    {
        return this.eventsIndex.containsKey(eventKey);
    }
    
    public Result<BaseEvent, Blank> buildEventClass(Context ctx, String passedEvent)
    {
        var event = Json.map(ctx.body(), this.eventsIndex.get(passedEvent));
        return Result.when(event.isPresent(), event.get(), Blank.BLANK);
    }
}