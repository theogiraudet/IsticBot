package fr.istic.bot.channels_updater;

import discord4j.core.object.entity.channel.GuildChannel;

import java.util.List;
import java.util.Objects;

/**
 * Représente un salon texuel à ignorer pour les modifications
 */
public class IgnoredTextChannel implements TextChannelModifier {

    private final GuildChannel channel;

    /**
     * @param channel le salon textuel à ignorer
     */
    public IgnoredTextChannel(GuildChannel channel) {
        this.channel = Objects.requireNonNull(channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        //Ignore this channel
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
        return List.of('~');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return channel.getName();
    }
}
