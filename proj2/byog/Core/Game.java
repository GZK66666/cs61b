package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import javax.swing.*;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import byog.TileEngine.Tileset;
import byog.lab5.Position;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;
    private static final int ENTRYX = 40;
    private static final int ENTRYY = 5;
    private static final int WELCOMEWIDTH = 600;
    private static final int WELCOMEHEIGHT = 800;
    private static final String WORLDPATH = "world.txt";
    private static final String PLAYERPATH = "player.txt";

    private boolean setupMode = true;
    private boolean newGameMode = false;
    private boolean quitMode = false;
    private String seedString = "";
    private String input = "";
    private TETile[][] world;
    private Position playerPosition;

    ///private method/

    private void switchNewGameMode() {

        newGameMode = !newGameMode;

    }

    private void switchSetupMode() {

        setupMode = !setupMode;

    }

    private void switchQuitMode() {

        quitMode = !quitMode;

    }

    /* Processes game recursively according to a given input Strings */
    private void
    processInput(String input) {

        if (input == null) {
            System.out.println("No input given!");
            System.exit(0);
        }

        String first = String.valueOf(input.charAt(0));
        first = first.toLowerCase();
        processInputString(first);

        if (input.length() > 1) {
            input = input.substring(1);
            processInput(input);
        }

    }

    /* Processes game according to a given single input String */
    private void processInputString(String input) {

        if (setupMode) {
            switch (input) {
                case "n" :
                    switchNewGameMode();
                    break;
                case "s" :
                    setupnewGame();
                    break;
                case "l" :
                    load();
                    break;
                case "q" :
                    System.exit(0);
                    break;
                default:
                    try {
                        Long.parseLong(input);
                        seedString += input;
                    }catch (NumberFormatException e) {
                        System.out.println("Invalid input given: " + input);
                        System.exit(0);
                    }
                    break;
            }
        }else {
            switch (input) {
                case "w" :
                case "s" :
                case "a" :
                case "d" :
                    move(input);
                    break;
                case ":" :
                    switchQuitMode();
                    break;
                case "q" :
                    saveAndQuit();
                    System.exit(0);
                    break;
                default:
            }
        }

    }

    /* Generate a random world and put a player in it */
    private void setupnewGame() {

        if (!newGameMode) {
            String error = "Input string " + "\"S\" given, but no game has been initialized.\n"
                    + "Please initialize game first by input string \"N\" and following random seed"
                    + "numbers";
            System.out.println(error);
            System.exit(0);
        }

        switchNewGameMode();

        MapGenerator map;
        if (seedString.equals("")) {
            map = new MapGenerator(WIDTH, HEIGHT, ENTRYX, ENTRYY);
        }else {
            long seed = Long.parseLong(seedString);
            map = new MapGenerator(WIDTH, HEIGHT, ENTRYX, ENTRYY, seed);
        }

        world = map.generate();
        playerPosition = map.playerPosition;

        switchSetupMode();

    }

    /* move the player */
    private void move(String input) {

        switch(input) {
            case "w" :
                if (!world[playerPosition.x][playerPosition.y + 1].equals(Tileset.WALL)) {
                    world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
                    world[playerPosition.x][playerPosition.y + 1] = Tileset.PLAYER;
                    playerPosition = new Position(playerPosition.x, playerPosition.y + 1);
                }
                return;
            case "s" :
                if (!world[playerPosition.x][playerPosition.y - 1].equals(Tileset.WALL) && !world[playerPosition.x][playerPosition.y - 1].equals(Tileset.LOCKED_DOOR)) {
                    world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
                    world[playerPosition.x][playerPosition.y - 1] = Tileset.PLAYER;
                    playerPosition = new Position(playerPosition.x, playerPosition.y - 1);
                }
                return;
            case "a" :
                if (!world[playerPosition.x - 1][playerPosition.y].equals(Tileset.WALL)) {
                    world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
                    world[playerPosition.x - 1][playerPosition.y] = Tileset.PLAYER;
                    playerPosition = new Position(playerPosition.x - 1, playerPosition.y);
                }
                return;
            default:
                if (!world[playerPosition.x + 1][playerPosition.y].equals(Tileset.WALL)) {
                    world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
                    world[playerPosition.x + 1][playerPosition.y] = Tileset.PLAYER;
                    playerPosition = new Position(playerPosition.x + 1, playerPosition.y);
                }
                return;
        }

    }

    /* save the game */
    private void saveAndQuit() {

        if (!quitMode) {
            return;
        }

        switchQuitMode();

        File worldFile = new File(WORLDPATH);
        File playerFile = new File(PLAYERPATH);

        try {
            if (!worldFile.exists()) {
                worldFile.createNewFile();
            }
            if (!playerFile.exists()) {
                playerFile.createNewFile();
            }

            FileOutputStream worldos = new FileOutputStream(worldFile);
            ObjectOutputStream worldoos = new ObjectOutputStream(worldos);
            worldoos.writeObject(world);
            worldoos.close();

            FileOutputStream playeros = new FileOutputStream(playerFile);
            ObjectOutputStream playeroos = new ObjectOutputStream(playeros);
            playeroos.writeObject(playerPosition);
            playeroos.close();
        }catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

    }

    /* load the game */
    private void load() {

        File worldFile = new File(WORLDPATH);
        File playerFile = new File(PLAYERPATH);

        try {
            FileInputStream worldis = new FileInputStream(worldFile);
            ObjectInputStream worldois = new ObjectInputStream(worldis);
            world = (TETile[][]) worldois.readObject();
            worldois.close();

            FileInputStream playeris = new FileInputStream(playerFile);
            ObjectInputStream playerois = new ObjectInputStream(playeris);
            playerPosition = (Position) playerois.readObject();
            playerois.close();
        }catch (FileNotFoundException e) {
            System.out.println("No previously saved world found.");
            System.exit(0);
        }catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }catch (ClassNotFoundException e) {
            System.out.println("Class TETile[][] not found.");
            System.exit(1);
        }

        switchSetupMode();
        switchNewGameMode();

    }

    /* show the welcome interface and receive the user input */
    private void processWelcome() {

        // prepare welcome board window
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WELCOMEWIDTH, WELCOMEHEIGHT);
        StdDraw.clear(StdDraw.BLACK);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String input = String.valueOf(StdDraw.nextKeyTyped());
                processInput(input);
            }

            renderWelcomeBoard();

            if (!setupMode) {
                break;
            }
        }

        processGame();

    }

    /* draw the welcome interface */
    private void renderWelcomeBoard() {

        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);

        //title
        StdDraw.setFont(new Font("Arial", Font.BOLD, 40));
        StdDraw.text(0.5, 0.8, "CS61B:BYoG");

        //menu
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.text(0.5, 0.5, "New Game: N");
        StdDraw.text(0.5, 0.475, "Load Game: L");
        StdDraw.text(0.5, 0.45, "Quit: Q");

        //seed
        if (newGameMode) {
            StdDraw.text(0.5, 0.25, "Seed: " + seedString);
            StdDraw.text(0.5, 0.225, "(Press S to start the game)");
        }

        StdDraw.show();
        StdDraw.pause(100);

    }

    /* Process keyboard inputs in game mode  */
    private void processGame() {

        ter.initialize(WIDTH, HEIGHT);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String input = String.valueOf(StdDraw.nextKeyTyped());
                processInput(input);
            }

            renderGame();
        }

    }

    /* Renders the current state of the game */
    private void renderGame() {

        renderWorld();
        showTileOnHover();
        StdDraw.pause(100);

    }

    /* Renders the world */
    private void renderWorld() {

        ter.renderFrame(world);

    }

    /* Draws text describing the Tile currently under the mouse pointer */
    private void showTileOnHover() {

        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        TETile mouseTetile = world[mouseX][mouseY];

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 15));
        StdDraw.setPenColor(Color.white);
        StdDraw.textLeft(1, HEIGHT - 1, mouseTetile.description());
        StdDraw.show();

    }


    ///public method
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        processWelcome();

    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {

        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        processInput(input);

        return world;

    }
}
