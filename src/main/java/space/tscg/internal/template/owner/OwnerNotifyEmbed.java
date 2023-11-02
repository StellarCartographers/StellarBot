package space.tscg.internal.template.owner;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.internal.template.MessageTemplate;
import space.tscg.util.text.Embed;

@AllArgsConstructor
public class OwnerNotifyEmbed implements MessageTemplate
{
    private UserSnowflake snowflake;
    
    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        if(this.snowflake != null)
        {
            builder.addContent(snowflake.getAsMention());
        }
        builder.addEmbeds(Embed.newBuilder()
                        .description("""
                                        Carrier ownership has been verified and your Fleet Carrier is now registered!
                                        This is your personal carrier management channel. Here you will see several predefined messages that will aid in management of your Fleet Carrier in the discord server. 
                                        
                                        Below is a breakdown of what you can expect""")
                        .field("Refresh Carrier Data", """
                                        You can manually trigger a carrier info update by clicking the 'Update' button. This will force the API Server to make a new call to Frontier's CAPI endpoint. This can only be done every 5 minutes. Also be aware that changes to your carrier in game can take up to 20 mintues to refelct in the CAPI data returned to us""")
                        .field("Carrier Data Display Options", """
                                        You can choose to not display the **Buy/Sell Orders**, **Outfitting Stock**, and **Shipyard Stock** Embeds
                                        
                                        If you choose to not display these, the Embed will instead read 'Data Not Available'. The buttons on this Embed serve as a Hide/Display toggle. By default each embed message is set to Display""")
                        .field("Most Importantly!", """
                                        This will also serve as the channel where the bot will Ping you directly if Frontier auth is required again. The API server will attempt to refresh the access_token every 6 hours so a ping is not needed, However refresh_tokens can randomly become invalid or can expire due to backend issues on the API Server. Rendering refreshing the access_token not possible.""").toEmbed());
        return builder.build();
    }
}
