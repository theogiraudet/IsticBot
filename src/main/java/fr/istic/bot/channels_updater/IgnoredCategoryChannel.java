package fr.istic.bot.channels_updater;

import discord4j.core.object.entity.channel.Category;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Représente une catégorie à ignorer pour les modifications
 */
public class IgnoredCategoryChannel implements ChannelModifier {

    private final Category channel;
    private final List<TextChannelModifier> channelModifiers;

    /**
     * @param channel la catégorie à ignorer
     * @param channelModifiers les modificateurs des salons de cette catégorie
     */
    public IgnoredCategoryChannel(Category channel, List<TextChannelModifier> channelModifiers) {
        this.channel = Objects.requireNonNull(channel);
        this.channelModifiers = Objects.requireNonNull(channelModifiers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        channelModifiers.forEach(TextChannelModifier::apply);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> plan() {
        final var formatted = new LinkedList<String>();
        formatted.add(channel.getName());

        for(int i = 0; i < channelModifiers.size() - 1; i++)
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
        list.add('~');
        list.addAll(channelModifiers.stream().map(ChannelModifier::getPrefix).flatMap(List::stream).collect(Collectors.toList()));
        return list;
    }
}
