package space.tscg;

import io.github.readonly.command.DiscordInfo;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import space.tscg.capi.AuthorizationFlow;
import space.tscg.capi.CAPIButton;
import space.tscg.capi.Constants;
import space.tscg.util.Embed;

public class JDAEventListener extends ListenerAdapter
{
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event)
    {
        DiscordInfo info = DiscordInfo.of(event.getUser());

        if (event.getComponentId().equals("decline"))
        {
            event.editMessageEmbeds(Embed.newBuilder().description("User declined permissions, You may safely dismiss this message").toEmbed()).setComponents().queue();
        }

        if (event.getComponentId().equals(CAPIButton.CONSENT.getId()))
        {
            event.editMessageEmbeds().setComponents(ActionRow.of(Button.link(AuthorizationFlow.getAuthorizationLogin(info), "Frontier Login"))).queue(m ->
            {
                BotLog.info(event.getChannelType().name());
                m.retrieveOriginal().queue(msg -> Constants.addMessageChannelReference(event.getUser().getId(), msg.getId(), msg.getChannel().getId()));
            });
        }
    }
}
