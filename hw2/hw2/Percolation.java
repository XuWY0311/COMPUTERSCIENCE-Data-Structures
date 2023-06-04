package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /** Part 1: Declarations. */
    private WeightedQuickUnionUF percolationGrid;
    private WeightedQuickUnionUF noBottomGrid;
    private boolean[][] openGrid;
    private int size;
    private int numberOfOpen;
    private int virtualTopSite;
    private int virtualBottomSite;

    /** Part 2: Assignment / Initiation. */
    public Percolation(int N) {
        /** Exception. */
        if (N <= 0) {
            throw new IllegalArgumentException("size N should be positive");
        }
        /** Initiate the percolation grid. */
        percolationGrid = new WeightedQuickUnionUF(N * N + 2);
        noBottomGrid = new WeightedQuickUnionUF(N * N + 1);
        size = N;
        numberOfOpen = 0;
        /** Initiate the open grid. */
        openGrid = new boolean[N][N];
        for (int i = 0; i <= N - 1; i++) {
            for (int j = 0; j <= N - 1; j++) {
                openGrid[i][j] = false;
            }
        }
        /** Set virtual sites. */
        virtualTopSite = N * N;
        virtualBottomSite = N * N + 1;
    }
    /** Part 3: Methods. */

    /** ✔Method 1: Open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (row < 0 | row > size - 1 | col < 0 | col > size - 1) {
            throw new IndexOutOfBoundsException("Open operation is outside its prescribed range");
        }
        if (!isOpen(row, col)) {
            openGrid[row][col] = true;
            numberOfOpen = numberOfOpen + 1;
            if (row - 1 >= 0 && isOpen(row - 1, col)) {
                percolationGrid.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                noBottomGrid.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row + 1 <= size - 1 && isOpen(row + 1, col)) {
                percolationGrid.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                noBottomGrid.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (col - 1 >= 0 && isOpen(row, col - 1)) {
                percolationGrid.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                noBottomGrid.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (col + 1 <= size - 1 && isOpen(row, col + 1)) {
                percolationGrid.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                noBottomGrid.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            if (row == 0) {
                percolationGrid.union(virtualTopSite, xyTo1D(row, col));
                noBottomGrid.union(virtualTopSite, xyTo1D(row, col));

            }
            if (row == size - 1) {
                percolationGrid.union(virtualBottomSite, xyTo1D(row, col));
            }
        }
    }
    /** ✔Method 2: Is the site (row, col) open?. */
    public boolean isOpen(int row, int col) {
        if (row < 0 | row > size - 1 | col < 0 | col > size - 1) {
            throw new IndexOutOfBoundsException("isOpen operation is outside its prescribed range");
        }
        return (openGrid[row][col]);
    }

    /** ✔Method 3: Is the site (row, col) full?. */
    public boolean isFull(int row, int col) {
        if (row < 0 | row > size - 1 | col < 0 | col > size - 1) {
            throw new IndexOutOfBoundsException("isFull operation is outside its prescribed range");
        }
        return (noBottomGrid.connected(virtualTopSite, xyTo1D(row, col)));
    }

    /** ✔Method 4: Number of open sites. */
    public int numberOfOpenSites() {
        return numberOfOpen;
    }

    /** ✔Method 5: Does the system percolate? */
    public boolean percolates() {
        return (percolationGrid.connected(virtualTopSite, virtualBottomSite));
    }

    /** ✔Method 6: Helper function to do matching. */
    private int xyTo1D(int r, int c) {
        return r * size + c;
    }
}

