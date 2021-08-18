package fr.istic.bot.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import fr.istic.bot.channels_updater.ChannelUpdater;
import fr.theogiraudet.json_command_parser.api.Command;

import java.io.IOException;

/**
 * Commande pour appliquer des modifications au Discord selon le fichier <code>Categories.json</code> les représentant
 */
public class DiscordUpdateCommand extends Command {

    private ChannelUpdater updater;

    /**
     * Planifie les modifications qui seront apportées au Discord par la commande <code>discord_update apply</code>
     * @param event l'event de création du message à l'origine de la commande
     */
    public void plan(MessageCreateEvent event) {
        event.getGuild().subscribe(this::createUpdater);

        var str = updater.plan();
        event.getMessage().getChannel().flatMap(c -> c.createMessage("```diff\n" + str + "\n```")).subscribe();
    }

    /**
     * Applique les modifications au Discord
     * @param event l'event de création du message à l'origine de la commande
     */
    public void apply(MessageCreateEvent event) {
        event.getGuild().subscribe(this::createUpdater);

        // updater.apply();
    }

    private void createUpdater(Guild guild) {
        if(updater == null) {
            try {
                updater = new ChannelUpdater(guild);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String defineSyntaxFile() {
        return "commands/DiscordUpdate.jc";
    }
}
