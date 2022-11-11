package fr.istic.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class Say extends ListenerAdapter {
    public Say(SlashCommandInteractionEvent event) {
        event.reply(Objects.requireNonNull(event.getOption("text")).getAsString()).queue();
    }
}
