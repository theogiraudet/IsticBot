package fr.istic.bot.channels_updater;

import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.channel.TextChannel;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Représente un salon à modifier
 */
public class ModifiedTextChannel implements TextChannelModifier {

    private final TextChannel channel;
    private final String name;
    private final String description;
    private final Set<PermissionOverwrite> permissions;

    /**
     * @param channel le salon textuel concerné par les modifications
     * @param name le nom du salon
     * @param description la description du salon
     * @param permissions les permissions du salon
     */
    public ModifiedTextChannel(TextChannel channel, String name, String description, Set<PermissionOverwrite> permissions) {
        this.channel = Objects.requireNonNull(channel);
        this.name = name;
        this.description = description;
        this.permissions = Objects.requireNonNull(permissions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        channel.edit(tces -> tces.setName(name).setTopic(description).setPermissionOverwrites(permissions)).subscribe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> plan() {
        var str = channel.getName() + " ➜ modify: ";
        if(name != null)
            str += "name ";
        if(description != null)
            str += "description ";
        if(!permissions.isEmpty())
            str += "permissions ";

        return List.of(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Character> getPrefix() {
        return List.of('+');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return channel.getName();
    }
}
