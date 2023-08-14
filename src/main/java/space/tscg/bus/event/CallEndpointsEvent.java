package space.tscg.bus.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import space.tscg.database.Member;

@AllArgsConstructor
@Getter
public class CallEndpointsEvent
{
    private Member member;
}
