package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

public class KeyboardInputSource implements InputSource {
    private static final boolean PRINT_TYPED_KEYS = false;
    private String type;

    public KeyboardInputSource() {
        type = "KEYBOARD";
    }

    public char getNextKey() {
        while (true) {

            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
        }
    }

    public boolean possibleNextInput() {
        return StdDraw.hasNextKeyTyped();
    }

    public String getType() {
        return type;
    }
}
