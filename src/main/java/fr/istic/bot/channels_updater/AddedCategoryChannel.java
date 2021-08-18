package fr.istic.bot.channels_updater;

import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Représente l'ajout d'une catégorie au serveur
 */
public class AddedCategoryChannel implements ChannelModifier {

    private final Guild guild;
    private final String name;
    private final Set<PermissionOverwrite> permissions;
    private final List<TextChannelModifier> channelModifiers;

    /**
     * @param guild            la guide concernée par le modificateur
     * @param name             le nom de la catégorie à ajouter
     * @param permissions      l'ensemble des permissions à appliquer sur la catégorie
     * @param channelModifiers la liste des permissions à donner à la catégorie
     */
    public AddedCategoryChannel(Guild guild, String name, Set<PermissionOverwrite> permissions, List<TextChannelModifier> channelModifiers) {
        this.guild = Objects.requireNonNull(guild);
        this.name = Objects.requireNonNull(name);
        this.permissions = Objects.requireNonNull(permissions);
        this.channelModifiers = Objects.requireNonNull(channelModifiers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        guild.createTextChannel(tcc -> tcc.setPermissionOverwrites(permissions).setName(name)).subscribe();
        channelModifiers.forEach(TextChannelModifier::apply);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> plan() {
        final var formatted = new LinkedList<String>();
        formatted.add(name);

        for (int i = 0; i < channelModifiers.size() - 1; i++)
            formatted.add("├─ " + channelModifiers.get(i).plan().get(0));
        formatted.add("└─ " + channelModifiers.get(channelModifiers.size() - 1).plan().get(0));

        return formatted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Character> getPrefix() {
        final var list = new LinkedList<Character>();
        list.add('+');
        list.addAll(channelModifiers.stream().map(ChannelModifier::getPrefix).flatMap(List::stream).collect(Collectors.toList()));
        return list;
    }
}
