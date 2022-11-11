package fr.istic.bot.core;

import fr.istic.bot.commands.Say;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.HashMap;

public class CommandHandler {
    private final HashMap<String, Class<? extends ListenerAdapter>> commands;

    public CommandHandler() {
        this.commands = new HashMap<>() {
            {
                put("say", Say.class);
            }
        };
    }

    public void getCommand(SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        System.out.println(commandName);
        Class<? extends ListenerAdapter> commandClass = commands.get(commandName);
        if (commandClass != null) {
            try {
                commandClass.getConstructor(SlashCommandInteractionEvent.class).newInstance(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
