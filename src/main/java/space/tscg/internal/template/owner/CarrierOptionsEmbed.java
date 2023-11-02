package space.tscg.internal.template.owner;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.SCGBot;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.database.DefinedTable;
import space.tscg.database.defined.TSCGDatabase;
import space.tscg.internal.MessageButton;
import space.tscg.internal.db.CarrierManager;
import space.tscg.internal.db.Name;
import space.tscg.internal.db.PublicChannel.PublicNames;
import space.tscg.internal.template.MessageTemplate;
import space.tscg.util.text.Embed;

public class CarrierOptionsEmbed implements MessageTemplate
{
    CarrierManager manager;
    
    public CarrierOptionsEmbed(IFleetCarrier carrier)
    {
        this.manager = TSCGDatabase.instance().get(DefinedTable.CHANNELS, carrier.getId(), CarrierManager.class);
    }
    
    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        builder.addContent("# Carrier Options");
        Embed embed = Embed.newBuilder();
        
        embed.fieldInline("**Buy/Sell Orders**", this.buildField(PublicNames.ORDERS));
        embed.fieldInline("**Outfitting Stock**", this.buildField(PublicNames.OUTFITTING));
        embed.fieldInline("**Shipyard Stock**", this.buildField(PublicNames.SHIPYARD));
        
        this.putButtons(builder);
        
        builder.addEmbeds(embed.toEmbed());
        
        return builder.build();
    }
    
    private void putButtons(MessageCreateBuilder builder)
    {
        var buttons = new ArrayList<Button>();
        
        if(this.manager.getPublicChannel().isHidden(PublicNames.ORDERS))
            buttons.add(MessageButton.ORDERS_DISPLAY.getButton());
        else
            buttons.add(MessageButton.ORDERS_HIDE.getButton());
        // -- //
        if(this.manager.getPublicChannel().isHidden(PublicNames.OUTFITTING))
            buttons.add(MessageButton.OUTFITTING_DISPLAY.getButton());
        else
            buttons.add(MessageButton.OUTFITTING_HIDE.getButton());
        // -- //
        if(this.manager.getPublicChannel().isHidden(PublicNames.SHIPYARD))
            buttons.add(MessageButton.SHIPYARD_DISPLAY.getButton());
        else
            buttons.add(MessageButton.SHIPYARD_HIDE.getButton());
        
        builder.addActionRow(buttons);
    }
    
    private String buildField(Name name)
    {
        String tmpl = "%s\n%s";
        return tmpl.formatted(this.manager.getPublicChannel().isHidden(name) ? this.getHiddenBlock() : this.getDisplayedBlock(), this.messageLink(name));
    }
    
    private String getDisplayedBlock()
    {
        return """
               ```diff
               + ⁣   Displayed    ⁣
               ```
               """;
    }
    
    private String getHiddenBlock()
    {
        return """
               ```diff
               - ⁣    Hidden      ⁣
               ```
               """;
    }
    
    private String messageLink(Name name)
    {
        TextChannel channel = SCGBot.INSTANCE.getJda().getTextChannelById(this.manager.getPublicChannel().getId());
        String channelId = channel.getId();
        String guildId = channel.getGuild().getId();
        String msgId = this.manager.getPublicChannel().getMessageId(name);
        return "https://discord.com/channels/%s/%s/%s".formatted(guildId, channelId, msgId);
    }
}
