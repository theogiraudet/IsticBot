package fr.istic.bot.channels_updater;

import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.Category;
import reactor.util.annotation.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Représente l'ajout d'un salon textuel au Discord
 */
public class AddedTextChannel implements TextChannelModifier {

    private final Guild guild;
    private final String category;
    private final String name;
    private final String description;
    private final Set<PermissionOverwrite> permissions;

    /**
     * @param guild la guilde concernée par le modificateur
     * @param category la catégorie dans laquelle ajouter le salon
     * @param name le nom du salon
     * @param description la description du salon
     * @param permissions l'ensemble des permissions à appliquer au salon
     */
    public AddedTextChannel(Guild guild, String category, String name, @Nullable String description, Set<PermissionOverwrite> permissions) {
        this.guild = Objects.requireNonNull(guild);
        this.category = Objects.requireNonNull(category);
        this.name = Objects.requireNonNull(name);
        this.description = description == null ? "" : description;
        this.permissions = Objects.requireNonNull(permissions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        final var cat = guild.getChannels().filter(c -> c.getName().equals(category)).filter(Category.class::isInstance).blockFirst();
        Objects.requireNonNull(cat);
        guild.createTextChannel(tcc -> tcc.setParentId(cat.getId()).setPermissionOverwrites(permissions).setTopic(description).setName(name)).subscribe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> plan() {
        return List.of(name);
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
        return name;
    }
}
