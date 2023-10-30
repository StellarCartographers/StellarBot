package space.tscg.internal.template.publik;

import java.util.LinkedList;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.internal.MessageButtons;
import space.tscg.internal.template.MessageTemplate;
import space.tscg.util.text.Ansi;
import space.tscg.util.text.Ansi.Color;
import space.tscg.util.text.Ansi.Style;
import space.tscg.util.text.Embed;

public class ModulesEmbed implements MessageTemplate
{
    private IFleetCarrier fleetCarrier;

    public ModulesEmbed(IFleetCarrier fleetCarrier)
    {
        this.fleetCarrier = fleetCarrier;
    }

    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        var buttons = new LinkedList<Button>();
        var service = this.fleetCarrier.getServices();
        var modules = fleetCarrier.getMarket().getModules();
        
        builder.addContent("# Carrier Stock");
        Embed sEmbed = Embed.newBuilder();
        sEmbed.title("╞═════════════════ Outfitting ════════════════╡");
        if (!service.isShipyardInstalled())
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.RED, "Service Not Installed")).toString());
        } else if (!service.getShipyard().isEnabled())
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.YELLOW, "Service Suspended")).toString());
        } else if (modules.isEmpty())
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.YELLOW, "No Stock")).toString());
        } else
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.CYAN, modules.size()).space(1).add(Color.GREEN, "Modules In Stock")).toString());
            buttons.add(MessageButtons.MODULE_LIST.getButton());
        }

        builder.addEmbeds(sEmbed.toEmbed());
        builder.addActionRow(buttons);

        return builder.build();
    }
}
