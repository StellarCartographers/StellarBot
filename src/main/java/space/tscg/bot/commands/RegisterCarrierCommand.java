package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;

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
        
    }

}
