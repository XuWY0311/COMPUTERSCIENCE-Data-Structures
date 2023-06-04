package byow.Core;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;

public class HUD {
    // class variable

    // constructor

    // method
    public static void printHUD(TETile[][] world) {
        StdDraw.setPenColor(Color.PINK);
        Font fontBig1 = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(fontBig1);

        StdDraw.setPenColor(Color.yellow);
        StdDraw.text(8, 29, "Click V to change vision");

        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();

        Position mousePosition = new Position(Math.min(x, Engine.WIDTH - 1), Math.min(y, Engine.HEIGHT - 1));
        TETile tileType = world[mousePosition.x][mousePosition.y];
        StdDraw.text(4, 30, tileType.description());
        StdDraw.show();

    }
}
