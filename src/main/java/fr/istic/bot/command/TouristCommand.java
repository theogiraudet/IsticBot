package fr.istic.bot.command;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import fr.theogiraudet.json_command_parser.api.Command;

import java.util.Set;

public class TouristCommand extends Command {

    public void count(MessageCreateEvent event) {
        System.out.println("test");
        event.getGuild().flatMapMany(Guild::getMembers)
                .filter(m -> !m.isBot())
                .filter(m -> Set.of(Snowflake.of(617673325539229697L), Snowflake.of(782304578053341254L)).containsAll(m.getRoleIds()))
                .count()
                .flatMap(l -> event.getMessage().getChannel().flatMap(c -> c.createMessage(l + " touristes.")))
                .subscribe();
    }

    @Override public String defineSyntaxFile() { return "commands/Tourist.jc"; }
}
