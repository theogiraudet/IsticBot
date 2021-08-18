package fr.istic.bot;

import discord4j.core.event.domain.message.MessageCreateEvent;
import fr.theogiraudet.json_command_parser.api.CommandExecutor;
import fr.theogiraudet.json_command_parser.api.CommandParser;
import fr.theogiraudet.json_command_parser.api.Configuration;
import fr.theogiraudet.json_command_parser.core.exception.ExecutionResult;
import fr.theogiraudet.json_command_parser.core.exception.Failure;
import reactor.core.publisher.Mono;

public class CommandInitiator {

    public static void initialize() {
        // Definition of the command prefix
        Configuration.setPrefix(",");
        Configuration.setDebug(true);

        // Definition of the package where commands classes are
        CommandParser.parseCommands("fr.istic.bot.command");

        final CommandExecutor executor = new CommandExecutor();

        // Listening to commands and then executing them
        final ExecutionResult result = Main.getClient().getEventDispatcher()
                .on(MessageCreateEvent.class)
                .flatMap
                        (
                                event -> Mono
                                        .justOrEmpty(event.getMessage().getContent())
                                        .filter(msg -> msg.startsWith(Configuration.getPrefix()))
                                        .flatMap
                                                (
                                                        msg -> Mono.justOrEmpty(executor.execute
                                                                (
                                                                        msg.substring(Configuration.getPrefix().length()),
                                                                        event
                                                                ))
                                                )
                        )
                .blockFirst();
        if(result instanceof Failure) {
            final Failure failure = (Failure)result;
            System.out.println(failure.cause().toString());
            System.out.println(failure.location());
        }
    }

}
