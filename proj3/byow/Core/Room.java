package byow.Core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Room implements Unit{

    // class variables
    private Position keypoint;
    private String direction;
    private int length;
    private int width;
    private boolean whetherInitial;


    // constructor
    public Room(Position keypoint,String direction, int length, int width, boolean whetherInitial) {
        this.keypoint = keypoint;
        this.direction = direction;
        this.length = length;
        this.width = width;
        this.whetherInitial = whetherInitial;
    }

    // functions

    /** return the keypoint of the room */
    @Override
    public Position keypoint() {
        return keypoint;
    }

    @Override
    public String direction() {
        return direction;
    }

    @Override
    public List<Position> wall() {
        List<Position> wallPosition = new ArrayList<>();
        if (direction.equals("N")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x,y + j));
            }
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x + width - 1,y + j));
            }
            for (int i = 1; i < width - 1; i++) {
                if (!whetherInitial) {
                    if (x + i == x + width - 2) {
                        continue;
                    }
                }
                wallPosition.add(new Position(x + i, y));
            }
            for (int i = 1; i < width - 1; i++) {
                wallPosition.add(new Position(x + i,y + length - 1));
            }
        }

        if (direction.equals("S")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x, y - j));
            }
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x - width + 1, y - j));
            }
            for (int i = 1; i < width - 1; i++) {
                if (!whetherInitial) {
                    if (x - i == x - width + 2) {
                        continue;
                    }
                }
                wallPosition.add(new Position(x - i, y));
            }
            for (int i = 1; i < width - 1; i++) {
                wallPosition.add(new Position(x - i, y - length + 1));
            }
        }

        if (direction.equals("W")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x - j, y));
            }
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x - j, y + width - 1));
            }
            for (int i = 1; i < width - 1; i++) {
                if (!whetherInitial) {
                    if (y + i == y + width - 2) {
                        continue;
                    }
                }
                wallPosition.add(new Position(x, y + i));
            }
            for (int i = 1; i < width - 1; i++) {
                wallPosition.add(new Position(x - length + 1, y + i));
            }
        }

        if (direction.equals("E")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x + j, y));
            }
            for (int j = 0; j < length; j++) {
                wallPosition.add(new Position(x + j, y - width + 1));
            }
            for (int i = 1; i < width - 1; i++) {
                if (!whetherInitial) {
                    if (y - i == y - width + 2) {
                        continue;
                    }
                }
                wallPosition.add(new Position(x, y - i));
            }
            for (int i = 1; i < width - 1; i++) {
                wallPosition.add(new Position(x + length - 1, y - i));
            }
        }

        return wallPosition;
    }

    public List<Position> potentialSpanPoint() {
        List<Position> spanPoint = new ArrayList<>();

        if (direction.equals("N")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x,y + j));
            }
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x + width - 1,y + j));
            }
            for (int i = 2; i < width - 2; i++) {
                if (!whetherInitial) {
                    if (x + i == x + width - 2) {
                        continue;
                    }
                }
                spanPoint.add(new Position(x + i, y));
            }
            for (int i = 2; i < width - 2; i++) {
                spanPoint.add(new Position(x + i,y + length - 1));
            }
        }

        if (direction.equals("S")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x, y - j));
            }
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x - width + 1, y - j));
            }
            for (int i = 2; i < width - 2; i++) {
                if (!whetherInitial) {
                    if (x - i == x - width + 2) {
                        continue;
                    }
                }
                spanPoint.add(new Position(x - i, y));
            }
            for (int i = 2; i < width - 2; i++) {
                spanPoint.add(new Position(x - i, y - length + 1));
            }
        }

        if (direction.equals("W")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x - j, y));
            }
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x - j, y + width - 1));
            }
            for (int i = 2; i < width - 2; i++) {
                if (!whetherInitial) {
                    if (y + i == y + width - 2) {
                        continue;
                    }
                }
                spanPoint.add(new Position(x, y + i));
            }
            for (int i = 2; i < width - 2; i++) {
                spanPoint.add(new Position(x - length + 1, y + i));
            }
        }

        if (direction.equals("E")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x + j, y));
            }
            for (int j = 2; j < length - 2; j++) {
                spanPoint.add(new Position(x + j, y - width + 1));
            }
            for (int i = 2; i < width - 2; i++) {
                if (!whetherInitial) {
                    if (y - i == y - width + 2) {
                        continue;
                    }
                }
                spanPoint.add(new Position(x, y - i));
            }
            for (int i = 2; i < width - 2; i++) {
                spanPoint.add(new Position(x + length - 1, y - i));
            }
        }

        return spanPoint;

    }

    // for not initial rooms only
    @Override
    public List<Position> checkPoints() {
        List<Position> temp = wall();
        List<Position> checkPoint = new ArrayList<>();


        if (direction.equals("N")) {
            for (int x = keypoint().x; x < keypoint().x + width; x++) {
                for (int y = keypoint().y + 1; y < keypoint.y + length; y++) {
                    checkPoint.add(new Position(x, y));
                }
            }
        }

        if (direction.equals("S")) {
            for (int x = keypoint().x; x > keypoint().x - width; x--) {
                for (int y = keypoint().y - 1; y > keypoint.y - length; y--) {
                    checkPoint.add(new Position(x, y));
                }
            }
        }

        if (direction.equals("E")) {
            for (int x = keypoint().x + 1; x < keypoint().x + length; x++) {
                for (int y = keypoint().y; y > keypoint.y - width; y--) {
                    checkPoint.add(new Position(x, y));
                }
            }
        }

        if (direction.equals("W")) {
            for (int x = keypoint().x - 1; x > keypoint().x - length; x--) {
                for (int y = keypoint().y; y < keypoint.y + width; y++) {
                    checkPoint.add(new Position(x, y));
                }
            }
        }

        return checkPoint;
    }


    @Override
    public int length() {
        return length;
    }

    @Override
    public int width() {
        return width;
    }

    public Boolean whetherInitial() {
        return whetherInitial;
    }
}
