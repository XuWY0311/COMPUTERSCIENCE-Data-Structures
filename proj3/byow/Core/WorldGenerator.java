package byow.Core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class WorldGenerator {

    /**
     * Draws a world that contains RANDOM tiles.
     */

    private static int width;
    private static int length;
    private static final int RoomLengthBound = 6;
    private static final int RoomWidthBound = 6;

    private static final int HallwayLengthBound = 11;
    private static final int HallwayWidthBound = 2;
    private static final int spanBound = 3;


    private long SEED;
    private static Random RANDOM;

    // constructor
    public WorldGenerator(long inputSeed) {
        SEED = inputSeed;
        RANDOM  = new Random(SEED);
    }

    // functions
    public int width() {
        return width;
    }

    public int length() {
        return length;
    }

    private void buildBlankWorld(TETile[][] worldMatrix) {
        width = worldMatrix.length;
        length = worldMatrix[0].length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < length; y += 1) {
                worldMatrix[x][y] = Tileset.NOTHING;
            }
        }
    }

    private Room buildInitialRoom(TETile[][] worldMatrix) {

        Para newRoomPara = rdRoomPara();

        Room newRoom = new Room(new Position(width / 3, 2 * length / 3), "E", newRoomPara.length, newRoomPara.width, true);

        buildRoom(worldMatrix, newRoom);

        return newRoom;
    }

    private Para rdRoomPara() {
        // the range of room's length is 5 to 10
        int length = RANDOM.nextInt(RoomLengthBound) + 5;
        // the range of room's width is 5 to 10
        int width = RANDOM.nextInt(RoomWidthBound) + 5;

        Para roomPara = new Para(length, width);
        return roomPara;
    }

    private Para rdHallwayPara() {
        // the range of hallway's length is 5 to 15
        int length = RANDOM.nextInt(HallwayLengthBound) + 5;
        // the range of hallway's width is 3 to 4
        int width = RANDOM.nextInt(HallwayWidthBound) + 3;


        Para hallwayPara = new Para(length, width);
        return hallwayPara;
    }

    private void buildRoom(TETile[][] worldMatrix, Room rm) {

        if (rm.direction().equals("N")) {
            int x = rm.keypoint().x;
            int y = rm.keypoint().y;

            // interior
            for (int i = x; i < x + rm.width(); i++) {
                for (int j = y; j < y + rm.length(); j++) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: rm.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }

        if (rm.direction().equals("S")) {
            int x = rm.keypoint().x;
            int y = rm.keypoint().y;

            // interior
            for (int i = x; i > x - rm.width(); i--) {
                for (int j = y; j > y - rm.length(); j--) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: rm.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }

        if (rm.direction().equals("W")) {
            int x = rm.keypoint().x;
            int y = rm.keypoint().y;

            // interior
            for (int i = x; i > x - rm.length(); i--) {
                for (int j = y; j < y + rm.width(); j++) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: rm.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }

        if (rm.direction().equals("E")) {
            int x = rm.keypoint().x;
            int y = rm.keypoint().y;

            // interior
            for (int i = x; i < x + rm.length(); i++) {
                for (int j = y; j > y - rm.width(); j--) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: rm.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }
    }

    private void buildHallway(TETile[][] worldMatrix, Hallway hw) {


        if (hw.direction().equals("N")) {
            int x = hw.keypoint().x;
            int y = hw.keypoint().y;

            // interior
            for (int i = x - 1; i < x - 1 + hw.width(); i++) {
                for (int j = y; j < y + hw.length(); j++) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: hw.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }

        if (hw.direction().equals("S")) {
            int x = hw.keypoint().x;
            int y = hw.keypoint().y;

            // interior
            for (int i = x + 1; i > x + 1 - hw.width(); i--) {
                for (int j = y; j > y - hw.length(); j--) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: hw.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }

        if (hw.direction().equals("W")) {
            int x = hw.keypoint().x;
            int y = hw.keypoint().y;

            // interior
            for (int i = x; i > x - hw.length(); i--) {
                for (int j = y - 1; j < y - 1 + hw.width(); j++) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: hw.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }

        if (hw.direction().equals("E")) {
            int x = hw.keypoint().x;
            int y = hw.keypoint().y;


            // interior
            for (int i = x; i < x + hw.length(); i++) {
                for (int j = y + 1; j > y + 1 - hw.width(); j--) {
                    worldMatrix[i][j] = Tileset.FLOOR;
                }
            }

            // wall
            for (Position wallPoint: hw.wall()) {
                worldMatrix[wallPoint.x][wallPoint.y] = Tileset.WALL;
            }
        }

    }

    private int rdSpanNumber(Unit o1) {

        if (o1 instanceof Room) {
            if (((Room) o1).whetherInitial()) {
                // initial room can have 1,2,3 spans
                int spanNum = RANDOM.nextInt(spanBound) + 1;
                return spanNum;
            } else {
                // linked room can have 1,2,3 spans
                int spanNum = RANDOM.nextInt(spanBound) + 1;
                return spanNum;
            }
        } else {
            // hallway can have 3,4,5 spans
            int spanNum = RANDOM.nextInt(spanBound) + 3;
            return spanNum;
        }
    }

    // only use in case 3: Hallway + Orthogonal Hallway
    // and the initial case situation
    private String getCutDirection(Unit o1, Position cutPoint) {
        // for the room case
        if (o1.direction().equals("N")) {
            if (cutPoint.x == o1.keypoint().x) {
                return "W";
            }
            if (cutPoint.y == o1.keypoint().y) {
                return "S";
            }
            if (cutPoint.x == o1.keypoint().x + o1.width() - 1) {
                return "E";
            }
            if (cutPoint.y == o1.keypoint().y + o1.length() - 1) {
                return "N";
            }
        }
        if (o1.direction().equals("S")) {
            if (cutPoint.x == o1.keypoint().x - o1.width() + 1) {
                return "W";
            }
            if (cutPoint.y == o1.keypoint().y - o1.length() + 1) {
                return "S";
            }
            if (cutPoint.x == o1.keypoint().x) {
                return "E";
            }
            if (cutPoint.y == o1.keypoint().y) {
                return "N";
            }
        }
        if (o1.direction().equals("E")) {
            if (cutPoint.x == o1.keypoint().x) {
                return "W";
            }
            if (cutPoint.y == o1.keypoint().y - o1.width() + 1) {
                return "S";
            }
            if (cutPoint.x == o1.keypoint().x + o1.length() - 1) {
                return "E";
            }
            if (cutPoint.y == o1.keypoint().y) {
                return "N";
            }
        }
        if (o1.direction().equals("W")) {
            if (cutPoint.x == o1.keypoint().x - o1.length() + 1) {
                return "W";
            }
            if (cutPoint.y == o1.keypoint().y) {
                return "S";
            }
            if (cutPoint.x == o1.keypoint().x) {
                return "E";
            }
            if (cutPoint.y == o1.keypoint().y + o1.width() - 1) {
                return "N";
            }
        }

        //  for the hallway case: only use in case 3: Hallway + Orthogonal Hallway
        if (o1.direction().equals("E") || o1.direction().equals("W")) {
            if (cutPoint.y > o1.keypoint().y) {
                return "N";
            } else {
                return "S";
            }
        }
        if (o1.direction().equals("S") || o1.direction().equals("N")) {
            if (cutPoint.x > o1.keypoint().x) {
                return "E";
            } else {
                return "W";
            }
        }
        return null;
    }

    // for hallway to linked room
    private Position cutToKey(Hallway hw, Para toBuildRoomPAra) {
        Position cutPoint = hw.endpoint();
        if (hw.direction().equals("N")) {
            return new Position(cutPoint.x - toBuildRoomPAra.width + 2, cutPoint.y);
        }
        if (hw.direction().equals("S")) {
            return new Position(cutPoint.x + toBuildRoomPAra.width - 2, cutPoint.y);
        }
        if (hw.direction().equals("E")) {
            return new Position(cutPoint.x, cutPoint.y + toBuildRoomPAra.width - 2);
        }
        if (hw.direction().equals("W")) {
            return new Position(cutPoint.x, cutPoint.y - toBuildRoomPAra.width + 2);
        }
        return null;
    }

    private boolean whetherStop(TETile[][] worldMatrix, Unit toBuiltUnit) {
        List<Position> checkPoints = toBuiltUnit.checkPoints();
        for (Position p : checkPoints) {
            if (p.x > width - 1 || p.x < 0 || p.y > length - 1 || p.y < 0) {
                return true;
            }
            if (!(worldMatrix[p.x][p.y] == Tileset.NOTHING)) {
                return true;
            }
        }
        return false;
    }

    private Unit span(TETile[][] worldMatrix, Unit thisUnit) {

        if (thisUnit instanceof Room) {
            // find a span point
            List<Position> wallList = ((Room) thisUnit).potentialSpanPoint();
            int idx = RANDOM.nextInt(wallList.size());
            Position spanPoint = wallList.get(idx);
            // initiate a new hallway according to the span point
            Para newHallwayPara = rdHallwayPara();
            String direction = getCutDirection(thisUnit, spanPoint);
            Hallway newHallway = new Hallway(spanPoint, direction, newHallwayPara.length, newHallwayPara.width);
            // whether to stop
//            if (((Room) thisUnit).whetherInitial()) {
//                buildHallway(worldMatrix, newHallway);
//                return newHallway;
//            } else {
            if (!whetherStop(worldMatrix, newHallway)) {
                buildHallway(worldMatrix, newHallway);
                return newHallway;
            }
        }

        if (thisUnit instanceof Hallway) {
            int tileNum = RANDOM.nextInt(3);
            switch (tileNum) {
                case 0: // hallway links with a room
                    // find a span point
                    Para newRoomPara = rdRoomPara();
                    Position newKeyPoint = cutToKey((Hallway) thisUnit, newRoomPara);
                    // initiate a new room according to the span point
                    Room newRoom = new Room(newKeyPoint, thisUnit.direction(), newRoomPara.length, newRoomPara.width, false);
                    // whether to stop
                    if (!whetherStop(worldMatrix, newRoom)) {
                        buildRoom(worldMatrix, newRoom);
                        return newRoom;
                    }

                    ;
                case 1: // hallway links with a parallel hallway
                    // find a span point
                    Position spanPointTWO = ((Hallway) thisUnit).endpoint();
                    // initiate a new hallway according to the span point
                    Para newHallwayPara = rdHallwayPara();
                    Hallway newHallway = null;
                    if (thisUnit.width() == 3) {
                        newHallway = new Hallway(spanPointTWO, thisUnit.direction(), newHallwayPara.length, 4);
                    }
                    if (thisUnit.width() == 4) {
                        newHallway = new Hallway(spanPointTWO, thisUnit.direction(), newHallwayPara.length, 3);
                    }
                    // whether to stop
                    if (!whetherStop(worldMatrix, newHallway)) {
                        buildHallway(worldMatrix, newHallway);
                        // additional fix: replace another keypoint's floor with wall
                        worldMatrix[newHallway.anotherKeyPoint().x][newHallway.anotherKeyPoint().y] = Tileset.WALL;
                        return newHallway;
                    }
                    ;
                case 2: // hallway links with an orthogonal hallway
                    // find a span point
                    List<Position> potentialSpanPoint = ((Hallway) thisUnit).potentialSpanPointForCaseThree();

                    int idx = RANDOM.nextInt(potentialSpanPoint.size());
                    Position spanPointTHREE = potentialSpanPoint.get(idx);

                    // get new span direction
                    String newDirection = getCutDirection(thisUnit, spanPointTHREE);

                    // initiate a new hallway according to the span point
                    Para newHallwayParaTHREE = rdHallwayPara();
                    newHallway = new Hallway(spanPointTHREE, newDirection, newHallwayParaTHREE.length, 3);

                    // whether to stop
                    if (!whetherStop(worldMatrix, newHallway)) {
                        buildHallway(worldMatrix, newHallway);
                        return newHallway;
                    }
                    ;
            }
        }
        return null;
    }

    public void generation(TETile[][] worldMatrix) {

        buildBlankWorld(worldMatrix);
        Room root = buildInitialRoom(worldMatrix);

        Queue<Unit> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Unit thisUnit = queue.poll();
            int spanNumber = rdSpanNumber(thisUnit);
            for (int i = 0; i < spanNumber; i++) {
                Unit nextUnit = span(worldMatrix, thisUnit);
                if (nextUnit != null) {
                    queue.add(nextUnit);
                }
            }
        }
    }

}
