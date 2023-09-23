package space.tscg;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import okhttp3.HttpUrl;
import space.tscg.common.domain.Endpoint;
import space.tscg.common.http.Http;
import space.tscg.util.CAPIButton;
import space.tscg.util.Embed;

public class JDAEventListener extends ListenerAdapter
{
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event)
    {
        HttpUrl.Builder builder = Endpoint.OAUTH_AUTHLINK.toHttpUrl().newBuilder();
        
        String code = Http.GET.call(builder.addQueryParameter("discordid", event.getUser().getId()).build()).getBody();

        if (event.getComponentId().equals("decline"))
        {
            event.editMessageEmbeds(Embed.newBuilder().description("User declined permissions, You may safely dismiss this message").toEmbed()).setComponents().queue();
        }

        if (event.getComponentId().equals(CAPIButton.CONSENT.getId()))
        {
            event.editMessageEmbeds().setComponents(ActionRow.of(Button.link(code, "Frontier Login"))).queue();
        }
    }
}
