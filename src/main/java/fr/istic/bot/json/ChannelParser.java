package fr.istic.bot.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Parser du fichier descripteur du Discord
 */
public class ChannelParser {

    private ChannelParser() {}

    /**
     * @param stream un InputStream
     * @return une liste de JsonCategory résultant du parse du contenu du stream
     * @throws IOException si une erreur est survenue lors du parse
     */
    public static List<JsonCategory> parse(InputStream stream) throws IOException {
        final var mapper = new ObjectMapper();
        return mapper.readValue(stream, new TypeReference<>(){});
    }

}
