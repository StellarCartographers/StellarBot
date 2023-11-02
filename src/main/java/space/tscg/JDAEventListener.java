package space.tscg;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.tinylog.Logger;

import io.github.readonly.common.util.RGB;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import okhttp3.HttpUrl;
import space.tscg.internal.MessageButton;
import space.tscg.internal.RequiredCategory;
import space.tscg.properties.dot.Dotenv;
import space.tscg.util.text.Ansi;
import space.tscg.util.text.Ansi.Color;
import space.tscg.util.text.Embed;
import space.tscg.web.Web;
import space.tscg.web.domain.Domain;
import space.tscg.web.domain.Endpoint;

public class JDAEventListener extends ListenerAdapter
{
    EnumSet<Permission> permSet   = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.CREATE_PUBLIC_THREADS, Permission.MANAGE_PERMISSIONS, Permission.MESSAGE_HISTORY, Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);
    List<String>        permNames = permSet.stream().map(Permission::getName).toList();
    Guild               mainServer;
    List<String>        categories;
    SelfUser            selfUser;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event)
    {
        if (MessageButton.MOCK_FRONTIER.equals(event.getButton())) {
            HttpUrl.Builder builder = Domain.of("localhost:9050").toEndpoint(Endpoint.OAUTH_CALLBACK).toHttpUrl().newBuilder();
            builder.addQueryParameter("testing", null);
            builder.addQueryParameter("id", event.getUser().getId());
            
            Web.GET.call(builder.build());
        }
    }
    
    @Override
    public void onReady(ReadyEvent event)
    {
        
        
        this.selfUser = event.getJDA().getSelfUser();
        if (Dotenv.get("discord_server") == null)
        {
            Logger.error("DISCORD_SERVER variable not defined in .env file");
            return;
        }
        this.mainServer = event.getJDA().getGuildById(Dotenv.get("discord_server"));
        
        TextChannel channel = mainServer.getTextChannelById("1168310195575861269");
        channel.getHistoryFromBeginning(50).queue(history ->
        {
            if (history.isEmpty())
            {
                var data = new MessageCreateBuilder()
                    .addContent("# Fleet Carrier Registration")
                    .addEmbeds(
                        Embed.newBuilder()
                        .description("To register click on the Button attached!")
                        .color(RGB.GREEN)
                        .field("Can I Register?", 
                            Ansi.newBlock(
                                l -> l.add(Color.YELLOW, "Absolutely! As long as you own a Fleet Carrier in-game and agree to our Data Useage Requirements (which will be presented when registering), Anyone can register their Fleet Carrier"))
                        )
                        .field("What Does This Do?", 
                            Ansi.newBlock(
                                l -> l.add(Color.YELLOW, "Registered Carriers will get a public channel that will display the following information:"),
                                l -> l.blankLine().space(2).add(Color.WHITE, "►").space(1).add(Color.CYAN, "Public Chat Thread"),
                                l -> l.space(2).add(Color.WHITE, "►").space(1).add(Color.CYAN, "Location"),
                                l -> l.space(2).add(Color.WHITE, "►").space(1).add(Color.CYAN, "Services"),
                                l -> l.space(2).add(Color.WHITE, "►").space(1).add(Color.CYAN, "Buy/Sell Orders"),
                                l -> l.space(2).add(Color.WHITE, "►").space(1).add(Color.CYAN, "Outfitting/Shipyard Stock"),
                                l -> l.blankLine().add(Color.YELLOW, "Information can be dynamically updated using our plugins for EDMC and/or EDDiscovery")
                        )).toEmbed())
                    .addActionRow(MessageButton.REGISTER_CARRIER.getButton())
                    .build();
                channel.sendMessage(data).queue();
            }
        });

        if (mainServer == null)
        {
            Logger.error("Cannot find discord server with id: " + Dotenv.get("discord_server"));
            Logger.error("Please add the Bot to the right Server");
            return;
        }
        
        this.categories = mainServer.getCategories().stream().map(Category::getName).toList();
        if (this.checkCategory("fleet-carriers"))
        {
            RequiredCategory.PUBLIC = this.mainServer.getCategoriesByName("fleet-carriers", false).get(0);
        }
        if (this.checkCategory("carrier-owners"))
        {
            RequiredCategory.OWNERS = this.mainServer.getCategoriesByName("carrier-owners", false).get(0);
        }
    }

    private boolean checkCategory(String category)
    {
        if (!categories.contains(category))
        {
            Logger.error("Missing required Channel Category: '%s'".formatted(category));
            Logger.error("  - Please create Category: '%s' and ensure the bot has the right permissions in that category".formatted(category));
            return false;
        } else
        {
            PermissionOverride perms = mainServer.getCategoriesByName(category, false).get(0).getPermissionOverride(mainServer.getRoleByBot(this.selfUser));
            if (perms == null)
            {
                Logger.error("Missing required permissions in Category: '%s'".formatted(category));
                Logger.error("  The following must be ALLOWED for the bot in category: ");
                Logger.error("  [" + String.join(", ", permNames) + "]");
                return false;
            }
            var allowed = perms.getAllowed();
            var missing = permSet.stream().filter(e -> !allowed.contains(e)).collect(Collectors.toList());
            if (!missing.isEmpty())
            {
                Logger.error("Not all ALLOWED Permissions are set in Category: '%s'");
                Logger.error("  The following must be ALLOWED for the bot in category: ");
                Logger.error("  [" + String.join(", ", missing.stream().map(Permission::getName).toList()) + "]");
                return false;
            }
        }
        return true;
    }
}
