package space.tscg.bot;

import java.util.EnumSet;

import org.tinylog.Logger;

import io.github.readonly.common.util.RGB;
import io.github.readonly.common.waiter.EventWaiter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import space.tscg.SCGBot;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.database.defined.TSCGDatabase;
import space.tscg.database.entity.TSCGMember;
import space.tscg.internal.MessageButton;
import space.tscg.internal.RequiredCategory;
import space.tscg.internal.StellarAPI;
import space.tscg.internal.db.CarrierManager;
import space.tscg.internal.db.PublicChannel.PublicNames;
import space.tscg.internal.server.bus.ServerEventWaiter;
import space.tscg.internal.server.bus.event.AuthorizedEvent;
import space.tscg.internal.server.bus.event.CarrierOptionChangeEvent;
import space.tscg.internal.server.bus.event.CreatedCarrierEvent;
import space.tscg.internal.template.HidableMessageTemplate;
import space.tscg.internal.template.publik.ModulesEmbed;
import space.tscg.internal.template.publik.OrdersEmbed;
import space.tscg.internal.template.publik.ShipyardEmbed;
import space.tscg.misc.error.NotAFleetCarrierOwner;
import space.tscg.util.text.CodeBlock;
import space.tscg.util.text.Embed;

public class ButtonListener implements EventListener
{
    @Override
    public void onEvent(GenericEvent event)
    {
        if (event instanceof ButtonInteractionEvent)
        {
            var interaction = (ButtonInteractionEvent) event;

            switch (MessageButton.get(interaction)) {
                case REGISTER_CARRIER:
                    this.handleRegisterCarrier(interaction);
                    break;
                case ORDERS_HIDE:
                    this.handleHideOrders(interaction);
                    break;
                case ORDERS_DISPLAY:
                    handleDisplayOrders(interaction);
                    break;
                case SHIPYARD_HIDE:
                    handleHideShipyard(interaction);
                    break;
                case SHIPYARD_DISPLAY:
                    handleDisplayShipyard(interaction);
                    break;
                case OUTFITTING_HIDE:
                    handleHideOutfitting(interaction);
                    break;
                case OUTFITTING_DISPLAY:
                    handleDisplayOutfitting(interaction);
                    break;
                default:
                    this.handleDefault(interaction);
            }
        }
    }

    private void handleDefault(ButtonInteractionEvent event)
    {
        event.reply("Button Action Not Implemented Yet").setEphemeral(true).queue();
    }

    private void handleHideOrders(ButtonInteractionEvent event)
    {
        var member = TSCGMember.fromUserSnowflake(event.getMember());
        var manager = CarrierManager.fromMember(member);
        var publicChannel = manager.getPublicChannel();
        var channel = event.getJDA().getChannelById(TextChannel.class, publicChannel.getId());
        event.deferEdit().queue();
        
        channel.editMessageEmbedsById(publicChannel.getMessageId(PublicNames.ORDERS), HidableMessageTemplate.getHiddenMessageEmbed()).queue(m ->
        {
            publicChannel.setHidden(PublicNames.ORDERS);
            TSCGDatabase.instance().update(manager);
            new CarrierOptionChangeEvent(manager.getId()).post();
        });
    }

    private void handleDisplayOrders(ButtonInteractionEvent event)
    {
        var member = TSCGMember.fromUserSnowflake(event.getMember());
        var manager = CarrierManager.fromMember(member);
        var publicChannel = manager.getPublicChannel();
        var channel = event.getJDA().getChannelById(TextChannel.class, publicChannel.getId());
        event.deferEdit().queue();
        
        var msg = new OrdersEmbed(IFleetCarrier.fromMmember(member)).getMessageData();
        channel.editMessageById(publicChannel.getMessageId(PublicNames.ORDERS), MessageEditData.fromCreateData(msg)).queue(m ->
        {
            publicChannel.setVisable(PublicNames.ORDERS);
            TSCGDatabase.instance().update(manager);
            new CarrierOptionChangeEvent(manager.getId()).post();
        });
    }
    
