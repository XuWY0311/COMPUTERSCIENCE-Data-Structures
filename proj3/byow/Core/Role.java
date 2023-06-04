package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class Role {
    TERenderer ter = new TERenderer();
    // local variable
    private Position keypoint;
    public TETile appearance;

    // constructor
    public Role(String appearance) {
        int width = Engine.WIDTH;
        int height = Engine.HEIGHT;
        Position initialRoomKeypoint = new Position(width / 3, 2 * height / 3);
        Position initialPlayerKeypoint = new Position(initialRoomKeypoint.x + 1, initialRoomKeypoint.y - 1);
        this.keypoint = initialPlayerKeypoint;
        changeAppearance(appearance); //
    }

    public Role(Position keypoint, String appearance) {
        this.keypoint = keypoint;
        changeAppearance(appearance); //
    }

    // method
    public Position keypoint() {
        return keypoint;
    }

    private void changeAppearance(String newAppearance) {
        if (newAppearance.equals("MOUNTAIN")) {
            this.appearance = Tileset.MOUNTAIN;
        } else if (newAppearance.equals("TREE")) {
            this.appearance = Tileset.TREE;
        } else {
            this.appearance = Tileset.AVATAR;
        }
    }

    public void drawRole(TETile[][] world) {
        world[keypoint.x][keypoint.y] = appearance;
    }

    private boolean validMove(TETile[][] world, String direction) {

        if (direction.equals("W")) {
            if (world[keypoint().x][keypoint().y + 1].equals(Tileset.WALL)) {
                return false;
            }
        }

        if (direction.equals("S")) {
            if (world[keypoint().x][keypoint().y - 1].equals(Tileset.WALL)) {
                return false;
            }
        }

        if (direction.equals("A")) {
            if (world[keypoint().x - 1][keypoint().y].equals(Tileset.WALL)) {
                return false;
            }
        }

        if (direction.equals("D")) {
            if (world[keypoint().x + 1][keypoint().y].equals(Tileset.WALL)) {
                return false;
            }
        }

        return true;
    }

    private Position nextKeypoint(String direction) {
        if (direction.equals("W")) {
            return new Position(keypoint().x, keypoint().y + 1);
        }

        if (direction.equals("S")) {
            return new Position(keypoint().x, keypoint().y - 1);
        }

        if (direction.equals("A")) {
            return new Position(keypoint().x - 1, keypoint().y);
        }

        if (direction.equals("D")) {
            return new Position(keypoint().x + 1, keypoint().y);
        }

        return null;
    }

    private void moveRole(TETile[][] world, String direction) {
        // check if step into a wall
        if (validMove(world, direction)) {
            // get next key point
            Position nextKeyPoint = nextKeypoint(direction);
            // change old key point to floor and change new key point to avatar
            world[keypoint.x][keypoint.y] = Tileset.FLOOR;
            keypoint.x = nextKeyPoint.x;
            keypoint.y = nextKeyPoint.y;
            world[keypoint.x][keypoint.y] = appearance;
        }
    }

    private boolean checkNumeric(String s) {
        int intValue;
        try {
            intValue = Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void moveByKeyBoard(TETile[][] world, String wholeInput, String sourceType) {

        InputSource inputSource;
        Boolean whetherLocalVision = false;

        if (sourceType.equals("STRING")) {
            inputSource = new StringInputSource(wholeInput);
        } else { // sourceType.equals("KEYBOARD")
            inputSource = new KeyboardInputSource();
        }


        List<String> inputList = new ArrayList<>();
        while (true) {

            // display HUD
            HUD.printHUD(world);

            if (inputSource.possibleNextInput()) {
                char input = inputSource.getNextKey();//
                String stringInput = String.valueOf(input);

                inputList.add(stringInput);

                // breaking point
                if (inputList.size() != 1) {
                    String prevInput = inputList.get(inputList.size() - 2);
                    if (prevInput.equals(":") && stringInput.equals("Q")) {
                        DealWithFile.saveFile(world);
                        System.exit(0);
                    }
                }

                // switch local vision and global vision
                if (stringInput.equals("V")) {
                    whetherLocalVision = !whetherLocalVision;
                }

                // check whether it is during the game or during the creating seed
                if (inputList.size() != 1) {
                    String prevInput = inputList.get(inputList.size() - 2);
                    if (checkNumeric(prevInput)) {
                        System.out.println(prevInput);
                        continue;
                    }

                }
                // valid move of player
                if (stringInput.equals("W") || stringInput.equals("S") || stringInput.equals("A") || stringInput.equals("D")) {
                    moveRole(world, stringInput);
                }
            }

            // render: if we want the local vision of the world
            if (whetherLocalVision) {
                TETile[][] localWorld = localVisionWorld(world);
                ter.renderFrame(localWorld);
            } else {
                ter.renderFrame(world);
            }

        }
    }

    private List<Position> localVisionPosition() {
        List<Position> positionList = new ArrayList<>();
        Position keypoint = keypoint();

        for (int i = -5; i < 6; i++) {
            for (int j = -5; j < 6; j++) {
                if (Math.abs(i) + Math.abs(j) <= 5) {
                    positionList.add(new Position(keypoint.x + i, keypoint.y + j));
                }
            }
        }

        return positionList;
    }

    private boolean whetherLocalPosition(List<Position> localVisionPositionList, int x, int y) {
        for (Position p : localVisionPositionList) {
            if (p.x == x && p.y == y) {
                return true;
            }
        }
        return false;
    }

    public TETile[][] localVisionWorld(TETile[][] world) {
        TETile[][] copyWorld = TETile.copyOf(world);
        List<Position> localVisionPositionList = localVisionPosition();
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                if (!whetherLocalPosition(localVisionPositionList, i, j)) {
                    copyWorld[i][j] = Tileset.NOTHING;
                }
            }
        }
        return copyWorld;
    }
}
