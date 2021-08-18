package fr.istic.bot.channels_updater;

import java.util.List;

/**
 * Représente les modifications à apporter à un channel
 */
public interface ChannelModifier {

    /**
     * Applique les modifications
     */
    void apply();

    /**
     * @return une liste des modifications qui seront apportés à application du modifier
     */
    List<String> plan();

    /**
     * @return le préfixe de modification du modifier
     */
    List<Character> getPrefix();

}
