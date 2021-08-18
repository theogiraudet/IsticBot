package fr.istic.bot.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import fr.istic.bot.utils.Utils;
import fr.theogiraudet.json_command_parser.api.Command;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RoleCommand extends Command {

    public void addRole(MessageCreateEvent event, String role, List<String> list) {
        final Optional<Role> roleOpt = checkRole(event, role);

        roleOpt.ifPresent(value -> Flux.fromIterable(list)
                .map(str -> Utils.toMember(Objects.requireNonNull(event.getGuild().block()), str))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .subscribe(m -> m.addRole(value.getId()).block()));
    }

    public void addRole(MessageCreateEvent event, String role, Flux<Member> users) {
        final Optional<Role> roleOpt = checkRole(event, role);
        roleOpt.ifPresent(value -> users.subscribe(m -> m.addRole(value.getId()).block()));
    }

    public void removeRole(MessageCreateEvent event, String role, Flux<Member> users) {
        final Optional<Role> roleOpt = checkRole(event, role);
        roleOpt.ifPresent(value -> users.subscribe(m -> m.removeRole(value.getId()).block()));
    }

    public void removeRole(MessageCreateEvent event, String role, List<String> list) {
        final Optional<Role> roleOpt = checkRole(event, role);

        roleOpt.ifPresent(value -> Flux.fromIterable(list)
                .map(str -> Utils.toMember(Objects.requireNonNull(event.getGuild().block()), str))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .subscribe(m -> m.removeRole(value.getId()).block()));
    }

    private Optional<Role> checkRole(MessageCreateEvent event, String role) {
        final Optional<Role> roleOpt = Utils.toRole(event.getGuild().block(), role);
        if(roleOpt.isEmpty())
            event.getMessage().getChannel().flatMap(c -> c.createMessage("Le role '" + role + "' n'existe pas.")).block();
        return roleOpt;
    }

    @Override
    public String defineSyntaxFile() {
        return "commands/Role.jc";
    }
}
