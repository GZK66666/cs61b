package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.util.*;

public class MapGenerator {

    /// Static members
    private static final int MAXROOMWIDTH = 6;
    private static final int MAXROOMHEIGHT = 6;
    private static final String NORTH = "N";
    private static final String EAST = "E";
    private static final String SOUTH = "S";
    private static final String WEST = "W";


    /// Instance members
    private int width;
    private int height;
    private Position initialPosition;
    public Position playerPosition;
    private Random random;
    private TETile[][] world;

    /* constructor without seed */
    MapGenerator(int width, int height, int initialX, int initialY) {

        this.width = width;
        this.height = height;
        initialPosition = new Position(initialX, initialY);
        playerPosition = new Position(initialX, initialY + 1);
        random = new Random();

    }

    /*constructor with seed*/
    MapGenerator(int width, int height, int initialX, int initialY, long seed) {

        this.width = width;
        this.height = height;
        initialPosition = new Position(initialX, initialY);
        playerPosition = new Position(initialX, initialY + 1);
        random = new Random(seed);

    }

    /* initial the world */
    private void initialize() {

        world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

    }

    /* make a room based on the given position */
    private void makeRoom(Position leftBottom, Position rightUpper) {

        for (int x = leftBottom.x; x <= rightUpper.x; x++) {
            for (int y = leftBottom.y; y <= rightUpper.y; y++) {
                if (x == leftBottom.x || y == leftBottom.y || x == rightUpper.x || y == rightUpper.y) {
                    world[x][y] = Tileset.WALL;
                }else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }

    }

    /* make the initial entrance */
    private void makeInitialEntrance(Position initialEntrance) {

        world[initialEntrance.x][initialEntrance.y] = Tileset.LOCKED_DOOR;

    }

    /* make a entrance on the room by change the wall to floor */
    private void makeEntrance(Position entrance) {

        world[entrance.x][entrance.y] = Tileset.FLOOR;

    }

    /* make the player */
    private void makePlayer() {
        world[playerPosition.x][playerPosition.y] = Tileset.PLAYER;
    }

    /* make the exit on the room by change the wall to floor */
    private void makeExit(Position exit) {

        world[exit.x][exit.y] = Tileset.FLOOR;

    }

    /* check if there is enough space to make a room base on the given position */
    private boolean checkAvailability(Position leftBottom, Position rightUpper) {

        if (leftBottom.x < 0 || leftBottom.y < 0 || rightUpper.x < 0 ||
                rightUpper.y < 0 || leftBottom.x >= width ||
                leftBottom.y >= height ||rightUpper.x >= width
                || rightUpper.y >= height) {
            return false;
        }

        for (int x = leftBottom.x; x < rightUpper.x; x++) {
            for (int y = leftBottom.y; y < rightUpper.y; y++) {
                if (world[x][y] == Tileset.FLOOR || world[x][y] == Tileset.WALL) {
                    return false;
                }
            }
        }

        return true;
    }

    /* return a new room on the north position based on a entrance if available, otherwise return null
     * 其实就是给定一个入口和长宽，随机生成一个房间的左下角和右下角的位置 */
    private Position[] randomPositionNorth(int w, int h, Position entrance) {

        int leftBottomX = entrance.x - random.nextInt(w) - 1; // 为什么要-1？？？
        int leftBottomY = entrance.y;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);

        if (checkAvailability(leftBottom, rightUpper)) {
            return new Position[]{leftBottom, rightUpper};
        }else {
            return null;
        }

    }

    /* return a new room on the north position based on a entrance if available, otherwise return null */
    private Position[] randomPositionSouth(int w, int h, Position entrance) {

        int rightUpperX = entrance.x + random.nextInt(w) + 1; // 为什么要-1？？？
        int rightUpperY = entrance.y;
        int leftBottomX = rightUpperX - w - 1;
        int leftBottomY = rightUpperY - h - 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);

