package space.tscg.internal.server.bus;

@FunctionalInterface
public interface ServerEventListener
{
    void onEvent(BaseEvent event);
}
