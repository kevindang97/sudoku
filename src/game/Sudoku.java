package game;

public class Sudoku {

  private int[][] grid;

  public Sudoku() {
    grid = new int[9][9];
  }

  /**
   * Returns a copy of the grid. A copy is used so the public can't grab a reference to the actual
   * private grid.
   * 
   * @return
   */
  public int[][] getGrid() {
    // going to have to clone a 2d array, so I can't use Arrays.cloneof()
    int[][] gridClone = new int[9][9];
    for (int i = 0; i < 9; i++) {
      System.arraycopy(grid[i], 0, gridClone[i], 0, 9);
    }
    return gridClone;
  }

  /**
   * Sets the value of a single cell on the grid
   * 
   * @param x x-coord of the cell
   * @param y y-coord of the cell
   * @param num number to be placed in the cell
   */
  public void setCell(int x, int y, int num) {
    if (num < 0 || num > 9) {
      throw new IllegalArgumentException("Invalid input for cell");
    }
    grid[x][y] = num;
  }

  public boolean isGridSolved() {
    for (int i = 0; i < 9; i++) {
      if (!(isRowSolved(i) && isColSolved(i) && isBlockSolved(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean isRowSolved(int row) {
    boolean[] nums = new boolean[10];
    for (int i = 0; i < 9; i++) {
      int cellNum = grid[row][i];
      if (cellNum == 0) {
        return false;
      } else if (nums[cellNum] == true) {
        return false;
      } else {
        nums[cellNum] = true;
      }
    }
    return true;
  }

  private boolean isColSolved(int col) {
    boolean[] nums = new boolean[10];
    for (int i = 0; i < 9; i++) {
      int cellNum = grid[col][i];
      if (cellNum == 0) {
        return false;
      } else if (nums[cellNum] == true) {
        return false;
      } else {
        nums[cellNum] = true;
      }
    }
    return true;
  }

  /**
   * Checks to see if a block (a 3x3 group of cells in the grid) contains all 9 unique numbers.
   * Blocks are number 1 to 9 from top left, row by row. So block 3 is the top right block, block 4
   * is the middle row left block, etc.
   * 
   * @param block
   * @return
   */
  private boolean isBlockSolved(int block) {
    // get the top left corner of the block
    int topLeftX = (block - 1) / 3;
    int topLeftY = (block % 3) * 3 - 2;

    boolean[] nums = new boolean[10];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int cellNum = grid[topLeftX + i][topLeftY + j];
        if (cellNum == 0) {
          return false;
        } else if (nums[cellNum] == true) {
          return false;
        } else {
          nums[cellNum] = true;
        }
      }
    }
    return true;
  }

}
