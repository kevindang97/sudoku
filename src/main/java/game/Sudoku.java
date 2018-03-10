package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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
   * @return A copy of the internally used 2D sudoku grid
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
   * Sets the value of a single cell on the grid.
   * 
   * @param x - x-coord of the cell
   * @param y - y-coord of the cell
   * @param num - number to be placed in the cell
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

  /**
   * Simple getter to determine if a cell can be changed or not.
   * 
   * @param x - x-coord of the cell
   * @param y - y-coord of the cell
   * @return true if the cell is changeable, false otherwise
   */
  public boolean isCellChangeable(int x, int y) {
    return changeable[x][y];
  }

  /**
   * Opens a file and reads its contents into a new sudoku grid. In the case of an error the
   * previous grid is preserved. The source file must contain at least 81 numbers, whitespace
   * separated. The first 81 characters are read into the grid. As a generic Reader is used other
   * sources can be used as well, such as a StringReader.
   * 
   * @param r - Reader source of the new sudoku grid, generally this is a fileReader or stringReader
   * @throws IOException
   */
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

  /**
   * Adds a new SudokuListener.
   * 
   * @param l - SudokuListener to be added
   */
  public void addChangeListener(SudokuListener l) {
    changeListeners.add(l);
    sudokuChanged();
  }

  /**
   * Removes a SudokuListener.
   * 
   * @param l - SudokuListener to be removed
   */
  public void removeChangeListener(SudokuListener l) {
    changeListeners.remove(l);
  }

  /**
   * Adds a new FinishListener.
   * 
   * @param l - FinishListener to be added
   */
  public void addFinishListener(FinishListener l) {
    finishListeners.add(l);
  }

  /**
   * Removes a FinishListener.
   * 
   * @param l - FinishListener to be removed
   */
  public void removeFinishListener(FinishListener l) {
    finishListeners.remove(l);
  }

  /**
   * Calls the SudokuListeners on the entire grid.
   */
  private void sudokuChanged() {
    for (SudokuListener l : changeListeners) {
      l.sudokuChanged();
    }

    if (cellsNotEmpty == 0) {
      sudokuFinished();
    }
  }

  /**
   * Calls the FinishListeners.
   */
  private void sudokuFinished() {
    for (FinishListener l : finishListeners) {
      l.sudokuFinished();
    }
  }

  /**
   * Checks the entire grid to see if it is validly solved.
   * 
   * @return true if the sudoku grid is validly solved
   */
  public boolean isGridSolved() {
    for (int i = 0; i < 9; i++) {
      if (!(isRowSolved(i) && isColSolved(i) && isBlockSolved(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks to see if an entire row is validly solved.
   * 
   * @param row - Row number to be checked. Valid input is: [0,8], where 0 is the topmost row and 8
   *        is the bottom-most row
   * @return
   */
  private boolean isRowSolved(int row) {
    if (row < 0 || row > 8) {
      throw new IllegalArgumentException("Invalid row number used: " + row);
    }

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

  /**
   * Checks to see if an entire column is validly solved.
   * 
   * @param col - Column number to be checked. Valid input is: [0,8], where 0 is the leftmost column
   *        and 8 is the rightmost column
   * @return
   */
  private boolean isColSolved(int col) {
    if (col < 0 || col > 8) {
      throw new IllegalArgumentException("Invalid col number used: " + col);
    }

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
   * Checks to see if a block (a 3x3 group of cells in the grid) is validly solved.
   * 
   * @param block - Block number to be checked. Valid input is: [0,8], where the blocks are numbered
   *        like so: <br>
   *        0 1 2 <br>
   *        3 4 5 <br>
   *        6 7 8
   * @return
   */
  private boolean isBlockSolved(int block) {
    if (block < 0 || block > 8) {
      throw new IllegalArgumentException("Invalid block number used: " + block);
    }


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
