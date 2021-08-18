package fr.istic.bot.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Représente une sous-catégorie du fichier de configuration JSON
 */
public class JsonOverwriteRole {

    private final List<String> addedRoles;
    private final List<String> removedRoles;

    JsonOverwriteRole() {
        addedRoles = new LinkedList<>();
        removedRoles = new LinkedList<>();
    }

    /**
     * @return la liste des rôles autorisés
     */
    public List<String> getAddedRoles() {
        return addedRoles;
    }

    /**
     * @param addedRoles une liste de rôles à autoriser
     */
    @JsonProperty(value = "add")
    public void addAddedRoles(List<String> addedRoles) {
        this.addedRoles.addAll(addedRoles);
    }

    /**
     * @return la liste des rôles interdits
     */
    public List<String> getRemovedRoles() {
        return removedRoles;
    }

    /**
     * @param removedRoles la liste des rôles à interdire
     */
    @JsonProperty(value = "remove")
    public void addRemovedRoles(List<String> removedRoles) {
        this.removedRoles.addAll(removedRoles);
    }
}
