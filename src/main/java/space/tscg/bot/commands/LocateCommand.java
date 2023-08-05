package space.tscg.bot.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import io.github.readonly.command.option.Option;
import io.github.readonly.command.option.RequiredOption;
import io.github.readonly.common.util.ResultLevel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.tscg.SCGBot;
import space.tscg.bot.util.Reply;
import space.tscg.edsm.EDSM;
import space.tscg.edsm.api.logs.LastKnownPosition;
import space.tscg.edsm.api.systems.SystemInformation;
import space.tscg.edsm.api.systems.modal.Information;
import space.tscg.util.StringBuilderHelper;

public class LocateCommand extends SlashCommand
{
    private String LOCATOR_CHANNEL = "839655435967135755";
    
    public LocateCommand()
    {
        this.name("locate");
        this.description("Locates a commander with the provided name");
        this.setOptions(
            RequiredOption.text("cmdr", "Cmdr name"), 
            Option.trueFalse("show-system-info", "Include system info"), 
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
        
        if (event.hasOption("private") && event.getOption("private").getAsBoolean())
        {
            event.getInteraction().deferReply(true).queue();
        } else
        {
            event.getInteraction().deferReply(false).queue();
        }

        final LastKnownPosition position = EDSM.getPosition(event.getOption("cmdr").getAsString(), true, true);

        if (position.getMessageNumber() == 203)
        {
            Reply.Error(event, "**" + event.getOption("cmdr").getAsString() + "** could not be found in EDSM database");
            return;
        }

        SystemInformation systemInfo = null;
        if (event.hasOption("show-system-info"))
        {
            if (event.getOption("show-system-info").getAsBoolean())
            {
                systemInfo = EDSM.getSystemInformation(position.getSystem());
            }
        }

        List<MessageEmbed> embeds = new ArrayList<>();

        String url = "https://www.edsm.net/en/system/id/%s/name/%s".formatted(position.getSystemId(), position.getSystem().replace(" ", "+"));

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Located " + position.getUrl().split("/")[position.getUrl().split("/").length - 1]).addField("System", position.getSystem(), false).addField("EDSM Link", url, false).setColor(Color.GREEN);

        embeds.add(embedBuilder.build());

        if (systemInfo != null)
        {

            EmbedBuilder systemEmbedBuilder = new EmbedBuilder().setTitle("System Information").setDescription(this.buildSystemInfo(systemInfo)).setColor(Color.GREEN);
            embeds.add(systemEmbedBuilder.build());
        }

        event.getInteraction().getHook().sendMessageEmbeds(embeds).queue();
    }

    private String buildSystemInfo(SystemInformation information)
    {
        StringBuilderHelper string = new StringBuilderHelper();

        if (information.isPermitLocked())
        {
            string.nextLine("- **Permit Locked System**");
            string.nextLine(" - requires `*%s*` permit".formatted(information.getPermitName()));
            string.ln();
        }

        string.nextLine("- %s".formatted(information.getPrimaryStar().getType()));
        if (information.getPrimaryStar().isScoopable())
        {
            string.nextLine("- Primary Star is scoopable");
        }

        if (information.hasCoordinates())
        {
            double[] coords = information.getCoordinates().getAsArray();
            string.nextLine("- __**Coordinates**__");
            string.nextLine("  - (X) `%s`".formatted(coords[0]));
            string.nextLine("  - (Y) `%s`".formatted(coords[1]));
            string.nextLine("  - (Z) `%s`".formatted(coords[2]));
        }

        if (information.hasExtraInformation())
        {
            Information i = information.getInformation();
            string.nextLine("__**Population:**__ `%s`".formatted(i.getFormattedPopulation()));
            string.nextLine("__**Allegiance:**__ `%s`".formatted(i.getAllegiance()));
            string.nextLine("__**Government:**__ `%s`".formatted(i.getGovernment()));
            string.nextLine("__**Faction:**__ `%s`".formatted(i.getFaction()));
            string.nextLine("__**FactionState:**__ `%s`".formatted(i.getFactionState()));
            string.nextLine("__**Security:**__ `%s`".formatted(i.getSecurity()));
            string.nextLine("__**Economy:**__ `%s`".formatted(i.getEconomy()));
        } else
        {
            string.nextLine("__**Government:**__ `%s`".formatted(information.getInformation().getGovernment()));
            string.nextLine("__**Security:**__ `%s`".formatted(information.getInformation().getSecurity()));
        }

        return string.toString();
    }
}
