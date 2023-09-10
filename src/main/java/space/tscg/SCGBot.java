package space.tscg;

import static net.dv8tion.jda.api.requests.GatewayIntent.DIRECT_MESSAGES;
import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MESSAGES;
import static net.dv8tion.jda.api.requests.GatewayIntent.MESSAGE_CONTENT;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.CLIENT_STATUS;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.EMOJI;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.STICKER;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.VOICE_STATE;

import java.util.Arrays;
import java.util.List;

import io.github.readonly.command.ClientBuilder;
import io.github.readonly.discordbot.DiscordBot;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import space.tscg.bot.commands.DistanceCommand;
import space.tscg.bot.commands.LocateCommand;
import space.tscg.bot.commands.RegisterCarrierCommand;
import space.tscg.bus.BusHandler;
import space.tscg.bus.TSCGBusListener;
import space.tscg.capi.CallbackServer;
import space.tscg.common.dotenv.Dotenv;

public class SCGBot extends DiscordBot
{
    public static final List<String> ALLOWED_CHANNELS = Arrays.asList("1117789491621527593", "839655435967135755");
    
    public static final String DEV_ID = "393847930039173131";
    
    public static SCGBot INSTANCE;
    
    @Getter
    private JDA jda;
    
    public SCGBot()
    {
        SCGBot.INSTANCE = this;
        new CallbackServer();
        ClientBuilder client = this.getClientBuilder();
        client.setOwnerId(DEV_ID);
        client.addGlobalSlashCommands(new DistanceCommand(), new LocateCommand(), new RegisterCarrierCommand());
        client.setActivity(Activity.playing("Elite Dangerous"));
        client.useHelpBuilder(true);

        JDABuilder builder = JDABuilder.createLight(Dotenv.get("DISCORD_TOKEN"));
        builder.enableIntents(GUILD_MESSAGES, MESSAGE_CONTENT, DIRECT_MESSAGES);
        builder.disableCache(EMOJI, STICKER, CLIENT_STATUS, VOICE_STATE, CacheFlag.SCHEDULED_EVENTS);
        builder.setActivity(Activity.playing("Init Stage"));
        builder.addEventListeners(this.getEventWaiter(), this.buildClient(), new JDAEventListener());
        
        this.jda = builder.build();
        
        BusHandler.register(new TSCGBusListener());
    }

    @Override
    public String getId()
    {
        return "scg-bot";
    }

    public static void main(String[] args)
    {
        new SCGBot();
    }
}
