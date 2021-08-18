package fr.istic.bot.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Représente une sous-catégorie du fichier de configuration JSON
 */
public class JsonSubCategory implements BasicChannel {

    private String name;
    private String oldName;
    private final List<JsonChannel> channels;
    private JsonOverwriteRole rolesModifier;

    public JsonSubCategory() {
        channels = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOldName() {
        return oldName == null ? null : "➽-" + oldName.replace(" ", "-");
    }

    /**
     * @param oldName l'ancien nom de la catégorie
     */
    @JsonProperty("old_name")
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * @param channels une liste de salons à ajouter pour cette sous-catégorie
     */
    @JsonProperty(value = "channels", required = true)
    public void addChannels(List<JsonChannel> channels) {
        this.channels.addAll(channels);
    }

    /**
     * @return la liste des salons de cette sous-catégorie
     */
    public List<JsonChannel> getChannels() {
        return channels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "➽-" + name.replace(" ", "-");
    }

    /**
     * @param name le nom de la catégorie
     */
    @JsonProperty(value = "name", required = true)
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonOverwriteRole getRolesModifier() {
        return rolesModifier;
    }

    /**
     * @param roleModifier la liste des rôles qui doivent pouvoir accéder ou non au salon
     */
    @JsonProperty(value = "roles")
    public void setRolesModifier(JsonOverwriteRole roleModifier) {
        this.rolesModifier = roleModifier;
    }
}
