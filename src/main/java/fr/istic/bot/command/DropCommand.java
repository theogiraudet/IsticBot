package fr.istic.bot.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import fr.istic.bot.utils.Utils;
import fr.theogiraudet.json_command_parser.api.Command;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public class DropCommand extends Command {

    public Flux<Member> dropBot(MessageCreateEvent event, List<String> users) {
        return Flux.fromIterable(users)
                .map(u -> Utils.toMember(event.getGuild().block(), u))
                .map(Optional::get)
                .filter(u -> !u.isBot());
                //.map(u -> u.getId().asString())
                //.buffer()
                //.blockFirst();
    }

    public Flux<Member> dropBot(MessageCreateEvent event, Flux<Member> users) {
        return users.filter(u -> !u.isBot());
    }

    @Override
    public String defineSyntaxFile() {
        return "commands/Drop.jc";
    }
}
