package fr.istic.bot.channels_updater;

import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.channel.Category;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Représente une catégorie à modifier
 */
public class ModifiedCategoryChannel implements ChannelModifier {

    private final Category channel;
    private final String name;
    private final List<TextChannelModifier> channels;
    private final Set<PermissionOverwrite> permissions;

    /**
     * @param channel la catégorie concernée par les modifications
     * @param name le nom de la catégorie
     * @param channels les modificateurs des salons de la catégorie
     * @param permissions les permissions à appliquer à la catégorie
     */
    public ModifiedCategoryChannel(Category channel, String name, List<TextChannelModifier> channels, Set<PermissionOverwrite> permissions) {
        this.channel = Objects.requireNonNull(channel);
        this.name = name;
        this.channels = Objects.requireNonNull(channels);
        this.permissions = Objects.requireNonNull(permissions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        channel.edit(tces -> tces.setName(name).setPermissionOverwrites(permissions)).subscribe();
        channels.forEach(TextChannelModifier::apply);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> plan() {
        final var formatted = new LinkedList<String>();

        var str = channel.getName() + " ➜ ";
        if(name != null)
            str += "renamed ";
        if(!permissions.isEmpty())
            str += "permissions modified";

        formatted.add(str);
        for(int i = 0; i < channels.size() - 1; i++)
            formatted.add("├─ " + channels.get(i).plan().get(0));
        formatted.add("└─ " + channels.get(channels.size() - 1).plan().get(0));

        return formatted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Character> getPrefix() {
        final var list = new LinkedList<Character>();
        list.add('+');
        list.addAll(channels.stream().map(ChannelModifier::getPrefix).flatMap(List::stream).collect(Collectors.toList()));
        return list;
    }
}
