package fr.istic.bot.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import fr.theogiraudet.json_command_parser.api.Command;

import java.util.List;

public class SayCommand extends Command {
    public void says(MessageCreateEvent event, List<String> text)
    {
        event.getMessage()
             .getChannel()
             .flatMap(message -> message.createMessage(String.join(" ", text)))
             .subscribe();
    }

    @Override public String defineSyntaxFile() { return "commands/Say.jc"; }
}
