package fr.istic.bot.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import fr.istic.bot.Main;
import fr.theogiraudet.json_command_parser.api.Command;

public class StopCommand extends Command {
    public void stop(MessageCreateEvent event)
    {
        event
                .getMessage()
                .getChannel()
                .flatMap(messageChannel -> messageChannel.createMessage("Extinction du bot."))
                .subscribe();

        Main.getClient().logout().subscribe();
    }

    @Override public String defineSyntaxFile() { return "commands/Stop.jc"; }
}
