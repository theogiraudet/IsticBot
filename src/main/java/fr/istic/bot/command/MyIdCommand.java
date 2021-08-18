package fr.istic.bot.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import fr.theogiraudet.json_command_parser.api.Command;

public class MyIdCommand extends Command {

    public void getMyId(MessageCreateEvent e) {
        e.getMessage().getChannel().flatMap(c -> c.createMessage(e.getMember().get().getId().asString())).block();
    }

    @Override
    public String defineSyntaxFile() {
        return "commands/MyId.jc";
    }
}
