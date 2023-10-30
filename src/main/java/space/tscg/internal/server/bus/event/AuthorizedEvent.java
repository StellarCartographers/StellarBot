package space.tscg.internal.server.bus.event;

import lombok.Getter;
import space.tscg.internal.server.bus.BaseEvent;
import space.tscg.internal.server.bus.index.ServerEvent;

@Getter
@ServerEvent
public class AuthorizedEvent implements BaseEvent
{
    private String discordId;
}
