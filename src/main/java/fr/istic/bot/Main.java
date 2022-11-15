package fr.istic.bot;

import fr.istic.bot.core.handler.Commands;
import fr.istic.bot.core.Listener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MESSAGES;
import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES;

public class Main {

    public static void main(String... args) throws InterruptedException {
        Dotenv dotenv = Dotenv.configure().load();
        String token = dotenv.get("TOKEN");

        JDA api = JDABuilder.create((token), GUILD_MESSAGES, GUILD_VOICE_STATES)
                .disableCache(
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOJI,
                        CacheFlag.STICKER,
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ONLINE_STATUS,
                        CacheFlag.SCHEDULED_EVENTS
                )
                .addEventListeners(new Listener(new Commands()))
                .setActivity(Activity.playing("!help"))
                .build();
        api.awaitReady();

    }
}
