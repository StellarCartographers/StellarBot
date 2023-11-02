package space.tscg.internal.server;

import com.google.common.eventbus.Subscribe;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import space.tscg.SCGBot;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.database.DefinedTable;
import space.tscg.database.defined.TSCGDatabase;
import space.tscg.internal.db.CarrierManager;
import space.tscg.internal.db.OwnerChannel;
import space.tscg.internal.db.OwnerChannel.OwnerNames;
import space.tscg.internal.server.bus.event.CarrierOptionChangeEvent;
import space.tscg.internal.template.owner.CarrierOptionsEmbed;

public class ButtonSubsciber
{
    @Subscribe
    public void onCarrierOptionChangeEvent(CarrierOptionChangeEvent event)
    {
        JDA jda = SCGBot.INSTANCE.getJda();
        IFleetCarrier carrier = TSCGDatabase.instance().get(DefinedTable.CARRIERS, event.getCarrierId(), IFleetCarrier.class);
        CarrierManager manager = TSCGDatabase.instance().get(DefinedTable.CHANNELS, event.getCarrierId(), CarrierManager.class);
        OwnerChannel channel = manager.getOwnerChannel();
        TextChannel ownerChannel = jda.getTextChannelById(channel.getId());
        
        var optionsEmbed = new CarrierOptionsEmbed(carrier).getMessageData();
        
        ownerChannel.editMessageById(channel.getMessageId(OwnerNames.OPTIONS), MessageEditData.fromCreateData(optionsEmbed)).queue();
    }
}
