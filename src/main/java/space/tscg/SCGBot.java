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
import java.util.Optional;

import io.github.readonly.command.ClientBuilder;
import io.github.readonly.discordbot.DiscordBot;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import space.tscg.bot.commands.DistanceCommand;
import space.tscg.bot.commands.LocateCommand;
import space.tscg.bot.commands.RegisterCarrierCommand;
import space.tscg.capi.CallbackServer;
import space.tscg.common.dotenv.Dotenv;
import space.tscg.events.EventListener;

public class SCGBot extends DiscordBot<SCGBot>
{
    public static final List<String> ALLOWED_CHANNELS = Arrays.asList("1117789491621527593", "839655435967135755");
    
    public static final String DEV_ID = "393847930039173131";
    
    public static SCGBot INSTANCE;
    
    public SCGBot()
    {
        SCGBot.INSTANCE = this;
        new CallbackServer();
        ClientBuilder client = this.getClientBuilder();
        client.setOwnerId(DEV_ID);
        client.addGlobalSlashCommands(new DistanceCommand(), new LocateCommand(), new RegisterCarrierCommand());
        client.setActivity(Activity.playing("Elite Dangerous"));
        client.useHelpBuilder(true);

        JDABuilder jda = JDABuilder.createLight(Dotenv.get("DISCORD_TOKEN"));
        jda.enableIntents(GUILD_MESSAGES, MESSAGE_CONTENT, DIRECT_MESSAGES);
        jda.disableCache(EMOJI, STICKER, CLIENT_STATUS, VOICE_STATE, CacheFlag.SCHEDULED_EVENTS);
        jda.setActivity(Activity.playing("Init Stage"));
        jda.addEventListeners(this.getEventWaiter(), this.buildClient(), new EventListener()).build();
    }

    @Override
    public String getId()
    {
        return "scg-bot";
    }

    @Override
    public Optional<?> getInstance()
    {
        return Optional.empty();
    }
    
    public static void main(String[] args)
    {
        new SCGBot();
    }
}
