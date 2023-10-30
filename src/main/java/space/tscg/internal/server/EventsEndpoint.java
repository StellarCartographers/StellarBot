package space.tscg.internal.server;

import java.util.TreeMap;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import space.tscg.internal.server.bus.ServerEventManager;
import space.tscg.internal.server.bus.ServerEventsProxy;

public class EventsEndpoint
{
    private static ServerEventsProxy eventProxy = ServerEventManager.getEventsProxy();
    
    public static void handle(Context ctx)
    {
        TreeMap<String, String> tree = new TreeMap<>(ctx.pathParamMap());
        var passedEvent = ctx.pathParamMap().get(tree.lastKey());
        
        if(eventProxy.isValidEvent(passedEvent))
        {
            var event = eventProxy.buildEventClass(ctx, passedEvent);
            if(event.isErr())
                ctx.status(404);
            
            event.get().post();
            ctx.status(HttpStatus.ACCEPTED);
        }
    }
}