        if (checkAvailability(leftBottom, rightUpper)) {
            return new Position[]{leftBottom, rightUpper};
        }else {
            return null;
        }

    }

    /* return a new room on the north position based on a entrance if available, otherwise return null */
    private Position[] randomPositionEast(int w, int h, Position entrance) {

        int leftBottomX = entrance.x; // 为什么要-1？？？
        int leftBottomY = entrance.y - random.nextInt(h) - 1;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);

        if (checkAvailability(leftBottom, rightUpper)) {
            return new Position[]{leftBottom, rightUpper};
        }else {
            return null;
        }

    }

    /* return a new room on the north position based on a entrance if available, otherwise return null */
    private Position[] randomPositionWest(int w, int h, Position entrance) {

        int rightUpperX = entrance.x; // 为什么要-1？？？
        int rightUpperY = entrance.y + random.nextInt(h) + 1;
        int leftBottomX = rightUpperX - w - 1;
        int leftBottomY = rightUpperY - h - 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);

        if (checkAvailability(leftBottom, rightUpper)) {
            return new Position[]{leftBottom, rightUpper};
        }else {
            return null;
        }

    }

    /* Returns the reverse direction of a given direction */
    private String getReverseDirection(String direction) {

        switch (direction) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            default:
                return EAST;
        }

    }

    /* return a random exit based on the given room */
    private Object[] randomExit(Position leftBottom, Position rightUpper, String currentDirection) {

        int w = rightUpper.x - leftBottom.x - 1;
        int h = rightUpper.y - leftBottom.y - 1;

        //random generate the exit direction
        List<String> directions = new LinkedList<>();
        directions.add(NORTH);
        directions.add(SOUTH);
        directions.add(WEST);
        directions.add(EAST);
        directions.remove(getReverseDirection(currentDirection));
        String nextDirection = directions.get(random.nextInt(directions.size()));

        //generate the exit position
        Position nextExitPosition;
        switch (nextDirection) {
            case NORTH :
                nextExitPosition = new Position(rightUpper.x - random.nextInt(w) - 1, rightUpper.y);
                break;
            case SOUTH :
                nextExitPosition = new Position(leftBottom.x + random.nextInt(w) + 1, leftBottom.y);
                break;
            case EAST :
                nextExitPosition = new Position(rightUpper.x, rightUpper.y - random.nextInt(h) - 1);
                break;
            default :
                nextExitPosition = new Position(leftBottom.x, leftBottom.y + random.nextInt(h) + 1);
        }

        return new Object[]{nextExitPosition, nextDirection};

    }

    /* make a bi exit based on the given room and call another recursiveAddRandomRoom */
    private void biExit(Position leftBottom, Position rightUpper, String currentDirection) {

        //exit1
        Object[] exit1 = randomExit(leftBottom, rightUpper, currentDirection);
        Position exitPosition1 = (Position) exit1[0];
        String direction1 = (String) exit1[1];

        //exit2
        Object[] exit2 = randomExit(leftBottom, rightUpper, currentDirection);
        Position exitPosition2 = (Position)exit2[0];
        String direction2 = (String) exit2[1];

        while (direction1.equals(direction2)) {
            exit2 = randomExit(leftBottom, rightUpper, currentDirection);
            exitPosition2 = (Position)exit2[0];
            direction2 = (String)exit2[1];
        }

        recursiveAddRandomRoom(exitPosition1, direction1);
        recursiveAddRandomRoom(exitPosition2, direction2);

    }

    /* make a tri exit based on the given room and call another recursiveAddRandomRoom */
    private void triExit(Position leftBottom, Position rightUpper, String currentDirection) {

        //exit1
        Object[] exit1 = randomExit(leftBottom, rightUpper, currentDirection);
        Position exitPosition1 = (Position) exit1[0];
        String direction1 = (String) exit1[1];

        //exit2
        Object[] exit2 = randomExit(leftBottom, rightUpper, currentDirection);
        Position exitPosition2 = (Position)exit2[0];
        String direction2 = (String) exit2[1];

        while (direction1.equals(direction2)) {
            exit2 = randomExit(leftBottom, rightUpper, currentDirection);
            exitPosition2 = (Position)exit2[0];
            direction2 = (String)exit2[1];
        }

        //exit3
        Object[] exit3 = randomExit(leftBottom, rightUpper, currentDirection);
        Position exitPosition3 = (Position)exit3[0];
        String direction3 = (String) exit3[1];

        while (direction1.equals(direction3) || direction2.equals(direction3)) {
            exit3 = randomExit(leftBottom, rightUpper, currentDirection);
            exitPosition3 = (Position)exit3[0];
            direction3 = (String)exit3[1];
        }

        recursiveAddRandomRoom(exitPosition1, direction1);
        recursiveAddRandomRoom(exitPosition2, direction2);
        recursiveAddRandomRoom(exitPosition3, direction3);

    }

    /* recursive adds  random room */
    private void recursiveAddRandomRoom(Position exit, String currentDirection) {

        int exitX = exit.x;
        int exitY = exit.y;
        int w = random.nextInt(MAXROOMWIDTH) + 1;
        int h = random.nextInt(MAXROOMHEIGHT) + 1;

        Position entrance;
        Position[] roomPositions;

        switch (currentDirection) {
            case NORTH :
                entrance = new Position(exitX, exitY + 1);
                roomPositions = randomPositionNorth(w, h, entrance);
                break;
            case SOUTH :
                entrance = new Position(exitX, exitY - 1);
                roomPositions = randomPositionSouth(w, h, entrance);
                break;
            case EAST :
                entrance = new Position(exitX + 1, exitY);
                roomPositions = randomPositionEast(w, h, entrance);
                break;
            default :
                entrance = new Position(exitX - 1, exitY);
                roomPositions = randomPositionWest(w, h, entrance);
                break;
        }

        if (roomPositions != null) {
            makeExit(exit);
            Position leftBottom = roomPositions[0];
            Position rightUpper = roomPositions[1];
            makeRoom(leftBottom, rightUpper);
            makeEntrance(entrance);

            switch (random.nextInt(3) + 1) {
                /* Comment in below for more simpler world */
//                case 1: monoExit(leftBottom, rightUpper, currentDirection); break;
                case 2:
                    biExit(leftBottom, rightUpper, currentDirection);
                    break;
                default:
                    triExit(leftBottom, rightUpper, currentDirection);
                    break;
            }
        }

    }

    public TETile[][] generate() {

        initialize();

        //make the first room
        Position[] firstRoomPositions = randomPositionNorth(MAXROOMWIDTH, MAXROOMHEIGHT, initialPosition);
        Position leftBottom = firstRoomPositions[0];
        Position rightUpper = firstRoomPositions[1];
        makeRoom(leftBottom, rightUpper);
        makeInitialEntrance(initialPosition);

        // Recursively call recursiveAddRandomRoom via first calling triExit
        triExit(leftBottom, rightUpper, NORTH);

        //make the player
        makePlayer();

        return world;

    }



    public static void main(String[] args) {

        int w = 80;
        int h = 40;

        long seed = Long.parseLong(args[0]);

        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(w, h);
        MapGenerator m = new MapGenerator(w, h, 40, 5, seed);
        TETile[][] world = m.generate();
        teRenderer.renderFrame(world);

    }
}
