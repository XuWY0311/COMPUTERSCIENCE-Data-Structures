package byow.Core;

import java.util.ArrayList;
import java.util.List;

public class Hallway implements Unit {
    // class variables
    private Position keypoint;
    private String direction;
    private int length;
    private int width;

    // constructor
    public Hallway(Position keypoint,String direction, int length, int width) {
        this.keypoint = keypoint;
        this.direction = direction;
        this.length = length;
        this.width = width;
    }

    // functions

    /** return the keypoint of the hallway */
    @Override
    public Position keypoint() {
        return keypoint;
    }

    public Position anotherKeyPoint() {
        if (direction.equals("N")) {
            return new Position(keypoint().x + 1, keypoint().y);
        }

        if (direction.equals("S")) {
            return new Position(keypoint().x - 1, keypoint().y);
        }

        if (direction.equals("E")) {
            return new Position(keypoint().x, keypoint().y - 1);
        }

        if (direction.equals("W")) {
            return new Position(keypoint().x, keypoint().y + 1);
        }
        return null;
    }

    @Override
    public String direction() {
        return direction;
    }

    @Override
    public List<Position> wall() {
        List<Position> wallPosition = new ArrayList<>();

        if (width == 3) {
            if (direction.equals("N")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - 1,y + j));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + 1,y + j));
                }
                wallPosition.add(endpoint());
            }

            if (direction.equals("S")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - 1,y - j));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + 1,y - j));
                }
                wallPosition.add(endpoint());
            }

            if (direction.equals("W")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - j,y + 1));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - j,y - 1));
                }
                wallPosition.add(endpoint());
            }

            if (direction.equals("E")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + j,y + 1));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + j,y - 1));
                }
                wallPosition.add(endpoint());
            }
        } else {
            if (direction.equals("N")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - 1,y + j));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + 2,y + j));
                }
                wallPosition.add(endpoint());
                wallPosition.add(new Position(endpoint().x + 1, endpoint().y));
            }

            if (direction.equals("S")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - 2,y - j));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + 1,y - j));
                }
                wallPosition.add(endpoint());
                wallPosition.add(new Position(endpoint().x - 1, endpoint().y));
            }

            if (direction.equals("W")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - j,y - 1));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x - j,y + 2));
                }
                wallPosition.add(endpoint());
                wallPosition.add(new Position(endpoint().x, endpoint().y + 1));
            }

            if (direction.equals("E")) {
                int x = keypoint.x;
                int y = keypoint.y;
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + j,y + 1));
                }
                for (int j = 0; j < length; j++) {
                    wallPosition.add(new Position(x + j,y - 2));
                }
                wallPosition.add(endpoint());
                wallPosition.add(new Position(endpoint().x, endpoint().y - 1));
            }

        }

        return wallPosition;
    }

    @Override
    public List<Position> checkPoints() {
        List<Position> temp = wall();
        List<Position> checkPoint = new ArrayList<>();

        if (direction.equals("N") || direction.equals("S")) {
            for (Position i : temp) {
                if (i.y != keypoint().y) {
                    checkPoint.add(i);
                }
            }
        }

        if (direction.equals("E") || direction.equals("W")) {
            for (Position i : temp) {
                if (i.x != keypoint().x) {
                    checkPoint.add(i);
                }
            }
        }

        return checkPoint;
    }


    public Position endpoint() {
        if (direction.equals("N")) {
            return new Position(keypoint().x, keypoint().y + length - 1);
        }

        if (direction.equals("S")) {
            return new Position(keypoint().x, keypoint().y - length + 1);
        }

        if (direction.equals("W")) {
            return new Position(keypoint().x - length + 1, keypoint().y);
        }

        if (direction.equals("E")) {
            return new Position(keypoint().x + length - 1, keypoint().y);
        }
        return null;
    }

    public List<Position> potentialSpanPointForCaseThree() {
        List<Position> temp = wall();
        List<Position> potentialSpanPoint = new ArrayList<>();


        if (direction.equals("N")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x - 1,y + j));
            }
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x + width - 2,y + j));
            }
        }

        if (direction.equals("S")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x + 1, y - j));
            }
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x - width + 2, y - j));
            }
        }

        if (direction.equals("W")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x - j, y - 1));
            }
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x - j, y + width - 2));
            }
        }

        if (direction.equals("E")) {
            int x = keypoint.x;
            int y = keypoint.y;
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x + j, y + 1));
            }
            for (int j = 2; j < length - 2; j++) {
                potentialSpanPoint.add(new Position(x + j, y - width + 2));
            }
        }

        return potentialSpanPoint;
    }


    @Override
    public int length() {
        return length;
    }

    @Override
    public int width() {
        return width;
    }
}
