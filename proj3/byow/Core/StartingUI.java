package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class StartingUI {
    static TERenderer ter = new TERenderer();


    // class variables
    private int width;
    private int height;

    private String appearance = "AVATAR";

    // constructor
    public StartingUI(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        // change the HUD bar size
        StdDraw.setYscale(0, this.height + 1);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    // methods
    public void drawMenu() {
        // setup
        StdDraw.clear(Color.BLACK);

        // drawing title
        StdDraw.setPenColor(Color.BLUE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height * 3 / 4, "CS61B: BYOW game BY MINNIE");


        // drawing menu
        StdDraw.setPenColor(Color.PINK);
        Font fontBig1 = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontBig1);
        StdDraw.text(this.width / 2, this.height * 1 / 2, "New Game (N)");
        StdDraw.text(this.width / 2, this.height * 1 / 2 - 1, "Load Game (L)");
        StdDraw.text(this.width / 2, this.height * 1 / 2 - 2, "Role Appearance (R)");
        StdDraw.text(this.width / 2, this.height * 1 / 2 - 3, "Quit (Q)");

        StdDraw.show();
    }

    public String solicitSeed(InputSource inputSource) {

        String inputs = new String();

        StdDraw.setPenColor(Color.PINK);
        Font fontBig1 = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontBig1);
        StdDraw.text(this.width / 2, this.height * 1 / 2 - 6, "Enter Seed: ");
        StdDraw.show();

        while (true) {
            char input = inputSource.getNextKey();
            // breakpoint
            if (String.valueOf(input).equals("S")) {
                return inputs;
            }

            inputs = inputs + input;

            // print input seed number
            drawMenu();
            StdDraw.text(this.width / 2, this.height * 1 / 2 - 6, "Enter Seed: ");

            StdDraw.text(this.width / 2, this.height * 1 / 2 - 7, inputs);
            StdDraw.show();
        }
    }

    public String solicitAppearance(InputSource inputSource) {
        StdDraw.setPenColor(Color.CYAN);
        Font fontBig1 = new Font("Monaco", Font.BOLD, 12);
        StdDraw.setFont(fontBig1);
        StdDraw.text(this.width / 2, this.height * 1 / 2 - 5, "Choose your role's appearance: AVATAR (O), MOUNTAIN (M), TREE (T)");
        StdDraw.show();

        char key = inputSource.getNextKey();
        if (String.valueOf(key).equals("M")) {
            return "MOUNTAIN";
        } else if (String.valueOf(key).equals("T")) {
            return "TREE";
        } else {
            return "AVATAR";
        }
    }

    public Position getRoleKeypoint(TETile[][] world) {
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                if (world[i][j].equals(Tileset.AVATAR) || world[i][j].equals(Tileset.MOUNTAIN) || world[i][j].equals(Tileset.TREE)) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    public String getRoleAppearance(TETile[][] world) {
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                if (world[i][j].equals(Tileset.MOUNTAIN)) {
                    return "MOUNTAIN";
                }
                if (world[i][j].equals(Tileset.TREE)) {
                    return "TREE";
                }
                if (world[i][j].equals(Tileset.AVATAR)) {
                    return "AVATAR";
                }
            }
        }
        return null;
    }

    public TETile[][] dealWithMenuInput(String wholeInput, String sourceType) {

        drawMenu();

        InputSource inputSource;

        if (sourceType.equals("STRING")) {
            inputSource = new StringInputSource(wholeInput);
        } else { // sourceType.equals("KEYBOARD")
            inputSource = new KeyboardInputSource();
        }

        char key = inputSource.getNextKey();

        if (String.valueOf(key).equals("N")) {
            String seed = solicitSeed(inputSource);
            TETile[][] finalWorldFrame = new TETile[Engine.WIDTH][Engine.HEIGHT];
            WorldGenerator world = new WorldGenerator(Long.parseLong(seed));
            world.generation(finalWorldFrame);

            Role player = new Role(appearance);
            player.drawRole(finalWorldFrame);

            return finalWorldFrame;
        }
        if (String.valueOf(key).equals("L")) {
            TETile[][] finalWorldFrame = DealWithFile.loadFile();
            Position keypoint = getRoleKeypoint(finalWorldFrame);
            String appearance = getRoleAppearance(finalWorldFrame);

            Role player = new Role(keypoint, appearance);
            player.drawRole(finalWorldFrame);

            return finalWorldFrame;
        }
        if (String.valueOf(key).equals("Q")) {
            System.exit(0);
            return null;
        }
        if (String.valueOf(key).equals("R")) {
            String newAppearance = solicitAppearance(inputSource);
            appearance = newAppearance;
            TETile[][] world = dealWithMenuInput(wholeInput, sourceType);
            return world;
        }
        return null;
    }

}
