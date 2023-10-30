package space.tscg.bot.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import io.github.readonly.command.option.Option;
import io.github.readonly.command.option.RequiredOption;
import io.github.readonly.common.util.ResultLevel;
import io.github.readonly.doc.annotation.docstandard.CommandInfo;
import space.tscg.api.edsm.EDSM;
import space.tscg.api.edsm.api.Location;
import space.tscg.bot.util.Reply;

@CommandInfo(name = "distance", description = "Check the distance between two coordinates")
public class DistanceCommand extends SlashCommand
{

    private String LOCATOR_CHANNEL = "839655435967135755";
    
    public DistanceCommand()
    {
        super("distance", "Check the distance between two coordinates");
        options(
            RequiredOption.text("from", "Enter either Cmdr name or System name"), 
            RequiredOption.text("to", "Enter either Cmdr name or System name"), 
            Option.trueFalse("private", "Only the user that issued command can see the reply")
        );
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
        if (!event.getChannel().getId().equals("1117789491621527593"))
        {
            Reply.EphemeralReply(event, ResultLevel.ERROR, "Command must be used in " + event.getJDA().getGuildChannelById(LOCATOR_CHANNEL).getJumpUrl());
            return;
        }

        event.getEvent().deferReply();

        String o1 = event.getOption("from").getAsString();
        String o2 = event.getOption("to").getAsString();

        Optional<Location> c1 = EDSM.getCoordinates(o1);
        Optional<Location> c2 = EDSM.getCoordinates(o2);

        List<String> notFound = new ArrayList<>();

        if (c1.isEmpty())
        {
            notFound.add("**" + o1 + "** could not be found in EDSM database");
        }
        if (c2.isEmpty())
        {
            notFound.add("**" + o2 + "** could not be found in EDSM database");
        }

        var cc1 = c1.get();
        var cc2 = c2.get();
        
        if (notFound.isEmpty())
        {
            double        d       = Math.sqrt(Math.pow((cc1.x - cc2.x), 2) + Math.pow((cc1.y - cc2.y), 2) + Math.pow((cc1.z - cc2.z), 2));
            DecimalFormat df      = new DecimalFormat("0.00");
            String        formate = "Distance between **" + cc1.name + "** and **" + cc2.name + "** is:\n# " + df.format(d) + " ly";
            Reply.Success(event, formate);
        } else
        {
            String e = String.join("\n", notFound);
            Reply.Error(event, e);
        }
    }
}
