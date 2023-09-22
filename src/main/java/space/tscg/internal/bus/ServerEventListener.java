package space.tscg.internal.bus;

@FunctionalInterface
public interface ServerEventListener
{
    void onEvent(BaseEvent event);
}
