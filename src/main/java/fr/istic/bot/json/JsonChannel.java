package fr.istic.bot.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Représente un salon du fichier de configuration JSON
 */
public class JsonChannel implements BasicChannel {

    private String name;
    private String description;
    private String oldName;
    private JsonOverwriteRole rolesModifier;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOldName() {
        return oldName;
    }

    /**
     * @param oldName l'ancien nom de la catégorie
     */
    @JsonProperty("old_name")
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name le nom de la catégorie
     */
    @JsonProperty(value = "name", required = true)
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return la description du salon
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description la description du salon
     */
    @JsonProperty(value = "description", required = true)
    public void setDescription(String description) {
        this.description = description;
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
