package space.tscg.bot.commands;

import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

import io.github.readonly.command.SlashCommand;

public abstract class AbstractCommand extends SlashCommand
{
    protected TaggedLogger logger;
    
    protected AbstractCommand(String name)
    {
        this(name, null);
    }
    
    protected AbstractCommand(String name, String desc)
    {
        super(name, desc);
        this.logger = Logger.tag(name + "-command");
    }
}
