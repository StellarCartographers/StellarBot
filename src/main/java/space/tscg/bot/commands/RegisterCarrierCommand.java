package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import space.tscg.SCGBot;
import space.tscg.bot.util.Reply;
import space.tscg.capi.Authorization;

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
            Reply.EphemeralReply(event, Authorization.askForLogin());
        } else {
            Reply.EphemeralReply(event, "Command is only useable by the developer at this time");
        }
    }
}
