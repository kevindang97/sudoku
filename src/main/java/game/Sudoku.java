package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import game.FinishListener;
import game.SudokuListener;

public class Sudoku {

  private int[][] grid;
  private boolean[][] changeable;
  private int cellsNotEmpty;
  private List<SudokuListener> changeListeners;
  private List<FinishListener> finishListeners;

  public Sudoku() {
    grid = new int[9][9];
    changeable = new boolean[9][9];
    cellsNotEmpty = 81;

    changeListeners = new ArrayList<SudokuListener>();
    finishListeners = new ArrayList<FinishListener>();

    // initialise default values in the 2D arrays
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        grid[i][j] = 0;
        changeable[i][j] = true;
      }
    }
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

  public int getCell(int x, int y) {
    if (x < 0 || x > 8 || y < 0 || y > 8) {
      return -1;
    } else {
      return grid[x][y];
    }
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
      throw new IllegalArgumentException("Invalid input for cell, number must be >= 0 && <= 9");
    }
    if (!changeable[x][y]) {
      throw new IllegalArgumentException("This cell is not changeable");
    }

    int previous = grid[x][y];
    grid[x][y] = num;

    if (previous == 0 && num != 0) {
      // placing a new number in an empty cell
      cellsNotEmpty--;
    } else if (previous != 0 && num == 0) {
      // removing an existing number to make a cell empty
      cellsNotEmpty++;
    }

    if (previous != num) {
      sudokuChanged();
    }
  }

  public boolean isCellChangeable(int x, int y) {
    return changeable[x][y];
  }

  public void openFile(Reader r) throws IOException {
    BufferedReader br = new BufferedReader(r);

    List<Integer> inputNumbers = new ArrayList<Integer>();
    int position = 0;
    String line = br.readLine();
    while (line != null) {
      for (String s : line.split(" ")) {
        int i = Integer.parseInt(s);
        if (i < 0 || i > 9) {
          throw new IOException("Invalid number in file, all numbers must be >= 0 && <= 9");
        }
        inputNumbers.add(i);
        position++;
        if (position > 80) {
          break;
        }
      }
      if (position > 80) {
        break;
      }
      line = br.readLine();
    }

    if (inputNumbers.size() < 81) {
      throw new IOException("Invalid amount of numbers in file, must contain 81 numbers");
    }

    cellsNotEmpty = 81;
    for (int i = 0; i < 81; i++) {
      grid[i % 9][i / 9] = inputNumbers.get(i);
      if (inputNumbers.get(i) == 0) {
        changeable[i % 9][i / 9] = true;
      } else {
        changeable[i % 9][i / 9] = false;
        cellsNotEmpty--;
      }
    }
    sudokuChanged();
  }

  public void addChangeListener(SudokuListener l) {
    changeListeners.add(l);
    sudokuChanged();
  }

  public void removeChangeListener(SudokuListener l) {
    changeListeners.remove(l);
  }

  public void addFinishListener(FinishListener l) {
    finishListeners.add(l);
  }

  public void removeFinishListener(FinishListener l) {
    finishListeners.remove(l);
  }

  private void sudokuChanged() {
    for (SudokuListener l : changeListeners) {
      l.sudokuChanged();
    }

    if (cellsNotEmpty == 0) {
      sudokuFinished();
    }
  }

  private void sudokuFinished() {
    for (FinishListener l : finishListeners) {
      l.sudokuFinished();
    }
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
    int topLeftX = (block % 3) * 3;
    int topLeftY = (block / 3) * 3;

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