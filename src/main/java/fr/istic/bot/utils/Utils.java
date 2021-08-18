package fr.istic.bot.utils;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import fr.istic.bot.Main;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {

    public static Optional<Member> toMember(Guild guild, String str) {
        if(str.matches("\\d+"))
            return Optional.ofNullable(guild.getMemberById(Snowflake.of(str)).blockOptional()
                    .orElse(guild.getMembers()
                            .filter(m -> (m.getNickname().isPresent() && m.getNickname().get().equals(str)) || m.getNicknameMention().equals(str) || m.getDisplayName().equals(str))
                            .blockFirst()));
        return Optional.ofNullable(guild.getMembers()
                .filter(m -> (m.getNickname().isPresent() && m.getNickname().get().equals(str)) || m.getNicknameMention().equals(str) || m.getDisplayName().equals(str))
                .blockFirst());
    }

    public static Optional<Role> toRole(Guild guild, String str) {
        if(str.matches("\\d+"))
            return Optional.ofNullable(guild.getRoleById(Snowflake.of(str)).blockOptional()
                    .orElse(guild.getRoles()
                            .filter(r -> r.getName().equals(str) || r.getMention().equals(str))
                            .blockFirst()));
        return Optional.ofNullable(guild.getRoles()
                        .filter(r -> r.getName().equals(str) || r.getMention().equals(str))
                        .blockFirst());
    }

}
