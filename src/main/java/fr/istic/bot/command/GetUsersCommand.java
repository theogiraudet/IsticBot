package fr.istic.bot.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import fr.theogiraudet.json_command_parser.api.Command;
import reactor.core.publisher.Flux;

import java.util.List;

public class GetUsersCommand extends Command {

    public Flux<Member> getAll(MessageCreateEvent event) {
       return event.getGuild()
                   .flatMapMany(Guild::getMembers);
                   //.map(member -> member.getId().asString())
                   //.buffer()
                   //.blockFirst();
    }

    @Override
    public String defineSyntaxFile() {
        return "commands/GetUsers.jc";
    }
}
