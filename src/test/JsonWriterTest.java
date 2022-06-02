import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            GameGallery gg = new GameGallery("My game gallery");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGameGallery() {
        try {
            GameGallery gg = new GameGallery("My game gallery");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameGallery.json");
            writer.open();
            writer.write(gg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGameGallery.json");
            gg = reader.read();
            assertEquals("My game gallery", gg.getName());
            assertEquals(0, gg.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGameGallery() {
        try {
            GameGallery gg = new GameGallery("My game gallery");
            Game game1 = new Game("Overcooked 2", "Co-op", "E for Everyone", 9);
            Game game2 = new Game("Human Fall Flat", "Co-op", "4+", 8);
            Game game3 = new Game("WipEout", "Racing", "7+", 8);
            gg.addGame(game1);
            gg.addGame(game2);
            gg.addGame(game3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameGallery.json");
            writer.open();
            writer.write(gg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameGallery.json");
            gg = reader.read();
            assertEquals("My game gallery", gg.getName());
            List<Game> games = gg.getGames();
            assertEquals(3, games.size());
            checkGame("Overcooked 2", "Co-op", "E for Everyone", 9, false, 0, games.get(0));
            checkGame("Human Fall Flat", "Co-op", "4+", 8, false, 0, games.get(1));
            checkGame("WipEout", "Racing", "7+", 8, false, 0, games.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}