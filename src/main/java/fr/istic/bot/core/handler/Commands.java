package fr.istic.bot.core.handler;

import fr.istic.bot.commands.LightOut;
import fr.istic.bot.commands.Say;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class Commands {
    private final HashMap<String, Class<? extends ListenerAdapter>> commands;

    public Commands() {
        this.commands = new HashMap<>() {
            {
                put("say", Say.class);
                put("lightout", LightOut.class);
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
