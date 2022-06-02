package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a game having a name, category, age rating, review rating,
//                   whether is favourite by the user, hours the user spent playing it
// Possible sub-classes: coop, racing, shooter, RPG, puzzle, sports,
// Age rating: https://en.wikipedia.org/wiki/Video_game_content_rating_system
public class Game implements Writable {
    private String name;
    private String category;
    // private int hoursToComplete;
    // private int progressPercent;
    // private int ranking;
    private int hoursSpent;
    private boolean isFavourite;
    private String ageRating;
    private int reviewRating;

    // EFFECTS: game has the given name, category, age rating,
    //          review rating, zero hours spent and is not favourite
    public Game(String name, String category, String ageRating, int reviewRating) {
        this.name = name;
        this.category = category;
        this.ageRating = ageRating;
        this.reviewRating = reviewRating;
        this.hoursSpent = 0;
        isFavourite = false;
    }

    //getters
    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public String getAgeRating() {
        return this.ageRating;
    }

    public int getReviewRating() {
        return this.reviewRating;
    }

    public int getHoursSpent() {
        return this.hoursSpent;
    }

    public boolean getIsFavourite() {
        return this.isFavourite;
    }

    //MODIFIES: this
    //EFFECTS: favourite this if this was not favourite,
    //         un-favourite this if this was favourite
    public void setFavourite() {
        boolean favourite = this.isFavourite;
        this.isFavourite = !favourite;
    }

    //REQUIRES: hours > 0
    //MODIFIES: this
    //EFFECTS: add hours to hoursSpent playing this game
    public void play(int hours) {
        this.hoursSpent = this.hoursSpent + hours;
    }



    // This method references code from CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("hoursSpent", hoursSpent);
        json.put("isFavourite", isFavourite);
        json.put("ageRating", ageRating);
        json.put("reviewRating", reviewRating);
        return json;
    }

}
