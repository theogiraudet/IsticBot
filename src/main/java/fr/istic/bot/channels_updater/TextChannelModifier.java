package fr.istic.bot.channels_updater;

/**
 * Représente les informations principales des salons textuels
 */
public interface TextChannelModifier extends ChannelModifier {

    /**
     * @return le nom du salon textuel
     */
    String getName();

}
