package space.tscg.internal.template.publik;

import java.util.LinkedList;
import java.util.List;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.api.carrier.ICarrierShip;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.internal.MessageButtons;
import space.tscg.internal.template.MessageTemplate;
import space.tscg.util.text.Ansi;
import space.tscg.util.text.Ansi.Color;
import space.tscg.util.text.Ansi.Style;
import space.tscg.util.text.Embed;

public class ShipyardEmbed implements MessageTemplate
{
    private IFleetCarrier      fleetCarrier;
    private List<ICarrierShip> ships;

    public ShipyardEmbed(IFleetCarrier fleetCarrier)
    {
        this.fleetCarrier = fleetCarrier;
        this.ships = fleetCarrier.getMarket().getShips();
    }

    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        var buttons = new LinkedList<Button>();
        var service = this.fleetCarrier.getServices();

        Embed sEmbed = Embed.newBuilder();
        sEmbed.title("╞═════════════════ Shipyard ═════════════════╡");
        if (!service.isOutfittingInstalled())
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.RED, "Service Not Installed")).toString());
        } else if (!service.getOutfitting().isEnabled())
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.YELLOW, "Service Suspended")).toString());
        } else if (this.ships.isEmpty())
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.YELLOW, "No Stock")).toString());
        } else
        {
            sEmbed.description(Ansi.newBlock(l -> l.add(Style.BOLD, Color.CYAN, ships.size()).space(1).add(Color.GREEN, "Ships In Stock")).toString());
            buttons.add(MessageButtons.SHIPYARD_LIST.getButton());
        }

        builder.addEmbeds(sEmbed.toEmbed());
        builder.addActionRow(buttons);

        return builder.build();
    }
}