    private void handleHideShipyard(ButtonInteractionEvent event)
    {
        var member = TSCGMember.fromUserSnowflake(event.getMember());
        var manager = CarrierManager.fromMember(member);
        var publicChannel = manager.getPublicChannel();
        var channel = event.getJDA().getChannelById(TextChannel.class, publicChannel.getId());
        event.deferEdit().queue();
        
        channel.editMessageEmbedsById(publicChannel.getMessageId(PublicNames.SHIPYARD), HidableMessageTemplate.getHiddenMessageEmbed()).queue(m ->
        {
            publicChannel.setHidden(PublicNames.SHIPYARD);
            TSCGDatabase.instance().update(manager);
            new CarrierOptionChangeEvent(manager.getId()).post();
        });
    }
    
    private void handleDisplayShipyard(ButtonInteractionEvent event)
    {
        var member = TSCGMember.fromUserSnowflake(event.getMember());
        var manager = CarrierManager.fromMember(member);
        var publicChannel = manager.getPublicChannel();
        var channel = event.getJDA().getChannelById(TextChannel.class, publicChannel.getId());
        event.deferEdit().queue();
        
        var msg = new ShipyardEmbed(IFleetCarrier.fromMmember(member)).getMessageData();
        channel.editMessageById(publicChannel.getMessageId(PublicNames.SHIPYARD), MessageEditData.fromCreateData(msg)).queue(m ->
        {
            publicChannel.setVisable(PublicNames.SHIPYARD);
            TSCGDatabase.instance().update(manager);
            new CarrierOptionChangeEvent(manager.getId()).post();
        });
    }
    
    private void handleHideOutfitting(ButtonInteractionEvent event)
    {
        var member = TSCGMember.fromUserSnowflake(event.getMember());
        var manager = CarrierManager.fromMember(member);
        var publicChannel = manager.getPublicChannel();
        var channel = event.getJDA().getChannelById(TextChannel.class, publicChannel.getId());
        event.deferEdit().queue();
        
        channel.editMessageEmbedsById(publicChannel.getMessageId(PublicNames.OUTFITTING), HidableMessageTemplate.getHiddenMessageEmbed()).queue(m ->
        {
            publicChannel.setHidden(PublicNames.OUTFITTING);
            TSCGDatabase.instance().update(manager);
            new CarrierOptionChangeEvent(manager.getId()).post();
        });
    }
    
    private void handleDisplayOutfitting(ButtonInteractionEvent event)
    {
        var member = TSCGMember.fromUserSnowflake(event.getMember());
        var manager = CarrierManager.fromMember(member);
        var publicChannel = manager.getPublicChannel();
        var channel = event.getJDA().getChannelById(TextChannel.class, publicChannel.getId());
        event.deferEdit().queue();
        
        var msg = new ModulesEmbed(IFleetCarrier.fromMmember(member)).getMessageData();
        channel.editMessageById(publicChannel.getMessageId(PublicNames.OUTFITTING), MessageEditData.fromCreateData(msg)).queue(m ->
        {
            publicChannel.setVisable(PublicNames.OUTFITTING);
            TSCGDatabase.instance().update(manager);
            new CarrierOptionChangeEvent(manager.getId()).post();
        });
    }

