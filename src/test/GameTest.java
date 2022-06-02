import model.*;
import persistence.JsonTest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest extends JsonTest{
    Game game;

    @BeforeEach
    void runBefore() {
        game = new Game("Overcooked 2", "Co-op", "E for Everyone", 9);
    }

    @Test
    void testSetFavourite() {
        game.setFavourite();
        assertTrue(game.getIsFavourite());

        game.setFavourite();
        assertFalse(game.getIsFavourite());
    }

    @Test
    void testPlay() {
        game.play(1);
        assertEquals(1, game.getHoursSpent());

        game.play(10);
        assertEquals(11, game.getHoursSpent());
    }

    @Test
    void testGetAgeRating() {
        assertEquals("E for Everyone", game.getAgeRating());
    }

    @Test
    void testGetReviewRating() {
        assertEquals(9, game.getReviewRating());
    }

    @Test
    void testGetName() {
        assertEquals("Overcooked 2", game.getName());
    }

    @Test
    void testGetCategory() {
        assertEquals("Co-op", game.getCategory());
    }

    @Test
    void testToJson() {
        JSONObject json = game.toJson();
        checkGame(json.getString("name"), json.getString("category"), json.getString("ageRating"),
                json.getInt("reviewRating"), json.getBoolean("isFavourite"), json.getInt("hoursSpent"),
                game);
    }
}
