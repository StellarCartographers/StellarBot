package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import io.github.readonly.common.util.RGB;
import space.tscg.util.text.Embed;

public class ShutdownCommand extends SlashCommand
{

    public ShutdownCommand()
    {
        super("shutdown");
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
        if(!event.isFromDeveloper()) {
            event.respond(Embed.descriptionEmbed("Developer Only Command", RGB.RED));
            return;
        }
        
        event.respond(Embed.descriptionEmbed("Shutting Down", RGB.GREEN));
        event.getJDA().shutdown();
    }
}