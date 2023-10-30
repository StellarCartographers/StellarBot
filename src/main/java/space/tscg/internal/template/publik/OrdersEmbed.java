package space.tscg.internal.template.publik;

import java.util.LinkedList;
import java.util.List;

import elite.dangerous.capi.modal.fleetcarrier.Orders;
import io.github.readonly.common.util.RGB;
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

public class OrdersEmbed implements MessageTemplate
{
    private Orders       carrierOrders;
    private List<Button> buttons;

    public OrdersEmbed(IFleetCarrier fleetCarrier)
    {
        this.carrierOrders = fleetCarrier.getOrders();
        this.buttons = new LinkedList<Button>();
    }

    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        var commodities = this.carrierOrders.getCommodities();
        var microResources = this.carrierOrders.getMicroResources();
        var noCommoditySaleOrBuy = !commodities.isBuying() && !commodities.isSelling();
        var noMicroResourcesSaleOrBuy = !microResources.isBuying() && !microResources.isSelling();

        builder.addContent("# Buy/Sell Orders");
        if (noCommoditySaleOrBuy && noMicroResourcesSaleOrBuy) {
            var none = Ansi.newBlock(l -> l.add(Color.YELLOW, "No Current Orders")).toString();
            builder.addEmbeds(Embed.newBuilder().description(none).toEmbed());
            return builder.build();
        }

        Embed buyEmbed = Embed.newBuilder();
        buyEmbed.title("╞══════════════════ Buying ══════════════════╡");
        if(!commodities.isBuying() && !microResources.isBuying())
        {
            buyEmbed.description(Ansi.newBlock(l -> l.add(Color.YELLOW, "Not Currently Buying")).toString());
            buyEmbed.color(RGB.ORANGE);
        } else {
            this.addCommodityPurchases(buyEmbed);
            this.addMicroResourcePurchases(buyEmbed);
            buttons.add(MessageButtons.PURCHASE_LIST.getButton());
        }
        builder.addEmbeds(buyEmbed.toEmbed());

        // -- //

        Embed sellEmbed = Embed.newBuilder();
        sellEmbed.title("╞══════════════════ Selling ═════════════════╡");
        if(!commodities.isSelling() && !microResources.isSelling())
        {
            sellEmbed.description(Ansi.newBlock(l -> l.add(Color.YELLOW, "Not Currently Selling")).toString());
            sellEmbed.color(RGB.ORANGE);
        } else {
            this.addCommoditySales(sellEmbed);
            this.addMicroResourceSales(sellEmbed);
            buttons.add(MessageButtons.SELL_LIST.getButton());
        }
        builder.addEmbeds(sellEmbed.toEmbed());

        if (!this.buttons.isEmpty()) {
            builder.addActionRow(this.buttons);
        }
        return builder.build();
    }

    private void addCommoditySales(Embed embed)
    {
        var commodities = this.carrierOrders.getCommodities();
        String info;
        if(commodities.isSelling()) {
            info = Ansi.newBlock(l -> l.add(Style.BOLD, Color.CYAN, commodities.getSales().size()).space(1).add(Color.GREEN, "Sale Orders")).toString();
        } else {
            info = Ansi.newBlock(l -> l.add(Color.YELLOW, "Not Currently Selling")).toString();
        }
        embed.field("Commodities", info);
    }

    private void addMicroResourceSales(Embed embed)
    {
        var microResources = this.carrierOrders.getMicroResources();
        String info;
        if(microResources.isSelling()) {
            info = Ansi.newBlock(l -> l.add(Style.BOLD, Color.CYAN, microResources.getSales().size()).space(1).add(Color.GREEN, "Sale Orders")).toString();
        } else {
            info = Ansi.newBlock(l -> l.add(Color.YELLOW, "Not Currently Selling")).toString();
        }
        embed.field("Micro Resources", info);
    }

    private void addCommodityPurchases(Embed embed)
    {
        var commodities = this.carrierOrders.getCommodities();
        String info;
        if(commodities.isBuying()) {
            info = Ansi.newBlock(l -> l.add(Style.BOLD, Color.CYAN, commodities.getPurchases().size()).space(1).add(Color.GREEN, "Buy Orders")).toString();
        } else {
            info = Ansi.newBlock(l -> l.add(Color.YELLOW, "Not Currently Buying")).toString();
        }
        embed.field("Commodities", info);
    }

    private void addMicroResourcePurchases(Embed embed)
    {
        var microResources = this.carrierOrders.getMicroResources();
        String info;
        if(microResources.isBuying()) {
            info = Ansi.newBlock(l -> l.add(Style.BOLD, Color.CYAN, microResources.getPurchases().size()).space(1).add(Color.GREEN, "Buy Orders")).toString();
        } else {
            info = Ansi.newBlock(l -> l.add(Color.YELLOW, "Not Currently Buying")).toString();
        }
        embed.field("Micro Resources", info);
    }
}
