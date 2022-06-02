package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads game gallery from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game gallery from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameGallery read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameGallery(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game gallery from JSON object and returns it
    private GameGallery parseGameGallery(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        GameGallery gg = new GameGallery(name);
        addGames(gg, jsonObject);
        return gg;
    }

    // MODIFIES: gg
    // EFFECTS: parses games from JSON object and adds them to game gallery
    private void addGames(GameGallery gg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(gg, nextGame);
        }
    }

    // MODIFIES: gg
    // EFFECTS: parses game from JSON object and adds it to game gallery
    private void addGame(GameGallery gg, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String category = jsonObject.getString("category");  //Category.valueOf(jsonObject.getString("category"));
        int hoursSpent = jsonObject.getInt("hoursSpent");
        boolean isFavourite = jsonObject.getBoolean("isFavourite");
        String ageRating = jsonObject.getString("ageRating");
        int reviewRating = jsonObject.getInt("reviewRating");
        Game game = new Game(name, category, ageRating, reviewRating);
        game.play(hoursSpent);
        if (isFavourite) {
            game.setFavourite();
        }
        gg.addGame(game);

    }
}
