package space.tscg.internal.bus.event;

import lombok.Getter;
import space.tscg.internal.bus.BaseEvent;
import space.tscg.internal.bus.index.ServerEvent;

@Getter
@ServerEvent
public class AuthorizedEvent implements BaseEvent
{
    private String discordId;
}
