package space.tscg.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

@AllArgsConstructor
@Getter
public class MemberAgreeEvent
{
    private final ButtonInteractionEvent event;
}
