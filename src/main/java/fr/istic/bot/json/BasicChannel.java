package fr.istic.bot.json;

/**
 * Représente les informations basiques d'un modifieur de channel
 */
public interface BasicChannel {

    /**
     * @return l'ancien nom du channel
     */
    String getOldName();

    /**
     * @return le nom du channel
     */
    String getName();

    /**
     * @return les rôles pouvant voir ou non le salon
     */
    JsonOverwriteRole getRolesModifier();
}
