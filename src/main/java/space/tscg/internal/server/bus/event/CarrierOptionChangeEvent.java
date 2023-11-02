package space.tscg.internal.server.bus.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import space.tscg.internal.server.bus.BaseEvent;

@AllArgsConstructor
@Getter
public class CarrierOptionChangeEvent implements BaseEvent
{
    private String carrierId;
}
