package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.SCGBot;
import space.tscg.bot.util.Reply;

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
        if(event.getAuthor().getId().equals(SCGBot.DEV_ID))
        {
            MessageCreateData smg = new MessageCreateBuilder()
                .addEmbeds(Reply.simpleEmbed("Sign into FrontierStore to verify your FleetCarrier"))
                .addActionRow(Button.success("auth", "Authorize"))
                .build();
            Reply.EphemeralReply(event, smg);
        } else {
            Reply.EphemeralReply(event, "Command is only useable by the developer at this time");
        }
    }
}
