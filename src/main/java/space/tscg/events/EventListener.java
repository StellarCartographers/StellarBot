package space.tscg.events;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import space.tscg.capi.AuthorizationFlow;
import space.tscg.capi.CAPIButton;
import space.tscg.util.Embed;

public class EventListener extends ListenerAdapter
{
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event)
    {
        if (event.getComponentId().equals(CAPIButton.CONSENT.getId()))
        {
            MessageEmbed embed = Embed.newBuilder().description("Authorize through Frontier Development").toEmbed();

            event.editMessageEmbeds(embed).setComponents(
                ActionRow.of(
                    Button.link(AuthorizationFlow.getAuthorizationLogin(), "Frontier Login")
                )
            ).queue();
        }
    }
}
