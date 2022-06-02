package ui;

import model.Event;
import model.EventLog;
import model.Game;
import model.GameGallery;

import java.util.List;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;


// Game Gallery Application
// This class references code from CPSC210/TellerApp
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
// and CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class GameGalleryApp {
    private static final String JSON_STORE = "./data/gameGallery.json";
    private GameGallery gameGallery;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the GameGallery application
    public GameGalleryApp() throws FileNotFoundException {
        runGameGallery();
    }

    //MODIFIES: this
    //EFFECTS: processes user input;
    private void runGameGallery() {
        boolean keepGoing = true;
        String command;

        init();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nTurning off....");

        EventLog eventLog = gameGallery.getEventLog();
        // eventLog.stream().skip(1);
        for (Event next : eventLog) {
            System.out.println(next.toString() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings("methodlength")
    private void processCommand(String command) {
        if (command.equals("v")) {
            doViewAllGames();
        } else if (command.equals("m")) {
            doViewAllCategories();
        } else if (command.equals("s")) {
            doViewAllFavourites();
        } else if (command.equals("a")) {
            doAddGame();
        } else if (command.equals("d")) {
            doDeleteGame();
        } else if (command.equals("p")) {
            doPlay();
        } else if (command.equals("g")) {
            doViewGame();
        } else if (command.equals("f")) {
            doFavouriteGame();
        } else if (command.equals("c")) {
            doViewCategory();
        } else if (command.equals("i")) {
            saveGameGallery();
        } else if (command.equals("l")) {
            loadGameGallery();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes GameGallery with three built-in games because they are so fun
    private void init() {
        gameGallery = new GameGallery("My Game Gallery");
        Game game1 = new Game("Overcooked 2", "Co-op", "E for Everyone", 9);
        Game game2 = new Game("Human Fall Flat", "Co-op", "4+", 8);
        Game game3 = new Game("WipEout", "Racing", "7+", 8);
        gameGallery.addGame(game1);
        gameGallery.addGame(game2);
        gameGallery.addGame(game3);
        // gameGallery.getEventLog().clear();

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view all games");
        System.out.println("\tm -> view all categories");
        System.out.println("\ts -> view all Favourites");
        System.out.println("\ta -> add a game");
        System.out.println("\td -> delete a game");
        System.out.println("\tp -> play a game");
        System.out.println("\tg -> view a game");
        System.out.println("\tf -> favourite a game");
        System.out.println("\tc -> view a category");
        System.out.println("\ti -> save game gallery to file");
        System.out.println("\tl -> load game gallery from file");
        System.out.println("\tq -> quit");
    }


    // EFFECTS: view the list of all games
    private void doViewAllGames() {
        if (gameGallery.isEmpty()) {
            System.out.print("No games downloaded yet!\n");
        } else {
            System.out.print("Games downloaded:\n");
            displayNames(gameGallery);
        }
    }

    // EFFECTS: view the list of all categories
    private void doViewAllCategories() {
        if (gameGallery.isEmpty()) {
            System.out.print("No games downloaded yet!\n");
        } else {
            System.out.print("Categories available:\n");
            printNames(gameGallery.getListOfCategories());
        }
    }

    // EFFECTS: view the list of all Favourite games
    private void doViewAllFavourites() {
        List<String> favourites = gameGallery.getFavourites();
        if (favourites.isEmpty()) {
            System.out.print("Favourite is empty...\n");
        } else {
            System.out.print("Favourites:\n");
            printNames(favourites);
        }
    }

    // MODIFIES: this
    // EFFECTS: add a game
    private void doAddGame() {
        System.out.print("Enter name of the game: ");
        String name = input.next();

        if (gameGallery.hasGame(name)) {
            System.out.println("Game " + name + " already added!\n");
        } else {
            System.out.print("Enter category of the game: ");
            String category = input.next();

            System.out.print("Enter age rating of the game: ");
            String ageRating = input.next();

            System.out.print("Enter review rating of the game (out of 10): ");
            int reviewRating = input.nextInt();

            gameGallery.addGame(new Game(name, category, ageRating, reviewRating));
            System.out.println("Game " + name + " added!\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete a game
    private void doDeleteGame() {
        System.out.print("Enter name of the game you want to delete: ");
        String name = input.next();

        if (gameGallery.hasGame(name)) {
            gameGallery.deleteGame(name);
            System.out.println("Game " + name + " deleted!\n");
        } else {
            System.out.println("Game " + name + " not found!\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: play a game
    private void doPlay() {
        System.out.print("Enter name of the game you want to play: ");
        String name = input.next();

        if (gameGallery.hasGame(name)) {
            System.out.print("Enter hours you spent playing: ");
            int hours = input.nextInt();

            gameGallery.getGame(name).play(hours);

            System.out.println("Played " + name + " for " + hours + " hours. Total hours: "
                    + gameGallery.getGame(name).getHoursSpent() + "\n");
        } else {
            System.out.println("Game " + name + " not found!\n");
        }
    }

    // EFFECTS: view information of a game
    private void doViewGame() {
        System.out.print("Enter name of the game you want to view: ");
        String name = input.next();

        if (gameGallery.hasGame(name)) {
            Game gameNow = gameGallery.getGame(name);
            String favourite = "No";

            if (gameNow.getIsFavourite()) {
                favourite = "Yes";
            }

            System.out.println("Name: " + gameNow.getName() + "\n"
                    + "Category: " + gameNow.getCategory() + "\n"
                    + "Age Rating: " + gameNow.getAgeRating() + "\n"
                    + "Review Rating: " + gameNow.getReviewRating() + "/10\n"
                    + "Hours spent playing: " + gameNow.getHoursSpent() + " h\n"
                    + "Favourite: " + favourite + "\n");
        } else {
            System.out.println("Game " + name + " not found!\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: mark a game as Favourite or remove a game from Favourites
    private void doFavouriteGame() {
        System.out.print("Enter name of the game you want to add to/ remove from Favourites: ");
        String name = input.next();

        if (gameGallery.hasGame(name)) {
            boolean favourite = gameGallery.getGame(name).getIsFavourite();
            gameGallery.getGame(name).setFavourite();

            if (!favourite) {
                System.out.println(name + " added to Favourite!\n");
            } else {
                System.out.println(name + " removed from Favourite!\n");
            }
        } else {
            System.out.println("Game " + name + " not found!\n");
        }
    }

    // EFFECTS: view names of games in a certain category
    private void doViewCategory() {
        System.out.print("Enter the category you want to view: ");
        String category = input.next();

        if (gameGallery.hasCategory(category)) {
            GameGallery gameGalleryNow = gameGallery.getCategory(category);

            System.out.println("Games in category " + category + ": ");
            displayNames(gameGalleryNow);
        } else {
            System.out.println("No game found in category " + category + "!\n");
        }
    }

    // EFFECTS: print a list of strings (game names)
    private void printNames(List<String> names) {
        for (String next : names) {
            System.out.println(next);
        }
    }

    // EFFECTS: display the names of all games in a game gallery
    private void displayNames(GameGallery gameGallery) {
        for (int i = 0; i < gameGallery.length(); i++) {
            System.out.println(gameGallery.getGameFromIndex(i).getName());
        }
    }

    // EFFECTS: saves the game gallery to file
    private void saveGameGallery() {
        try {
            jsonWriter.open();
            jsonWriter.write(gameGallery);
            jsonWriter.close();
            System.out.println("Saved " + gameGallery.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game gallery from file
    private void loadGameGallery() {
        try {
            gameGallery = jsonReader.read();
            System.out.println("Loaded " + gameGallery.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
