package fr.istic.bot.core;

import fr.istic.bot.commands.LightOut;
import fr.istic.bot.core.handler.Commands;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Listener extends ListenerAdapter {
    private final Commands commandHandler;

    public Listener(Commands commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandHandler.getCommand(event);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            guild.upsertCommand("say", "Say something")
                    .addOption(OptionType.STRING, "text", "The text to say", true)
                    .queue();
            guild.upsertCommand("lightout", "Light out")
                    .queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] args = event.getComponentId().split("\\.");
        if(args[0].equalsIgnoreCase("lo")) {
            if (event.getComponentId().equals("LO.yes")) {
                LightOut.constructBoard(event);
            } else if (event.getComponentId().equals("LO.no")) {
                event.deferReply().queue();
                event.getHook().sendMessage("Sadge ... see you soon !").queue();
            }
        } else if(args[0].equalsIgnoreCase("light")) {
            event.editButton(
                    Button.secondary(event.getComponentId(), Emoji.fromUnicode("\uD83D\uDEAB"))
            ).queue();

        }
    }
}

