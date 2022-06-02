package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// A game gallery to navigate game info
public class GameGallery implements Writable {
    private String name;
    private List<Game> games;
    private EventLog eventLog;

    //EFFECTS: Constructor, initializes a list of empty games
    public GameGallery(String name) {
        this.name = name;
        games = new ArrayList<>();
        eventLog = EventLog.getInstance();
    }

    // getter
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns an unmodifiable list of games in this game gallery
    public List<Game> getGames() {
        return Collections.unmodifiableList(games);
    }

    // EFFECTS: return the eventLog
    public EventLog getEventLog() {
        return eventLog;
    }


    //REQUIRES: game is not in this
    //MODIFIES: this
    //EFFECTS: add a new game to this
    public void addGame(Game game) {
        this.games.add(game);
        eventLog.logEvent(new Event(game.getName() + " added to Game Gallery!"));
    }

    //REQUIRES: this is not empty
    //EFFECTS: returns the position index of the game with gameName, -1 if not found
    public int getIndexInGallery(String gameName) {
        int pos = -1;

        for (Game next : this.games) {
            if (gameName.equals(next.getName())) {
                pos = this.games.indexOf(next);
                break;
            }
        }

        return pos;
    }


    //REQUIRES: this is not empty
    //MODIFIES: this
    //EFFECTS: delete the game with the given name and returns true;
    //         returns false if the game is not found.
    public boolean deleteGame(String gameName) {
        int pos = getIndexInGallery(gameName);

        if (pos != -1) {
            this.games.remove(pos);
            eventLog.logEvent(new Event(gameName + " deleted from Game Gallery!"));
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: this has a game with gameName
    //EFFECTS: returns the game with the given name
    public Game getGame(String gameName) {
        int pos = getIndexInGallery(gameName);

        return this.games.get(pos);
    }

    //REQUIRES: index >= 0
    //EFFECTS: returns the game with the given index
    public Game getGameFromIndex(int index) {
        return this.games.get(index);
    }


    //REQUIRES: this contains game(s) of this category
    //EFFECTS: returns a new GameGallery which have all the games in the given category from this
    public GameGallery getCategory(String category) {
        GameGallery thisCategory = new GameGallery("My Game Gallery - " + category);

        for (Game next : this.games) {
            if (category.equals(next.getCategory())) {
                thisCategory.addGame(next);
            }
        }

        return thisCategory;
    }

    //EFFECTS: returns a list of distinct category names found in this gallery
    public List<String> getListOfCategories() {
        List<String> listOfCategories = new ArrayList<>();

        for (Game next : this.games) {
            if (!listOfCategories.contains(next.getCategory())) {
                listOfCategories.add(next.getCategory());
            }
        }
        return listOfCategories;
    }

    //EFFECTS: returns a list of names of favourite games
    public List<String> getFavourites() {
        List<String> favourites = new ArrayList<>();

        for (Game next : this.games) {
            if (next.getIsFavourite()) {
                favourites.add(next.getName());
            }
        }
        return favourites;
    }


    //EFFECTS: returns true if this contains a game with the given name
    public boolean hasGame(String gameName) {
        return (this.getIndexInGallery(gameName) != -1);
    }

    //EFFECTS: returns true if this contains a game of the given category
    public boolean hasCategory(String category) {
        return this.getListOfCategories().contains(category);
    }

    // EFFECTS: returns the number of games in the gallery
    public int length() {
        return this.games.size();
    }

    // EFFECTS: returns true if the gallery is empty, false otherwise
    public boolean isEmpty() {
        return this.length() == 0;
    }

    // The following two methods reference code from CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("games", gamesToJson());
        return json;
    }

    // EFFECTS: returns games in this game gallery as a JSON array
    public JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : games) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }
}
