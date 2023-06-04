package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private TETile[][] world;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        // draws the world to the screen
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Point p = new Point(20, 20);
        addPlus(world, 3, p);
        ter.renderFrame(world);
    }
    public static void addPlus(TETile[][] world, int s, Point p) {
        TETile[][] thisWorld = world;
        // Find the keypoints.
        List<Point> keypoints = new ArrayList<>();
        keypoints.add(new Point(p.x, p.y + s));
        keypoints.add(new Point(p.x - s, p.y));
        keypoints.add(p);
        keypoints.add(new Point(p.x + s, p.y));
        keypoints.add(new Point(p.x, p.y - s));
        // Draw s*s blocks.
        for (Point keypoint : keypoints) {
            drawBlock(thisWorld, keypoint, s);
        }
    }
    public static void drawBlock(TETile[][] thisWorld, Point keypoint, int s) {
        for (int x = keypoint.x; x < keypoint.x + s; x += 1) {
            for (int y = keypoint.y - s; y < keypoint.y; y += 1) {
                thisWorld[x][y] = Tileset.FLOWER;
            }
        }
    }
}
