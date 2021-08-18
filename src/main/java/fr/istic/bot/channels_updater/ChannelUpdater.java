package fr.istic.bot.channels_updater;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.Category;
import discord4j.core.object.entity.channel.TextChannel;
import fr.istic.bot.json.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe mettant à jour le serveur en fonction du descripteur JSON
 */
public class ChannelUpdater {

    private final List<JsonCategory> categories;
    private final Guild guild;
    private DiscordModifier modifier;

    /**
     * @param guild la guilde concernée par la mise à jour
     * @throws IOException
     */
    public ChannelUpdater(Guild guild) throws IOException {
        this.guild = guild;
        categories = ChannelParser.parse(ChannelParser.class.getClassLoader().getResourceAsStream("Categories.json"));
    }

    /**
     * @return un String résumant toutes les modifications qui seront apportées au serveur
     */
    public String plan() {
        modifier = manageDiscord();

        return Flux.fromIterable(modifier.getPrefix())
                .zipWithIterable(modifier.plan())
                .map(t -> t.getT1() + " " + t.getT2())
                .reduce((s1, s2) -> s1 + "\n" + s2).block();
    }

    /**
     * Applique les modifications au serveur
     */
    public void apply() {
        modifier.apply();
    }

    /**
     * Prépare les modifications à apporter au serveur
     * @return un DiscordModifier
     */
    private DiscordModifier manageDiscord() {
        final var categoriesParsed = categories.stream().map(this::manageCategory).collect(Collectors.toList());
        return new DiscordModifier(guild, categoriesParsed);
    }

    /**
     * Prépare les modifications à apporter à la catégorie
     * @param category les informations du descripteur concernant une catégorie
     * @return un ChannelModifier, modifications à apporter à la catégorie
     */
    private ChannelModifier manageCategory(JsonCategory category) {
        final var oldName = category.getOldName() == null ? category.getName() : category.getOldName();
        final var cat = guild.getChannels()
                .filter(c -> c.getName().equals(oldName))
                .filter(Category.class::isInstance)
                .map(Category.class::cast)
                .blockFirst();

        if(oldName.equals(category.getOldName()) && cat == null)
            throw new IllegalStateException("The channel " + oldName + " does not exist");

        final var isNew = cat == null;
        final var name =
                (category.getOldName() == null && isNew) || (!category.getName().equals(category.getOldName()) && !isNew) ?
            category.getName() : null;

        final var channels = category.getSubCategories().stream().map(sc -> manageSubCategory(sc, name)).flatMap(List::stream).collect(Collectors.toList());
        final var channelsName = channels.stream().map(TextChannelModifier::getName).collect(Collectors.toList());

        if(!isNew)
            cat.getChannels().filter(c -> !channelsName.contains(c.getName())).map(RemovedTextChannel::new).subscribe(channels::add);

        if(isNew)
            return new AddedCategoryChannel(guild, name, Set.of(), channels);
        if(needUpdate(cat, category))
            return new IgnoredCategoryChannel(cat, channels);

        return new ModifiedCategoryChannel(cat, name, channels, Set.of());
    }

    /**
     * Prépare les modifications à apporter à la sous-catégorie
     * @param subCategory les informations du descripteur concernant une sous-catégorie
     * @param category le nom de la catégorie dans laquelle se trouve la sous-catégorie
     * @return une liste de TextChannelModifier, modifications à apporter à la sous-catégorie et aux salons de cette sous-catégorie
     */
    private List<TextChannelModifier> manageSubCategory(JsonSubCategory subCategory, String category) {
        final var oldName = subCategory.getOldName() == null ? subCategory.getName() : subCategory.getOldName();
        final var cat = guild.getChannels()
                .filter(c -> c.getName().equals(oldName))
                .filter(TextChannel.class::isInstance)
                .map(TextChannel.class::cast)
                .blockFirst();

        if(oldName.equals(subCategory.getOldName()) && cat == null)
            throw new IllegalStateException("The channel " + oldName + " does not exist");

        final var isNew = cat == null;
        String name = null;
        if((subCategory.getOldName() == null && isNew) || (!subCategory.getName().equals(subCategory.getOldName()) && !isNew))
            name = subCategory.getName();


        final var list = new LinkedList<TextChannelModifier>();

        if(isNew)
            list.add(new AddedTextChannel(guild, category, name, null, Set.of()));
        else if(needUpdate(cat, subCategory) && !"➽-default".equals(name))
            list.add(new IgnoredTextChannel(cat));
        else if(!"➽-default".equals(name))
            list.add(new ModifiedTextChannel(cat, name, null, Set.of()));

        list.addAll(subCategory.getChannels().stream().map(c -> manageChannel(c, category)).collect(Collectors.toList()));

        return list;
    }

    /**
     * Prépare les modifications à apporter au salon
     * @param channel les informations du descripteur concernant un salon
     * @param category le nom de la catégorie dans laquelle se trouve le salon
     * @return un TextChannelModifier, modifications à apporter au salon
     */
    private TextChannelModifier manageChannel(JsonChannel channel, String category) {
        final var oldName = channel.getOldName() == null ? channel.getName() : channel.getOldName();
        final var chan = guild.getChannels()
                .filter(c -> c.getName().equals(oldName))
                .filter(TextChannel.class::isInstance)
                .map(TextChannel.class::cast)
                .blockFirst();

        if(oldName.equals(channel.getOldName()) && chan == null)
            throw new IllegalStateException("The channel " + oldName + " does not exist");

        final var isNew = chan == null;
        String name = null;
        if((channel.getOldName() == null && isNew) || (channel.getOldName() != null && !channel.getName().equals(channel.getOldName()) && !isNew))
            name = channel.getName().replace(" ", "-");

        if(isNew)
            return new AddedTextChannel(guild, category, name, null, Set.of());
        if(chanNeedsUpdate(chan, channel))
            return new IgnoredTextChannel(chan);

        return new ModifiedTextChannel(chan, name, null, Set.of());
    }

    /**
     * @param channel un salon textuel
     * @param informations les informations du descripteur concernant ce salon
     * @return true si <i>channel</i> doit être mis à jour en fonction de <i>informations</i>, false si <i>channel</i> est déjà à jour
     */
    private boolean chanNeedsUpdate(TextChannel channel, JsonChannel informations) {
        var isEquals = needUpdate(channel, informations);
        isEquals &= (channel.getTopic().orElse("").equals(informations.getDescription())) || (channel.getTopic().isEmpty() && informations.getDescription() == null);
        return isEquals;
    }

    /**
     * @param channel un salon textuel
     * @param informations les informations basiques du descripteur concernant ce salon
     * @return true si <i>channel</i> doit être mis à jour en fonction de <i>informations</i>, false si <i>channel</i> est déjà à jour
     */
    private boolean needUpdate(TextChannel channel, BasicChannel informations) {
        var isEquals = true;
        isEquals &= channel.getName().equals(informations.getName());
        return isEquals;
    }

    /**
     * @param category une catégorie
     * @param json les informations du descripteur concernant cette catégorie
     * @return true si <i>category</i> doit être mis à jour en fonction de <i>json</i>, false si <i>category</i> est déjà à jour
     */
    private boolean needUpdate(Category category, JsonCategory json) {
        var isEquals = true;
        isEquals &= json.getName().equals(category.getName());
        return isEquals;
    }

    /*private x createRoles(BasicChannel channel) {
        final var roles = channel.getRolesModifier();
        guild.getRoles().filter(r -> roles.getAddedRoles().contains(r.getName())).map(
                OverwriteData.builder().allow()
        )

    }*/
}
