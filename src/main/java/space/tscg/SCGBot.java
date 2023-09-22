package space.tscg;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.*;

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
import space.tscg.common.dotenv.Dotenv;
import space.tscg.internal.Server;

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
        new Server();
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
