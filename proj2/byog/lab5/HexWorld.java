package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    private static final int WIDTH = 27;
    private static final int HEIGHT = 30;

    private static void initial(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    private static void assignment(TETile[][] world, Position p, int s, int random) {
        int first_y = p.y - (2 * s) + 1;
        int last_y = p.y;
        int x = p.x;
        int length = s;
        int max_length = s + (s - 1) * 2;
        TETile assign = null;

        switch (random) {
            case 0 : assign = Tileset.WALL; break;
            case 1 : assign = Tileset.FLOWER; break;
            case 2 : assign = Tileset.GRASS; break;
            default : assign = Tileset.SAND;
        }

        while (length <= max_length) {
            for (int i = x, count = 0; count < length; i++, count++) {
                world[i][first_y] = assign;
                world[i][last_y] = assign;
            }

            length += 2;
            first_y += 1;
            last_y -= 1;
            x -= 1;
        }
    }

    private static void draw(TETile[][] world) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }

    private static Position topRightNeighbour(Position p, int s) {
        int y = p.y - s;
        int x = p.x + (2 * s - 2) + 1;
//        System.out.println(x + ", " + y);
        return new Position(x, y);
    }

    private static Position bottomRightNeighbour(Position p, int s) {
        int y = p.y + s;
        int x = p.x + (2 * s - 2) + 1;
//        System.out.println(x + ", " + y);
        return new Position(x, y);
    }

    public static void addHexagon(TETile[][] world, Position p, int s, int random) {
        //初始化
//        initial(world);
        //赋值
        assignment(world, p, s, random);
        //画图
//        draw(world);
    }

    public static void drawRandomVerticalHexes(TETile[][] world, int N, Position top, int s) {
        Position current = new Position(top.x, top.y);
        int tilenum = RANDOM.nextInt(3);

        for (int i = 0; i < N; i++) {
            addHexagon(world, current, s, tilenum);
            current.y += (2 * s);
            tilenum = RANDOM.nextInt(3);
        }
    }

    public static void main(String[] args) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        //initial
        initial(world);
        //first
        Position first_col = new Position(2, 11);
        drawRandomVerticalHexes(world, 3, first_col, 3);
        //second
        Position second_col = topRightNeighbour(first_col, 3);
        drawRandomVerticalHexes(world, 4, second_col, 3);
        //thirth
        Position third_col = topRightNeighbour(second_col, 3);
        drawRandomVerticalHexes(world, 5, third_col, 3);
        //forth
        Position forth_col = bottomRightNeighbour(third_col, 3);
        drawRandomVerticalHexes(world, 4, forth_col, 3);
        //fifth
        Position fifth_col = bottomRightNeighbour(forth_col, 3);
        drawRandomVerticalHexes(world, 3, fifth_col, 3);
        //draw
        draw(world);
    }

}
