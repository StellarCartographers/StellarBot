package space.tscg.internal.server.bus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.atteo.classindex.ClassIndex;
import org.tinylog.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.javalin.http.Context;
import panda.std.Blank;
import panda.std.Result;
import space.tscg.internal.server.bus.index.ServerEvent;

public class ServerEventsProxy
{
    private final ObjectMapper                            MAPPER      = new ObjectMapper()
                    .setSerializationInclusion(Include.NON_NULL)
                    .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .disable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final Map<String, Class<? extends BaseEvent>> eventsIndex = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public ServerEventsProxy()
    {
        ClassIndex.getAnnotated(ServerEvent.class).forEach(cls -> {
            var eventclassName = cls.getSimpleName();
            eventclassName = eventclassName.replace("Event", "").toLowerCase();
            if (BaseEvent.class.isAssignableFrom(cls))
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
        var event = map(ctx.body(), this.eventsIndex.get(passedEvent));
        return Result.when(event.isPresent(), event.get(), Blank.BLANK);
    }
    
    private <T> Optional<T> map(String jsonString, Class<T> clzz)
    {
        try
        {
            return Optional.of(MAPPER.readValue(jsonString, clzz));
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
