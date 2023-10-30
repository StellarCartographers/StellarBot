package space.tscg.internal.server.bus.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import space.tscg.internal.server.bus.BaseEvent;

@AllArgsConstructor
@Getter
public class CreatedCarrierEvent implements BaseEvent
{
    private String carrierId;
    private String memberId;
    private String guildId;
    private String ownerChannelId;
    private String publicChannelId;
}
