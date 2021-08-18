package fr.istic.bot.channels_updater;

import discord4j.core.object.entity.Guild;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Représente les modifications à apporter à un serveur Discord
 */
public class DiscordModifier implements ChannelModifier {

    private final List<ChannelModifier> modifiers;
    private final Guild guild;

    /**
     * @param guild la guilde concernée par le modificateur
     * @param modifiers l'ensemble des modificateurs du serveur
     */
    public DiscordModifier(Guild guild, List<ChannelModifier> modifiers) {
        this.modifiers = Objects.requireNonNull(modifiers);
        this.guild = Objects.requireNonNull(guild);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        modifiers.forEach(ChannelModifier::apply);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> plan() {
        final var formatted = new LinkedList<String>();
        formatted.add(guild.getName());

        for(int i = 0; i < modifiers.size(); i++) {
            final var list = modifiers.get(i).plan();
            if(i < modifiers.size() - 1) {
                formatted.add("├─ " + list.get(0));
                formatted.addAll(list.subList(1, list.size()).stream().map(s -> "│  " + s).collect(Collectors.toList()));
            } else {
                formatted.add("└─ " + list.get(0));
                formatted.addAll(list.subList(1, list.size()).stream().map(s -> "   " + s).collect(Collectors.toList()));
            }
        }

        return formatted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Character> getPrefix() {
        final var list = new LinkedList<Character>();
        list.add(' ');
        list.addAll(modifiers.stream().map(ChannelModifier::getPrefix).flatMap(List::stream).collect(Collectors.toList()));
        return list;
    }
}