    private void handleRegisterCarrier(ButtonInteractionEvent event)
    {
        EventWaiter waiter = SCGBot.INSTANCE.getWaiter();
        ServerEventWaiter eventWaiter = new ServerEventWaiter();

        Member member = event.getMember();

        Logger.info("Is Member Null? " + member == null);

        event.replyEmbeds(this.getConsentEmbed()).addActionRow(MessageButton.CONSENT.getButton(), MessageButton.DENY.getButton()).setEphemeral(true).queue();

        waiter.waitForEvent(ButtonInteractionEvent.class, p -> p.getMember().equals(member), action ->
        {
            InteractionHook hook = action.getHook();
            this.handleButtonEvent(action);
            eventWaiter.waitForEvent(AuthorizedEvent.class, done ->
            {
                // AuthorizedEvent is received when the API server retrieves the callback from Frontier Oauth
                // Inform the user we are validating carrier ownership
                hook.editOriginalEmbeds(Embed.descriptionEmbed("Validating Carrier Ownership", RGB.YELLOW).toEmbed()).setComponents().queue();

                // Call /fleetcarrier via our API Server and get the Result
                var carrierResult = StellarAPI.getFleetCarrier(member.getId(), true);
                if (carrierResult.isErr())
                {
                    var errState = carrierResult.getError().state();
                    if (NotAFleetCarrierOwner.is(errState.getData()))
                    {
                        // Was not a carrier owner, inform the user and cease operation
                        hook.editOriginalEmbeds(Embed.descriptionEmbed("You must own a fleetcarrier to register a fleetcarrier", RGB.RED).toEmbed()).setComponents().queue();
                        return;
                    }

                    Embed embed = Embed.newBuilder().description("There was an error during carrier validation, please report this to staff").field("Error Encountered", CodeBlock.style("json").line(errState.toJson()).toString()).color(RGB.RED);

                    // Result was an error, inform the user and cease operation
                    hook.editOriginalEmbeds(embed.toEmbed()).setComponents().queue();
                    return;
                }
                var carrier = carrierResult.get();

                var createResult = StellarAPI.createFleetCarrier(carrier);
                if (createResult.isErr())
                {
                    hook.editOriginalEmbeds(Embed.descriptionEmbed("There was an error during carrier creation, please report this to staff", RGB.RED).toEmbed()).setComponents().queue();
                    return;
                } else
                {
                    hook.editOriginalEmbeds(Embed.newBuilder().title("Validation Complete").description("## Creating Fleet Carrier Channels").footer("This message can be dismissed").color(RGB.GREEN).toEmbed()).setComponents().queue();
                }

                Guild guild = event.getGuild();

                //!fr
                guild.createTextChannel(carrier.getName(), RequiredCategory.PUBLIC).queue(
                    publicChannel -> {
                        guild.createTextChannel(member.getUser().getName() + "-" + carrier.getCallsign().replace("-", ""), RequiredCategory.OWNERS).queue(
                            ownerChannel -> {
                                ownerChannel.getPermissionContainer().getManager().putMemberPermissionOverride( 
                                    event.getMember().getIdLong(), 
                                    EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), 
                                    EnumSet.of(Permission.CREATE_INSTANT_INVITE)
                                ).queue(finished -> {
                                    CarrierManager carrierManager = CarrierManager.create(carrier.getCarrierId(), ownerChannel.getId(), publicChannel.getId());
                                    TSCGDatabase.instance().create(carrierManager).after(i -> {
                                        new CreatedCarrierEvent(member, carrier.getCarrierId(), member.getId(), event.getGuild().getId(), ownerChannel.getId(), publicChannel.getId()).post();
                                    });
                                });
                            }
                        );
                    }
                );
                //@fr
            });
        });
    }

    private MessageEmbed getConsentEmbed()
    {
        return Embed.newBuilder().title("Data Useage Requirements")
                        .description("""
                                     ```asciidoc
                                     When you register your Fleet Carrier, our service will collect data from Frontier and Discord and store a portion of that data in our database. By clicking "Accept" below, you grant TSCG Network permission to access and retain the data necessary to offer this service. The link below provides a breakdown of what pieces of that data we save after obtaining it, why it is needed, and the method used if further measures are required to ensure it's stored securely ::

                                     [Also states what information we WILL NOT request at any time while using this service]
                                     ```""")
                        .field("StellarCartographers Network", "[Data Useage Link](https://tscg.network/data)").toEmbed();
    }

    private void handleButtonEvent(ButtonInteractionEvent event)
    {
        if (MessageButton.DENY.equals(event.getButton()))
        {
            event.editMessageEmbeds(Embed.newBuilder().description("User declined permissions, You may safely dismiss this message").toEmbed()).setComponents().queue();
        }

        if (MessageButton.CONSENT.equals(event.getButton()))
        {
            var info = Embed.descriptionEmbed("This would normally be the OAuth link. Which would take you to Frontiers auth login, " + "this mocks the actions that happen on the backend when the API Server receives a sucessfull authorization. If sucessfull, "
                            + "the user is directed to a \"authenticated\" landing page (https://tscg.network/authenticated)", RGB.GREEN);
            event.editMessageEmbeds().setEmbeds(info.toEmbed()).setComponents(ActionRow.of(MessageButton.MOCK_FRONTIER.getButton())).queue();
        }
    }
}
