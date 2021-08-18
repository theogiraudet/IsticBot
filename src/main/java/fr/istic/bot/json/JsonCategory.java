package fr.istic.bot.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Représente une catégorie du fichier de configuration JSON
 */
public class JsonCategory implements BasicChannel {

    private String name;
    private String oldName;
    private final List<JsonSubCategory> categories;
    private final JsonOverwriteRole roles;

    public JsonCategory() {
        categories = new LinkedList<>();
        this.roles = new JsonOverwriteRole();
    }

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
     * @param roles la liste des rôles qui doivent pouvoir accéder à la catégorie
     */
    @JsonProperty(value = "roles", required = true)
    public void setRolesModifier(List<String> roles) {
        this.roles.addAddedRoles(roles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonOverwriteRole getRolesModifier() {
        return roles;
    }

    /**
     * @param categories une liste de sous-catégories pour cette catégorie
     */
    @JsonProperty(value = "sub_categories", required = true)
    public void addSubCategories(List<JsonSubCategory> categories) {
        this.categories.addAll(categories);
    }

    /**
     * @return la liste de toutes les sous-catégories
     */
    public List<JsonSubCategory> getSubCategories() {
        return categories;
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

}
