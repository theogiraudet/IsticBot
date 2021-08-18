package fr.istic.bot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;


public class Main {

    private static GatewayDiscordClient client;

    public static void main(String... args) {
        final String token = args[0];
        DiscordClient discordClient = DiscordClient.create(token);
        discordClient.gateway().setEnabledIntents(IntentSet.of(Intent.GUILD_MEMBERS));
        client = discordClient.login().block();
        client.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("!ping".equals(message.getContent())) {
            }
        });

        CommandInitiator.initialize();
        client.onDisconnect().block();
    }

    public static GatewayDiscordClient getClient() {
        return client;
    }
}
