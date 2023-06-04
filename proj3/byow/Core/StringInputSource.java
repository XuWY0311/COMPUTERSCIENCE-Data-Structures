package byow.Core;


public class StringInputSource implements InputSource {
    private String input;
    private int index;

    private String type;

    public StringInputSource(String s) {
        index = 0;
        input = s;
        type = "STRING";
    }

    public char getNextKey() {
        char returnChar = Character.toUpperCase(input.charAt(index));
        index += 1;
        return returnChar;
    }

    public boolean possibleNextInput() {
        return index < input.length();
    }

    public String getType() {
        return type;
    }
}
