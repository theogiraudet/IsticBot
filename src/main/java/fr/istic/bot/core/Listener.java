package fr.istic.bot.core;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class Listener extends ListenerAdapter {
    private final CommandHandler commandHandler;

    public Listener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandHandler.getCommand(event);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds())
            guild.upsertCommand("say", "Say something")
                    .addOption(OptionType.STRING, "text", "The text to say", true)
                    .queue();
    }
}

