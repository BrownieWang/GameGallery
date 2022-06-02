package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;


// This class references code from CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkGame(String name, String category, String ageRating, int reviewRating, boolean favourite,
                             int hoursSpent, Game game) {
        assertEquals(name, game.getName());
        assertEquals(category, game.getCategory());
        assertEquals(ageRating, game.getAgeRating());
        assertEquals(reviewRating, game.getReviewRating());
        assertEquals(hoursSpent, game.getHoursSpent());
        assertEquals(favourite, game.getIsFavourite());
    }
}
