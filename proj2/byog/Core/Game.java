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
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final String WORLDPATH = "world.txt";
    private static final String PLAYERPATH = "player.txt";

    public String input = "";
    public TETile[][] world;
    public Position playerPosition;


    /* draw the world */
    private void drawTheWorld(boolean initial) {

        if (initial) {
            ter.initialize(WIDTH, HEIGHT);
        }
        ter.renderFrame(world);
        showTileOnHover();

    }

    /* Draws text describing the Tile currently under the mouse pointer */
    private void showTileOnHover() {
        // turn the position of mouse pointer into xy-coordinate
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        TETile mouseTile = world[mouseX][mouseY];

        // draw as text
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 15));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textLeft(1, HEIGHT - 1, mouseTile.description());
        StdDraw.show();
    }

    /* receive the random seed from user input */
    private String randomSeed() {

        //接受随机数
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.textLeft(0, 2, "please input random seed end with S:");
        StdDraw.show();

        while (!StdDraw.hasNextKeyTyped()){}
        char inputChar = StdDraw.nextKeyTyped();

        while (inputChar != 's') {
            input += String.valueOf(inputChar);
            StdDraw.clear(Color.black);
            mainMenu();
            Font font1 = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(font1);
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(0, 2, "please input random seed end with S:");
            StdDraw.textLeft(0, 1, input.substring(1, input.length()) + "(press S to start game!)");
            StdDraw.show();
            while (!StdDraw.hasNextKeyTyped()){}
            inputChar = StdDraw.nextKeyTyped();
        }

        return input.substring(1, input.length());

    }

    /* random generate the initial world */
    private void generateInitialWorldByKeyword(String seed) {

        MapGenerator map = new MapGenerator(WIDTH, HEIGHT, 40, 5, seed);
        world = map.generate();
        playerPosition = map.playerPosition;
        drawTheWorld(true);
        //user control
        userControlByKeywork();


    }

    /* W operation */
    private void wOperation() {

        int newPositionX = playerPosition.x;
        int newPositionY = playerPosition.y + 1;

        if (world[newPositionX][newPositionY].equals(Tileset.FLOOR)) {
            world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
            world[newPositionX][newPositionY] = Tileset.PLAYER;
            playerPosition.y = newPositionY;
            this.input += 'w';
        }

        drawTheWorld(false);

    }

    /* S operation */
    private void sOperation() {

        int newPositionX = playerPosition.x;
        int newPositionY = playerPosition.y - 1;

        if (world[newPositionX][newPositionY].equals(Tileset.FLOOR)) {
            world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
            world[newPositionX][newPositionY] = Tileset.PLAYER;
            playerPosition.y = newPositionY;
            this.input += 's';
        }

        drawTheWorld(false);

    }

    /* D operation */
    private void dOperation() {

        int newPositionX = playerPosition.x + 1;
        int newPositionY = playerPosition.y;

        if (world[newPositionX][newPositionY].equals(Tileset.FLOOR)) {
            world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
            world[newPositionX][newPositionY] = Tileset.PLAYER;
            playerPosition.x = newPositionX;
            this.input += 'd';
        }

        drawTheWorld(false);

    }

    /* W operation */
    private void aOperation() {

        int newPositionX = playerPosition.x - 1;
        int newPositionY = playerPosition.y;

        if (world[newPositionX][newPositionY].equals(Tileset.FLOOR)) {
            world[playerPosition.x][playerPosition.y] = Tileset.FLOOR;
            world[newPositionX][newPositionY] = Tileset.PLAYER;
            playerPosition.x = newPositionX;
            this.input += 'a';
        }

        drawTheWorld(false);

    }

    /* user control */
    private void userControlByKeywork() {

        boolean flag = true;

        while (!StdDraw.hasNextKeyTyped()){}
        char input = StdDraw.nextKeyTyped();

        while (input != 'q' || flag) {
            switch (input) {
                case 'w' :
                    wOperation();
                    break;
                case 's' :
                    sOperation();
                    break;
                case 'd' :
                    dOperation();
                    break;
                case 'a' :
                    aOperation();
                    break;
                case ':' :
                    this.input += ':';
                    flag = false;
                    break;
                default:
                    break;
            }

            while (!StdDraw.hasNextKeyTyped()){ }
            input = StdDraw.nextKeyTyped();
        }

        this.input += 'q';

        System.out.println(this.input);

        //save the game
        quitAndSave();

        System.exit(1);

    }

    /* quit and save */
    private void quitAndSave() {

        File world = new File(WORLDPATH);
        File player = new File(PLAYERPATH);

        try {
            if (!world.exists()) {
                world.createNewFile();
            }
            if (!player.exists()) {
                player.createNewFile();
            }
            //save sorld
            FileOutputStream fos = new FileOutputStream(world);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.world);
            System.out.println("save world success!");
            oos.close();
            //save player
            FileOutputStream fos1 = new FileOutputStream(player);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(playerPosition);
            System.out.println("save player success!");
            oos1.close();
        }catch (IOException e) {
            System.out.println(e);
            System.exit(-1);
        }

    }

    /* load the game */
    private void loadGame(String TypeOrOerations) {

        File world = new File(WORLDPATH);
        File player = new File(PLAYERPATH);

        try {
            if (!world.exists()) {
                System.out.println("the world doesn't exit!");
                System.exit(-1);
            }
            if (!player.exists()) {
                System.out.println("the player doesn't exit!");
                System.exit(-1);
            }
            //load world
            FileInputStream fis = new FileInputStream(world);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.world = (TETile[][]) ois.readObject();
            System.out.println("read world success!");
            ois.close();
            //load player
            FileInputStream fis1 = new FileInputStream(player);
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            this.playerPosition = (Position) ois1.readObject();
            System.out.println("read player success!");
            ois1.close();
        }catch (IOException e) {
            System.out.println("io fail!");
            System.exit(-1);
        }catch (ClassNotFoundException e) {
            System.out.println("class not found!");
            System.exit(-1);
        }

        drawTheWorld(true);

        if (TypeOrOerations.equals("Keywork")) {
            userControlByKeywork();
        }else {
            userControlByString(TypeOrOerations);
        }

    }

    /* receive the user input */
    private void userInput() {

        while (!StdDraw.hasNextKeyTyped()){}
        char input = StdDraw.nextKeyTyped();
        String seed;

        if (input == 'n') {
            this.input += String.valueOf(input);
            seed = randomSeed();
            this.input += 's';
            generateInitialWorldByKeyword(seed);
        }else if (input == 'l') {
            //load game
            loadGame("Keywork");
        }else if (input == 'q') {
            System.exit(0);
        }else {
            System.exit(-1);
        }

    }

    /* main menu */
    private void mainMenu() {

        int width = 40;
        int height = 40;

        StdDraw.setCanvasSize(width * 16, height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.black);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 2 + 10, "CS61B: THE GAME");
        StdDraw.text(width / 2, height / 2, "New Game(N)");
        StdDraw.text(width / 2, height / 2 - 2, "Load Game(L)");
        StdDraw.text(width / 2, height / 2 - 4, "Quit(Q)");

        StdDraw.show();

    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        //show the main menu
        mainMenu();
        //receive user input
        userInput();

        return;

    }

    /* user control based on the given string */
    private void userControlByString(String operations) {

        for (int i = 0; i < operations.length(); i++) {
            char operation = operations.charAt(i);
            switch (operation) {
                case 'w' :
                    wOperation();
                    break;
                case 's' :
                    sOperation();
                    break;
                case 'd' :
                    dOperation();
                    break;
                default:
                    aOperation();
                    break;
            }
        }

    }

    /* generate a new world based on the given string */
    private void generateInitialWorldByString(String input) {

        int i;
        for (i = 1; i < input.length(); i++) {
            if (input.charAt(i) == 's') {
                break;
            }
        }

        MapGenerator map = new MapGenerator(WIDTH, HEIGHT, 40, 5, input.substring(1, i));
        playerPosition = map.playerPosition;
        world = map.generate();

        drawTheWorld(true);

        String operations = "";
        while (i < input.length() && input.charAt(i) != ':') {
            operations += String.valueOf(input.charAt(i));
            i++;
        }

        //user control
        if (!operations.equals("")) {
            operations = operations.substring(1, operations.length());
            userControlByString(operations);
        }

        //save the game
        if (i < input.length() && input.charAt(i) == ':' && input.charAt(i + 1) == 'q') {
            quitAndSave();
        }

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
        if (input.length() < 1) {
            System.out.println("please input valid string!");
            System.exit(-1);
        }

        this.input += input;

        if (input.startsWith("n")) {
            generateInitialWorldByString(input);
        }else if (input.startsWith("l")) {
            String operaitons = input.substring(1, input.length());
            loadGame(operaitons);
        }else {
            System.out.println("please input the string start with n or l!");
        }

        return world;

    }

}
