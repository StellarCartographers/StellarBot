package space.tscg.internal.template.owner;

import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.internal.MessageButton;
import space.tscg.internal.template.MessageTemplate;

public class UpdateEmbed implements MessageTemplate
{
    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        builder.addActionRow(MessageButton.REFRESH.getButton());
        return builder.build();
    }
}
