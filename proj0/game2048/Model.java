package game2048;

import java.util.Formatter;
import java.util.Observable;
import java.util.List;
import java.util.ArrayList;



/** The state of a game of 2048.
 *  @author Weiyuan XU
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private final Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    /** Same as above, but gameOver is false. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        this(rawValues, score, maxScore, false);
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     * */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Allow initial game board to announce a hot start to the GUI. */
    public void hotStartAnnounce() {
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */

    public void tilt(Side side) {
        /** Initiate the side. */
        _board.setViewingPerspective(side);

        /** Initiate the list that indicates whether a tile has been merged before. */
        List<Pair> hasBeenMerged = new ArrayList<Pair>();

        /** Iterate the board from the first column. */
        for (int column = 0; column < _board.size(); column = column + 1) {

            /** Iterate each column from the highest row. */
            for (int row = 3; row >= 0; row = row - 1) {
                Tile t = _board.tile(column, row);
                int originalValue;
                if (t != null) {
                    originalValue = t.value();
                } else {
                    originalValue = 0;
                }
                    /** When tile exists, then it may move. */
                    if (t != null) {

                        /** If it is a Merge move, record the increase in score. */
                        int targetRow = targetRow(_board, column, row, hasBeenMerged);
                        Tile targetTile = _board.tile(column, targetRow);
                        int targetValue;
                        if (targetTile != null) {
                            targetValue = targetTile.value();
                        } else {
                            targetValue = 0;
                        }
                        if ((originalValue == targetValue) && (row != targetRow)) {
                            _score = _score + 2 * targetValue;

                            /** If it is a Merge move, label that the newly-created tile cannot be merged again. */
                            Pair mergedTile = new Pair(column, targetRow);
                            hasBeenMerged.add(mergedTile);
                        }

                        /** Move. */
                        _board.move(column, targetRow, t);
                    }
                }
            }
            /** Implement this tilt action. */
            setChanged();

            /** Reset the side to North. */
            _board.setViewingPerspective(Side.NORTH);

            /** Check whether game is over after each tilt. */
            checkGameOver();
        }


    /** Self-created Helper Class No. 1
     * This class helps to create a Pair class.
     */
    public static class Pair{
        private final int key;
        private final int value;
        public Pair(int aKey, int aValue)
        {
            key   = aKey;
            value = aValue;
        }
        public int key()   { return key; }
        public int value() { return value; }
    }

    /** Self-created Helper Function No. 1
     * This function judges whether it is empty between the specific two tiles on the same column.
     * There are two situations that will make this function return true:
     * 1. The two input rows are the same row or they are adjacent;
     * 2. The two input rows are not adjacent and there is no tile between these two rows given the column.
     */
    public static boolean isEmpty(Board b, int column, int higherRow, int lowerRow){
        if ((higherRow == lowerRow) || (higherRow == lowerRow + 1)){
            return true;
        }else{
            if (higherRow == lowerRow + 2){
                if (b.tile(column,lowerRow + 1) == null){
                    return true;
                }
            }
            if (higherRow == lowerRow + 3){
                if ((b.tile(column,lowerRow + 1) == null) && (b.tile(column,lowerRow + 2) == null)){
                    return true;
                }
            }
        }
        return false;
    }

    /** Self-created Helper Function No. 2
     * For each element on the specific column,
     * this function returns the target row to end after each tilt.
     *
     * There are two types of move:
     * 1. Non-merge move;
     * 2. Merge move.
     */
    public int targetRow(Board b, int column, int row, List<Pair> hasBeenMerged){
        for (int j = 3; j >= row; j = j - 1){
            if ((canNonMergeMove(b,column,row,j) == true) | (canMergeMove(b,column,row,j,hasBeenMerged) == true)){
                return j;
            }
        }
        return row;
    }


    /** Self-created Helper Function No. 3
     * This function help judge whether it is feasible to do a Not-merge move.
     * There are two conditions to realize Not-merge move:
     * 1. The target tile is empty;
     * 2. It is empty between original tile and target tile.
     */
    public static boolean canNonMergeMove(Board b, int column, int recentRow, int pointerRow){
        if ((b.tile(column,pointerRow) == null) && (isEmpty(b,column,pointerRow,recentRow) == true)){
            return true;
        }
        return false;
    }

    /** Self-created Helper Function No. 4
     * This function help judge whether it is feasible to do a Merge move.
     * There are four conditions to realize Not-merge move:
     * 1. The target tile has the same value as the original tile;
     * 2. The target tile has not been merged before;
     * 3. It is empty between original tile and target tile.
     * 4. The original tile and the target tile are in exactly different positions.
     */
    public static boolean canMergeMove(Board b, int column, int recentRow, int pointerRow, List<Pair> hasBeenMerged) {

        /** Setup. */
        boolean ans = false;
        for (int i = 0; i < hasBeenMerged.size(); i++){
            if ((column == hasBeenMerged.get(i).key) && (pointerRow == hasBeenMerged.get(i).value)){
                ans = true;
            }else{
                ans = false;
            }
        }
        Tile recentTile = b.tile(column, recentRow);
        Tile pointerTile = b.tile(column, pointerRow);
        int recentValue;
        int pointerValue;
        if (recentTile != null) {
            recentValue = recentTile.value();
        } else {
            recentValue = 0;
        }
        if (pointerTile != null) {
            pointerValue = pointerTile.value();
        } else {
            pointerValue = 0;
        }

        /** Check all the conditions. */
        if ((pointerValue == recentValue) && (isEmpty(b, column, pointerRow, recentRow) == true) && (recentRow != pointerRow) && (ans == false)) {
            return true;
        }
        return false;
    }



    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        for (int i = 0; i < b.size(); i = i + 1) {
            for (int j = 0; j < b.size(); j = j + 1) {
                if (b.tile(i,j) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int i = 0; i < b.size(); i = i + 1) {
            for (int j = 0; j < b.size(); j = j + 1) {
                if ((b.tile(i,j) != null) && (b.tile(i,j).value() == MAX_PIECE)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */

    public static boolean hasAdjacentTiles(Board b) {
        for (int i = 0; i < b.size(); i = i + 1) {
            for (int j = 0; j < b.size(); j = j + 1) {
                if ((i + 1 != 4) && ((b.tile(i, j).value() == b.tile(i + 1, j).value()))){
                    return true;
                }
                else if ((i - 1 != -1) && ((b.tile(i, j).value() == b.tile(i - 1, j).value()))){
                    return true;
                }
                else if ((j + 1 != 4) && ((b.tile(i, j).value() == b.tile(i, j + 1).value()))){
                    return true;
                }
                else if ((j - 1 != -1) && ((b.tile(i, j).value() == b.tile(i, j - 1).value()))){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)==true){
            return true;
        }
        else if (hasAdjacentTiles(b)==true){
            return true;
        }
        return false;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
