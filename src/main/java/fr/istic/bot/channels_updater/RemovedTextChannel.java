package fr.istic.bot.channels_updater;

import discord4j.core.object.entity.channel.GuildChannel;

import java.util.List;
import java.util.Objects;

/**
 * Représente la suppression d'un salon
 */
public class RemovedTextChannel implements TextChannelModifier {

    private final GuildChannel channel;

    /**
     * @param channel le salon à supprimer
     */
    public RemovedTextChannel(GuildChannel channel) {
        this.channel = Objects.requireNonNull(channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        channel.delete().subscribe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> plan() {
        return List.of(channel.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Character> getPrefix() {
        return List.of('-');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return channel.getName();
    }
}
