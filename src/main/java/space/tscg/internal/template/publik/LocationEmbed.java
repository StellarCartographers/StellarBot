package space.tscg.internal.template.publik;

import static space.tscg.util.text.Ansi.Color.*;
import static space.tscg.util.text.Ansi.Style.*;

import java.util.function.Consumer;

import io.github.readonly.common.util.RGB;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.api.edsm.EDSM;
import space.tscg.api.edsm.api.systems.BasicSystemInformation;
import space.tscg.api.edsm.api.systems.ISystem;
import space.tscg.api.edsm.api.systems.SystemInformation;
import space.tscg.internal.template.MessageTemplate;
import space.tscg.util.text.Ansi;
import space.tscg.util.text.Ansi.Line;
import space.tscg.util.text.Embed;

public class LocationEmbed implements MessageTemplate
{
    private final String         HEADER     = "# Carrier Location";

    private final Consumer<Line> allegiance = l -> l.add(UNDERLINE, WHITE, "Allegiance").add(WHITE, ":").space(3).add(YELLOW, "%s");
    private final Consumer<Line> faction    = l -> l.add(UNDERLINE, WHITE, "Faction").add(WHITE, ":").space(6).add(YELLOW, "%s");
    private final Consumer<Line> state      = l -> l.add(UNDERLINE, WHITE, "State").add(WHITE, ":").space(8).add(YELLOW, "%s");
    private final Consumer<Line> population = l -> l.add(UNDERLINE, WHITE, "Population").add(WHITE, ":").space(3).add(YELLOW, "%s");

    private MessageCreateBuilder msgBuilder;
    private ISystem              info;

    public LocationEmbed(IFleetCarrier fleetCarrier)
    {
        var sys = EDSM.getSystemInformation(fleetCarrier.getSystem());
        this.info = sys.isPresent() ? sys.get() : new BasicSystemInformation(fleetCarrier.getSystem());
        this.msgBuilder = new MessageCreateBuilder();
    }

    @Override
    public MessageCreateData getMessageData()
    {
        return info.isBasic() ? createBasicEmbed() : createInfoEmbed();
    }

    private MessageCreateData createBasicEmbed()
    {
        msgBuilder.addContent(HEADER);
        Embed embed = Embed.newBuilder();
        embed.color(RGB.GREEN);
        embed.field("System", "> " + info.getName());
        return msgBuilder.addEmbeds(embed.toEmbed()).build();
    }

    private MessageCreateData createInfoEmbed()
    {
        var sys = (SystemInformation) info;
        msgBuilder.addContent(HEADER);
        Embed embed = Embed.newBuilder();
        embed.color(RGB.GREEN);
        embed.field("System", "> [%s](%s)".formatted(sys.getName(), sys.getSystemEdsmUrl()));
        
        var info = sys.getInformation();
        
        var block = Ansi.newBlock(
                        allegiance, 
                        faction, 
                        securityLine(),
                        state, 
                        population).formatted(
                                        info.getAllegiance(),
                                        info.getFaction(),
                                        info.getFactionState(),
                                        info.getSmallFormatPopulation());
        
        embed.field("Extra Information", block);
        return msgBuilder.addEmbeds(embed.toEmbed()).build();
    }

    private Consumer<Line> securityLine()
    {
        var sys = (SystemInformation) info;
        switch (sys.getInformation().getSecurity()) {
            case "High": {
                return l -> l.add(UNDERLINE, WHITE, "Security:").space(5).add(CYAN, "High");
            }
            case "Medium": {
                return l -> l.add(UNDERLINE, WHITE, "Security:").space(5).add(BLUE, "Medium");
            }
            case "Low": {
                return l -> l.add(UNDERLINE, WHITE, "Security:").space(5).add(GREEN, "Low");
            }
            default: {
                return l -> l.add(UNDERLINE, WHITE, "Security:").space(5).add(RED, "Anarchy");
            }
        }
    }

}
