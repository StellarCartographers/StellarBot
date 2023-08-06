package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
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
        if (event.getAuthor().getId().equals(SCGBot.DEV_ID))
        {
            event.reply("Click the buttons for more info").addActionRow(Button.link(Authorization.askForLogin(), "Authorize")).queue();
            return;
        } else
        {
            Reply.EphemeralReply(event, "Command is only useable by the developer at this time");
        }
    }
}
