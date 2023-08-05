package space.tscg.bot.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import io.github.readonly.command.option.Option;
import io.github.readonly.command.option.RequiredOption;
import io.github.readonly.common.util.ResultLevel;
import io.github.readonly.doc.annotation.docstandard.CommandInfo;
import space.tscg.SCGBot;
import space.tscg.bot.edsm.EDSM;
import space.tscg.bot.edsm.api.Location;
import space.tscg.bot.util.Reply;

@CommandInfo(name = "distance", description = "Check the distance between two coordinates")
public class DistanceCommand extends SlashCommand
{

    private String LOCATOR_CHANNEL = "839655435967135755";
    
    public DistanceCommand()
    {
        this.name("distance");
        this.description("Check the distance between two coordinates");
        this.setOptions(
            RequiredOption.text("from", "Enter either Cmdr name or System name"), 
            RequiredOption.text("to", "Enter either Cmdr name or System name"), 
            Option.trueFalse("private", "Only the user that issued command can see the reply")
        );
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
        if (!SCGBot.ALLOWED_CHANNELS.contains(event.getChannel().getId()))
        {
            Reply.EphemeralReply(event, ResultLevel.ERROR, "Command must be used in " + event.getJDA().getGuildChannelById(LOCATOR_CHANNEL).getJumpUrl());
            return;
        }

        event.getEvent().deferReply();

        String o1 = event.getOption("from").getAsString();
        String o2 = event.getOption("to").getAsString();

        Location c1 = EDSM.getCoordinates(o1);
        Location c2 = EDSM.getCoordinates(o2);

        List<String> notFound = new ArrayList<>();

        if (c1 == null)
        {
            notFound.add("**" + o1 + "** could not be found in EDSM database");
        }
        if (c2 == null)
        {
            notFound.add("**" + o2 + "** could not be found in EDSM database");
        }

        if (notFound.isEmpty())
        {
            double        d       = Math.sqrt(Math.pow((c1.x - c2.x), 2) + Math.pow((c1.y - c2.y), 2) + Math.pow((c1.z - c2.z), 2));
            DecimalFormat df      = new DecimalFormat("0.00");
            String        formate = "Distance between **" + c1.name + "** and **" + c2.name + "** is:\n# " + df.format(d) + " ly";
            Reply.Success(event, formate);
        } else
        {
            String e = String.join("\n", notFound);
            Reply.Error(event, e);
        }
    }
}
