package space.tscg.internal.template;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.util.text.Embed;
import space.tscg.util.text.TimeFormat;

public interface MessageTemplate
{
    public MessageCreateData getMessageData();
    
    default MessageEmbed appendLastUpdated()
    {
        return Embed.newBuilder().field("Last Updated", TimeFormat.Default() + " (" + TimeFormat.RelativeTime() + ")").toEmbed();
    }
}
