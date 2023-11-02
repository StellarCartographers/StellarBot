package space.tscg.internal.server;

import com.google.common.eventbus.Subscribe;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import space.tscg.SCGBot;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.database.DefinedTable;
import space.tscg.database.defined.TSCGDatabase;
import space.tscg.database.entity.EliteInfo;
import space.tscg.internal.StellarAPI;
import space.tscg.internal.db.CarrierManager;
import space.tscg.internal.db.OwnerChannel.OwnerNames;
import space.tscg.internal.db.PublicChannel.PublicNames;
import space.tscg.internal.server.bus.event.CreatedCarrierEvent;
import space.tscg.internal.template.owner.CarrierOptionsEmbed;
import space.tscg.internal.template.owner.OwnerNotifyEmbed;
import space.tscg.internal.template.owner.UpdateEmbed;
import space.tscg.internal.template.publik.LocationEmbed;
import space.tscg.internal.template.publik.ModulesEmbed;
import space.tscg.internal.template.publik.OrdersEmbed;
import space.tscg.internal.template.publik.ServicesEmbed;
import space.tscg.internal.template.publik.ShipyardEmbed;

public class CarrierRegisterSubsciber
{
    @Subscribe
    public void onCarrierCreated(CreatedCarrierEvent event)
    {
        JDA jda = SCGBot.INSTANCE.getJda();
        
        IFleetCarrier carrier = TSCGDatabase.instance().get(DefinedTable.CARRIERS, event.getCarrierId(), IFleetCarrier.class);
        CarrierManager manager = TSCGDatabase.instance().get(DefinedTable.CHANNELS, event.getCarrierId(), CarrierManager.class);
        Guild guild = jda.getGuildById(event.getGuildId());
        UserSnowflake user = jda.getUserById(event.getMemberId());

        TextChannel ownerChannel = guild.getTextChannelById(event.getOwnerChannelId());
        TextChannel publicChannel = guild.getTextChannelById(event.getPublicChannelId());
        
        var locationEmbed = new LocationEmbed(carrier).getMessageData();
        var servicesEmbed = new ServicesEmbed(carrier).getMessageData();
        var buySellEmbed = new OrdersEmbed(carrier).getMessageData();
        var outfittingEmbed = new ModulesEmbed(carrier).getMessageData();
        var shipyardEmbed = new ShipyardEmbed(carrier).getMessageData();
        var chatMsgData = new MessageCreateBuilder().addContent("# Carrier Chat").build();

        publicChannel.sendMessage(chatMsgData).queue(s -> {
            s.createThreadChannel("Chat Thread").queue();
        });
        
        publicChannel.sendMessage(locationEmbed).queue(s -> {
            manager.getPublicChannel().setMessageId(PublicNames.LOCATION, s.getId());
        });
        
        publicChannel.sendMessage(servicesEmbed).queue(s -> {
            manager.getPublicChannel().setMessageId(PublicNames.SERVICES, s.getId());
        });
        
        publicChannel.sendMessage(buySellEmbed).queue(s -> {
            manager.getPublicChannel().setMessageId(PublicNames.ORDERS, s.getId());
        });
        
        publicChannel.sendMessage(outfittingEmbed).queue(s -> {
            manager.getPublicChannel().setMessageId(PublicNames.OUTFITTING, s.getId());
        });
        
        publicChannel.sendMessage(shipyardEmbed).queue(s -> {
            manager.getPublicChannel().setMessageId(PublicNames.SHIPYARD, s.getId());
            TSCGDatabase.instance().update(manager);
            
            var notifyEmbed = new OwnerNotifyEmbed(user != null ? user : guild.getMemberById(event.getMemberId())).getMessageData();
            var updateEmbed = new UpdateEmbed().getMessageData();
            var optionsEmbed = new CarrierOptionsEmbed(carrier).getMessageData();
            
            ownerChannel.sendMessage(notifyEmbed).queue(s1 -> {
                manager.getOwnerChannel().setMessageId(OwnerNames.INFO, s1.getId());
            });
            
            ownerChannel.sendMessage(updateEmbed).queue(s2 -> {
                manager.getOwnerChannel().setMessageId(OwnerNames.REFRESH, s2.getId());
            });
            
            ownerChannel.sendMessage(optionsEmbed).queue(s3 -> {
                manager.getOwnerChannel().setMessageId(OwnerNames.OPTIONS, s3.getId());
                TSCGDatabase.instance().update(manager);
                var elite = EliteInfo.Builder().carrierId(carrier.getId()).build();
                var member = StellarAPI.getMember(user);
                member.setElite(elite);
                TSCGDatabase.instance().update(member);
            });
        });
    }
}
