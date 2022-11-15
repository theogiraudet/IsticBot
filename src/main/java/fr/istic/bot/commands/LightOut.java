package fr.istic.bot.commands;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class LightOut extends ListenerAdapter {
    public LightOut(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        event.getHook().sendMessage("Light out ?")
                .addActionRow(
                        Button.primary("LO.yes", "Yes"),
                        Button.danger("LO.no", "No")
                )
                .queue();
    }

    public static void constructBoard(ButtonInteractionEvent event) {
        event.deferReply().queue();
        InteractionHook hook = event.getHook();
        hook.sendMessage("Board constructed");
        MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.setContent("Board constructed");

        Button[] buttons = new Button[5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[j] = Button.primary("LIGHT." + i + "." + j, i + "." + j);
            }
            builder.addActionRow(buttons);
            buttons = new Button[5];
        }
        hook.sendMessage(builder.build()).queue();
    }
}
