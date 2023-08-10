package space.tscg.capi;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import space.tscg.BotLog;
import space.tscg.common.dotenv.Dotenv;

@UtilityClass
public final class Constants
{
    public final static String CLIENT_ID_VARIABLE = Dotenv.get("client_id");
    
    public final static ClientID CLIENT_ID = new ClientID(Constants.CLIENT_ID_VARIABLE);
    
    public final static String CALLBACK_URL_STRING = "https://auth.tscg.network/oauth/callback";

    public final static String LIVE_SERVER   = "https://companion.orerve.net";
    public final static String LEGACY_SERVER = "https://legacy-companion.orerve.net/";
    public final static String AUTH_SERVER   = "https://auth.frontierstore.net";

    public final static Scope  SCOPE     = new Scope("capi", "auth");
    public final static String AUTH_URL  = "/auth";
    public final static String TOKEN_URL = "/token";

    public final static String PROFILE_URL      = "/profile";
    public final static String JOURNAL_URL      = "/journal";
    public final static String FLEETCARRIER_URL = "/fleetcarrier";
    
    public final static URI AUTH_URI = URI.create(AUTH_SERVER + AUTH_URL);
    public final static URI TOKEN_URI = URI.create(AUTH_SERVER + TOKEN_URL);
    public final static URI CALLBACK_URI = URI.create(CALLBACK_URL_STRING);
    
    public static List<String> AUTH_MSG_ID_LIST = new ArrayList<>();
    
    static class MessageChannelReference {
        private String channelID;
        private String messageID;
        
        public void delete(JDA jda)
        {
            jda.getTextChannelById(this.channelID).deleteMessageById(this.messageID).queue(
                success -> BotLog.info("Sucessfully deleted Frontier Login message with ID: " + this.messageID), null);
        }
    }
}
