package space.tscg.internal.template.publik;

import static space.tscg.util.text.Ansi.Color.*;
import static space.tscg.util.text.Ansi.Style.*;

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
import space.tscg.util.text.Embed;

public class LocationEmbed implements MessageTemplate
{
    private ISystem info;

    public LocationEmbed(IFleetCarrier fleetCarrier)
    {
        this(fleetCarrier.getSystem());
    }
    
    public LocationEmbed(ISystem info)
    {
        this.info = info;
    }
    
    public LocationEmbed(String system)
    {
        var sys = EDSM.getSystemInformation(system);
        this.info = sys.isPresent() ? sys.get() : new BasicSystemInformation(system);
    }

    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        builder.addContent("# Carrier Location");
        Embed embed = Embed.newBuilder();
        if(info.isBasic()) {
            embed.field("System", "> " + info.getName());
            embed.color(RGB.YELLOW);
        } else {
            var sys = (SystemInformation) info;
            embed.field("System", "> [%s](%s)".formatted(sys.getName(), sys.getSystemEdsmUrl()));
            embed.field("Extra Information", this.locationBlock(sys));
            embed.color(RGB.YELLOW);
        }
        builder.addEmbeds(embed.toEmbed());
        return builder.build();
    }

    private String locationBlock(SystemInformation info) {
        var sysInfo = info.getInformation();
        return Ansi.newBlock(
            l -> l.add(UNDERLINE, WHITE, "Allegiance").add(WHITE, ":").space(3).add(YELLOW, sysInfo.getAllegiance()),
            l -> l.add(UNDERLINE, WHITE, "Faction").add(WHITE, ":").space(6).add(YELLOW, sysInfo.getFaction()),
            l -> securityLine(l, info),
            l -> l.add(UNDERLINE, WHITE, "State").add(WHITE, ":").space(8).add(YELLOW, sysInfo.getFactionState()),
            l -> l.add(UNDERLINE, WHITE, "Population").add(WHITE, ":").space(3).add(YELLOW, sysInfo.getSmallFormatPopulation())
        ).toString();
    }

    private void securityLine(Ansi.Line l, SystemInformation info)
    {
        var s = l.add(UNDERLINE, WHITE, "Security").add(WHITE, ":").space(5);

        switch (info.getInformation().getSecurity()) {
            case "High" -> s.add(CYAN, "High");
            case "Medium" -> s.add(BLUE, "Medium");
            case "Low" -> s.add(GREEN, "Low");
            default -> s.add(RED, "Anarchy");
        }
    }
}