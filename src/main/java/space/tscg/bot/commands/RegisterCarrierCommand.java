package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
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
            event.replyEmbeds(this.getConsentEmbed())
            .addActionRow(CAPIButton.CONSENT.get(), Button.danger("decline", "Decline")).setEphemeral(true).queue();

            return;
        } else
        {
            Reply.EphemeralReply(event, "Command is only useable by the developer at this time");
        }
    }

    private MessageEmbed getConsentEmbed()
    {
        return Embed.newBuilder().description("""
            ```asciidoc
When you register your Fleet Carrier, our service will collect data from Frontier and Discord and store a portion of that data in our database. By clicking "Accept" below, you allow permission to access and retain the data necessary to offer this service. The link below provides a breakdown of what pieces of that data we save after obtaining it, why it is needed, and the method used if further measures are required to ensure it's stored securely ::

[Also states what information we WILL NOT request at any time while using this service]
```""")
            .field("StellarCartographers Github", "[Data Useage Link](https://github.com/StellarCartographers/StellarBot/blob/master/data.md)").toEmbed();
    }
}
