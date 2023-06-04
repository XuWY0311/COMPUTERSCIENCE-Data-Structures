package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.io.File;

public class DealWithFile {
    public final static String filename = "Last_saved.txt";

    public static void saveFile(TETile[][] world) {
        // generate a String World from a 2D array World

        String StringWorld = TETile.toString(world);

        Out out = new Out(filename);
        out.println(StringWorld);
    }

    public static TETile[][] loadFile() {

        // check if the file is in the target position
        File file = new File(filename);
        if (file.exists()) {
            In in = new In(filename);
            String s = in.readAll();
            TETile[][] world = txtTo2DArray(s);
            return world;
        } else {
            System.exit(0);
        }
        return null;
    }

    public static TETile[][] txtTo2DArray(String s) {
        int worldWidth = Engine.WIDTH;
        int worldHeight = Engine.HEIGHT;

        int k = 0;

        TETile[][] world = new TETile[worldWidth][worldHeight];

        for (int j = worldHeight - 1; j >= 0; j--) {
            for (int i = 0; i < worldWidth; i++) {
                if (k < s.length()) {
                    if (String.valueOf(s.charAt(k)).equals(" ")) {
                        world[i][j] = Tileset.NOTHING;
                    }
                    if (String.valueOf(s.charAt(k)).equals("·")) {
                        world[i][j] = Tileset.FLOOR;
                    }
                    if (String.valueOf(s.charAt(k)).equals("#")) {
                        world[i][j] = Tileset.WALL;
                    }
                    if (String.valueOf(s.charAt(k)).equals("@")) {
                        world[i][j] = Tileset.AVATAR;
                    }
                    if (String.valueOf(s.charAt(k)).equals("▲")) {
                        world[i][j] = Tileset.MOUNTAIN;
                    }
                    if (String.valueOf(s.charAt(k)).equals("♠")) {
                        world[i][j] = Tileset.TREE;
                    }

                    if (i == worldWidth - 1) {
                        k = k + 2;
                    } else {
                        k++;
                    }
                }
            }
        }

        return world;
    }
}