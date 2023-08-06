package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.tscg.SCGBot;
import space.tscg.bot.util.Reply;
import space.tscg.capi.CAPIButton;
import space.tscg.util.Embed;

public class RegisterCarrierCommand extends SlashCommand
{
    public RegisterCarrierCommand()
    {
        name("register-carrier");
        description("Register your Fleet Carrier");
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
     
        if (event.getAuthor().getId().equals(SCGBot.DEV_ID))
        {
            event.replyEmbeds(this.getConsentEmbed()).addActionRow(CAPIButton.CONSENT.get()).setEphemeral(true).queue();

            return;
        } else
        {
            Reply.EphemeralReply(event, "Command is only useable by the developer at this time");
        }
    }

    private MessageEmbed getConsentEmbed()
    {
        return Embed.newBuilder().description("""
        	When registering your Fleet Carrier, our service will collect certain data from Frontier and Discord and also store parts of that data in our database.\s\
        	Below will be a document link of what information will be stored after accessing, why storing it is needed, and how it's stored. As well as certain information\s\
        	the service WILL NOT request and/or store at any time during your use of this service. To continue with registration, and by clicking 'Accept' you are giving\s\
        	consent to access and store the required data needed to perform this service. And you have read the data useage document linkd below.""")
            .field("Document Link", "[data.md](https://github.com/StellarCartographers/StellarBot/blob/master/data.md)").toEmbed();
    }
}
