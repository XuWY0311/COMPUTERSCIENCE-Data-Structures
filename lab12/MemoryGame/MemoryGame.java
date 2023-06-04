package MemoryGame;

import byowTools.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;
import org.eclipse.jetty.server.AbstractNetworkConnector;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String result = new String();
        for (int i = 0; i < n ; i++) {
            int target = rand.nextInt(CHARACTERS.length);
            char charI = CHARACTERS[target];
            result = result + charI;
        }
        return result;
    }

    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
        * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height / 2, s);

        //TODO: If the game is not over, display encouragement, and let the user know if they
        // should be typing their answer or watching for the next round.
        if (!gameOver) {
            Random rand = new Random(); //instance of random class
            int upperbound = ENCOURAGEMENT.length;
            int int_random = rand.nextInt(upperbound);
            Font fontBig1 = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(fontBig1);
            StdDraw.line(0, 38, 40, 38);
            StdDraw.text(3, 39, "Round:" + this.round);
            StdDraw.text(35, 39, ENCOURAGEMENT[int_random]);
            if (playerTurn == false) {
                StdDraw.text(20, 39, "Watch!");
            } else {
                StdDraw.text(20, 39, "Type!");
            }
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(Character.toString(letters.charAt(i)));
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        String inputs = new String();
        drawFrame(inputs);
        while (inputs.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
              char input = StdDraw.nextKeyTyped();
              inputs = inputs + String.valueOf(input);
              drawFrame(inputs);
            }
        }
        StdDraw.pause(1000);
        return new String(inputs);
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        round = 1;
        playerTurn = false;

        //TODO: Establish Engine loop
        while (!gameOver) {
            // Watch Time!
            playerTurn = false;
            drawFrame("Round:" + round);
            StdDraw.pause(1000);
            String thisString = generateRandomString(round);
            flashSequence(thisString);
            // Type Time!
            playerTurn = true;
            String typeString = solicitNCharsInput(round);
            // Check Time!
            if (thisString.equals(typeString)) {
                round = round + 1;
                StdDraw.pause(1000);
            } else {
                gameOver = true;
                drawFrame("Game Over! You made it to round: " + round);
            }
        }
    }
}
