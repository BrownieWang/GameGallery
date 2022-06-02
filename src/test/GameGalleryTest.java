import model.Game;
import model.GameGallery;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameGalleryTest extends JsonTest {
    GameGallery gameGallery;
    Game game1;
    Game game2;
    Game game3;

    @BeforeEach
    void runBefore() {
        gameGallery = new GameGallery("My Game Gallery");
        game1 = new Game("Overcooked 2", "Co-op", "E for Everyone", 9);
        game2 = new Game("Human Fall Flat", "Co-op", "4+", 8);
        game3 = new Game("WipEout", "Racing", "7+", 8);
        gameGallery.addGame(game1);
        gameGallery.addGame(game2);
        gameGallery.addGame(game3);
    }

    @Test
    void testGetName() {
        assertEquals("My Game Gallery", gameGallery.getName());
    }

    @Test
    void testGetGames() {
        List<Game> games = gameGallery.getGames();
        assertEquals("Overcooked 2", games.get(0).getName());
        assertEquals("Human Fall Flat", games.get(1).getName());
        assertEquals("WipEout", games.get(2).getName());
    }

    @Test
    void testAddGame() {
        Game game4 = new Game("7 Days to Die", "Horror", "10+", 7);
        gameGallery.addGame(game4);

        assertEquals(4, gameGallery.length());
        assertEquals(3, gameGallery.getIndexInGallery("7 Days to Die"));
    }

    @Test
    void testGetIndexInGallery() {
        assertEquals(0, gameGallery.getIndexInGallery("Overcooked 2"));
        assertEquals(1, gameGallery.getIndexInGallery("Human Fall Flat"));
        assertEquals(2, gameGallery.getIndexInGallery("WipEout"));
        assertEquals(-1, gameGallery.getIndexInGallery("7 Days to Die"));
    }

    @Test
    void testDeleteGame() {
        assertTrue(gameGallery.deleteGame("Human Fall Flat"));

        assertEquals(2, gameGallery.length());
        assertEquals(0, gameGallery.getIndexInGallery("Overcooked 2"));
        assertEquals(1, gameGallery.getIndexInGallery("WipEout"));
        assertEquals(-1, gameGallery.getIndexInGallery("Human Fall Flat"));

        assertFalse(gameGallery.deleteGame("FIFA"));
    }

    @Test
    void testGetGame() {
        Game gameNow = gameGallery.getGame("Human Fall Flat");

        assertEquals("Human Fall Flat", gameNow.getName());
    }

    @Test
    void testGetGameFromIndex() {
        assertEquals("Overcooked 2", gameGallery.getGameFromIndex(0).getName());
        assertEquals("Human Fall Flat", gameGallery.getGameFromIndex(1).getName());
        assertEquals("WipEout", gameGallery.getGameFromIndex(2).getName());
    }

    @Test
    void testGetCategory() {
        GameGallery coop = gameGallery.getCategory("Co-op");

        assertEquals(2, coop.length());
        assertEquals(0, coop.getIndexInGallery("Overcooked 2"));
        assertEquals(1, coop.getIndexInGallery("Human Fall Flat"));
    }

    @Test
    void testGetListOfCategories() {
        List<String> categories = gameGallery.getListOfCategories();

        assertEquals(2, categories.size());
        assertEquals("Co-op", categories.get(0));
        assertEquals("Racing", categories.get(1));
    }

    @Test
    void testGetFavourites() {
        List<String> favourites = gameGallery.getFavourites();
        assertEquals(0, favourites.size());

        gameGallery.getGame("Overcooked 2").setFavourite();
        gameGallery.getGame("Human Fall Flat").setFavourite();

        List<String> favourites2 = gameGallery.getFavourites();
        assertEquals(2, favourites2.size());
        assertEquals("Overcooked 2", favourites2.get(0));
        assertEquals("Human Fall Flat", favourites2.get(1));

        gameGallery.getGame("Overcooked 2").setFavourite();

        List<String> favourites3 = gameGallery.getFavourites();
        assertEquals(1, favourites3.size());
        assertEquals("Human Fall Flat", favourites3.get(0));
    }

    @Test
    void testHasGame() {
        assertTrue(gameGallery.hasGame("Human Fall Flat"));
        assertFalse(gameGallery.hasGame("7 Days to Die"));
    }

    @Test
    void testHasCategory() {
        assertTrue(gameGallery.hasCategory("Racing"));
        assertFalse(gameGallery.hasCategory("Horror"));
    }

    @Test
    void testLengthEmpty() {
        assertEquals(3, gameGallery.length());
        assertFalse(gameGallery.isEmpty());

        gameGallery.deleteGame("Human Fall Flat");
        assertEquals(2, gameGallery.length());
        assertFalse(gameGallery.isEmpty());

        GameGallery newGallery = new GameGallery("My Game Gallery");
        assertEquals(0, newGallery.length());
        assertTrue(newGallery.isEmpty());
    }

    @Test
    void testGamesToJson() {
        JSONArray jsonArray = gameGallery.gamesToJson();
        JSONObject json1 = jsonArray.getJSONObject(0);
        JSONObject json2 = jsonArray.getJSONObject(1);
        JSONObject json3 = jsonArray.getJSONObject(2);
        checkGame(json1.getString("name"), json1.getString("category"), json1.getString("ageRating"),
                json1.getInt("reviewRating"), json1.getBoolean("isFavourite"),
                json1.getInt("hoursSpent"), game1);
        checkGame(json2.getString("name"), json2.getString("category"), json2.getString("ageRating"),
                json2.getInt("reviewRating"), json2.getBoolean("isFavourite"),
                json2.getInt("hoursSpent"), game2);
        checkGame(json3.getString("name"), json3.getString("category"), json3.getString("ageRating"),
                json3.getInt("reviewRating"), json3.getBoolean("isFavourite"),
                json3.getInt("hoursSpent"), game3);
    }

    @Test
    void testToJson() {
        JSONObject json = gameGallery.toJson();
        assertEquals("My Game Gallery", json.getString("name"));

        JSONArray jsonArray = json.getJSONArray("games");
        JSONObject json1 = jsonArray.getJSONObject(0);
        JSONObject json2 = jsonArray.getJSONObject(1);
        JSONObject json3 = jsonArray.getJSONObject(2);
        checkGame(json1.getString("name"), json1.getString("category"), json1.getString("ageRating"),
                json1.getInt("reviewRating"), json1.getBoolean("isFavourite"),
                json1.getInt("hoursSpent"), game1);
        checkGame(json2.getString("name"), json2.getString("category"), json2.getString("ageRating"),
                json2.getInt("reviewRating"), json2.getBoolean("isFavourite"),
                json2.getInt("hoursSpent"), game2);
        checkGame(json3.getString("name"), json3.getString("category"), json3.getString("ageRating"),
                json3.getInt("reviewRating"), json3.getBoolean("isFavourite"),
                json3.getInt("hoursSpent"), game3);
    }

}