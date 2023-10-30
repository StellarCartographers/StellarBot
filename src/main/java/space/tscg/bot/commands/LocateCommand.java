package space.tscg.bot.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import io.github.readonly.command.option.Option;
import io.github.readonly.command.option.RequiredOption;
import io.github.readonly.common.util.ResultLevel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.tscg.api.edsm.EDSM;
import space.tscg.api.edsm.api.LastKnownPosition;
import space.tscg.api.edsm.api.systems.SystemInformation;
import space.tscg.api.edsm.api.systems.modal.Information;
import space.tscg.bot.util.Reply;
import space.tscg.util.text.TextBuilder;

public class LocateCommand extends SlashCommand
{
    private String LOCATOR_CHANNEL = "839655435967135755";

    public LocateCommand()
    {
        super("locate", "Locates a commander with the provided name");
        options(RequiredOption.text("cmdr", "Cmdr name"), Option.trueFalse("show-system-info", "Include system info"),
                        Option.trueFalse("private", "Only the user that issued command can see the reply"));
    }

    @Override
    protected void execute(SlashCommandEvent event)
    {
        if (!event.getChannel().getId().equals("1117789491621527593")) {
            Reply.EphemeralReply(event, ResultLevel.ERROR, "Command must be used in " + event.getJDA().getGuildChannelById(LOCATOR_CHANNEL).getJumpUrl());
            return;
        }

        if (event.hasOption("private") && event.getOption("private").getAsBoolean()) {
            event.getInteraction().deferReply(true).queue();
        } else {
            event.getInteraction().deferReply(false).queue();
        }

        final Optional<LastKnownPosition> position = EDSM.getPosition(event.getOption("cmdr").getAsString(), true, true);
        if (position.isEmpty()) {
            Reply.Error(event, "Error during EDSM query");
            return;
        }

        final var actualPostition = position.get();

        if (actualPostition.getMsgNum() == 203) {
            Reply.Error(event, "**" + event.getOption("cmdr").getAsString() + "** could not be found in EDSM database");
            return;
        }

        Optional<SystemInformation> systemInfo = null;
        if (event.hasOption("show-system-info")) {
            if (event.getOption("show-system-info").getAsBoolean()) {
                systemInfo = EDSM.getSystemInformation(actualPostition.getSystem());
            }
        }

        List<MessageEmbed> embeds = new ArrayList<>();

        //!fr
        embeds.add(new EmbedBuilder()
                        .setTitle("Located " + actualPostition.getUrl().split("/")[actualPostition.getUrl().split("/").length - 1])
                        .addField("System", actualPostition.getSystem(), false)
                        .addField("EDSM Link", actualPostition.getSystemEdsmUrl(), false)
                        .setColor(Color.GREEN).build());
        
        if (systemInfo != null && systemInfo.isPresent()) {

            embeds.add(new EmbedBuilder()
                            .setTitle("System Information")
                            .setDescription(this.buildSystemInfo(systemInfo.get()))
                            .setColor(Color.GREEN).build());
        }
        //@fr
        event.getInteraction().getHook().sendMessageEmbeds(embeds).queue();
    }

    private String buildSystemInfo(SystemInformation information)
    {
        TextBuilder string = new TextBuilder();

        if (information.isPermitLocked()) {
            string.nextLine("- **Permit Locked System**");
            string.nextLine(" - requires `*%s*` permit".formatted(information.getPermitName()));
            string.ln();
        }

        string.nextLine("- %s".formatted(information.getPrimaryStar().getType()));
        if (information.getPrimaryStar().isScoopable()) {
            string.nextLine("- Primary Star is scoopable");
        }

        if (information.hasCoordinates()) {
            double[] coords = information.getCoordinates().getAsArray();
            string.nextLine("- __**Coordinates**__");
            string.nextLine("  - (X) `%s`".formatted(coords[0]));
            string.nextLine("  - (Y) `%s`".formatted(coords[1]));
            string.nextLine("  - (Z) `%s`".formatted(coords[2]));
        }

        if (information.hasExtraSystemInfo()) {
            Information i = information.getInformation();
            string.nextLine("__**Population:**__ `%s`".formatted(i.getFormattedPopulation()));
            string.nextLine("__**Allegiance:**__ `%s`".formatted(i.getAllegiance()));
            string.nextLine("__**Government:**__ `%s`".formatted(i.getGovernment()));
            string.nextLine("__**Faction:**__ `%s`".formatted(i.getFaction()));
            string.nextLine("__**FactionState:**__ `%s`".formatted(i.getFactionState()));
            string.nextLine("__**Security:**__ `%s`".formatted(i.getSecurity()));
            string.nextLine("__**Economy:**__ `%s`".formatted(i.getEconomy()));
        } else {
            string.nextLine("__**Government:**__ `%s`".formatted(information.getInformation().getGovernment()));
            string.nextLine("__**Security:**__ `%s`".formatted(information.getInformation().getSecurity()));
        }

        return string.toString();
    }
}
