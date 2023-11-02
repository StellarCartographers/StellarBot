package space.tscg.internal.template;

import io.github.readonly.common.util.RGB;
import net.dv8tion.jda.api.entities.MessageEmbed;
import space.tscg.util.text.Ansi;
import space.tscg.util.text.Ansi.Color;
import space.tscg.util.text.Embed;

public interface HidableMessageTemplate extends MessageTemplate
{
    static MessageEmbed getHiddenMessageEmbed()
    {
        return Embed.newBuilder().description(Ansi.newBlock(l -> l.add(Color.RED, "Not Available")).toString()).color(RGB.RED).toEmbed();
    }
}
