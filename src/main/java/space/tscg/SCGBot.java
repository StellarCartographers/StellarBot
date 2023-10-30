package space.tscg;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.SCHEDULED_EVENTS;

import java.util.EnumSet;

import com.google.common.eventbus.EventBus;

import io.github.readonly.command.ClientBuilder;
import io.github.readonly.command.ContextMenu;
import io.github.readonly.command.SlashCommand;
import io.github.readonly.common.ServerCommands;
import io.github.readonly.common.ToolSet;
import io.github.readonly.common.waiter.EventWaiter;
import io.github.readonly.discordbot.DiscordBot;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import space.tscg.bot.RegisterListener;
import space.tscg.bot.commands.DistanceCommand;
import space.tscg.bot.commands.LocateCommand;
import space.tscg.bot.commands.NearestPOICommand;
import space.tscg.bot.commands.ShutdownCommand;
import space.tscg.internal.server.Server;
import space.tscg.properties.dot.Dotenv;
import space.tscg.web.domain.DefaultDomain;

public class SCGBot extends DiscordBot
{
    public static final String DEV_ID = "393847930039173131";
    
    public static SCGBot INSTANCE;
    
    @Getter
    private EventWaiter waiter;
    @Getter
    private JDA jda;
    @Getter
    private final EventBus EVENT_BUS = new EventBus();
    
    public SCGBot()
    {
        SCGBot.INSTANCE = this;
        if(Dotenv.get("db_host").equals("localhost"))
        {
            DefaultDomain.setDefault("localhost:9050");
        }
        new Server();
 
        this.waiter = new EventWaiter();
        this.jda = buildJDA();
    }

    public static void main(String[] args)
    {
        new SCGBot();
    }

    @Override
    protected void addToBuilder(ClientBuilder builder)
    {
    }

    @Override
    protected String getOwnerId()
    {
        return DEV_ID;
    }

    @Override
    protected Activity getActivity()
    {
        return Activity.playing("Elite Dangerous");
    }

    @Override
    protected String getToken()
    {
        return Dotenv.get("DISCORD_TOKEN");
    }

    @Override
    protected EnumSet<GatewayIntent> getGatewayIntents()
    {
        return EnumSet.of(GUILD_MESSAGES, MESSAGE_CONTENT, DIRECT_MESSAGES);
    }

    @Override
    protected EnumSet<CacheFlag> getDisabledCacheFlags()
    {
        return EnumSet.of(EMOJI, STICKER, CLIENT_STATUS, VOICE_STATE, SCHEDULED_EVENTS, ACTIVITY, ONLINE_STATUS);
    }

    @Override
    protected void addSlashCommands(ToolSet<SlashCommand> set)
    {
        set.addMultiple(new DistanceCommand(), new LocateCommand(), new NearestPOICommand(), new ShutdownCommand());
    }

    @Override
    protected void addContextMenus(ToolSet<ContextMenu<?>> set)
    {
    }

    @Override
    protected void addServerCommands(ToolSet<ServerCommands> set)
    {
    }

    @Override
    protected void addEventListeners(ToolSet<Object> set)
    {
        set.addMultiple(this.waiter ,new JDAEventListener(), new RegisterListener());
    }
}
