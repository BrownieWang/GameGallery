import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameGallery gg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGameGallery() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGameGallery.json");
        try {
            GameGallery gg = reader.read();
            assertEquals("My game gallery", gg.getName());
            assertEquals(0, gg.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameGallery() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameGallery.json");
        try {
            GameGallery gg = reader.read();
            assertEquals("My game gallery", gg.getName());
            List<Game> games = gg.getGames();
            assertEquals(3, games.size());
            checkGame("Overcooked 2", "Co-op", "E for Everyone", 9, true, 0, games.get(0));
            checkGame("Human Fall Flat", "Co-op", "4+", 8, false, 0, games.get(1));
            checkGame("WipEout", "Racing", "7+", 8, false, 0, games.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}