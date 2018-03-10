package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import game.FinishListener;
import game.Sudoku;
import game.SudokuListener;
import net.miginfocom.swing.MigLayout;

public class GridPanel extends JPanel implements SudokuListener, FinishListener, KeyListener {

  /**
   * Randomly generated serial version id
   */
  private static final long serialVersionUID = -3101919069301561588L;

  private Sudoku sudoku;
  private CellLabel[][] cells;
  private List<Set<CellLabel>> numCells;
  private List<Set<CellLabel>> rowCells;
  private List<Set<CellLabel>> colCells;
  private List<Set<CellLabel>> boxCells;
  private List<List<Set<CellLabel>>> adjCells;

  private int selectedX;
  private int selectedY;

  public GridPanel(Sudoku s) {
    super();

    setLayout(new MigLayout("wrap 9, gap 0 0"));
    setBackground(Style.DEFAULT_BACKGROUND);

    sudoku = s;
    sudoku.addChangeListener(this);
    sudoku.addFinishListener(this);
    cells = new CellLabel[9][9];
    selectedX = -1;
    selectedY = -1;
    numCells = new ArrayList<Set<CellLabel>>();
    rowCells = new ArrayList<Set<CellLabel>>();
    colCells = new ArrayList<Set<CellLabel>>();
    boxCells = new ArrayList<Set<CellLabel>>();
    adjCells = new ArrayList<List<Set<CellLabel>>>();
    for (int i = 0; i < 9; i++) {
      numCells.add(new HashSet<CellLabel>());
      rowCells.add(new HashSet<CellLabel>());
      colCells.add(new HashSet<CellLabel>());
      boxCells.add(new HashSet<CellLabel>());
      adjCells.add(new ArrayList<Set<CellLabel>>());
      for (int j = 0; j < 9; j++) {
        adjCells.get(i).add(new HashSet<CellLabel>());
      }
    }

    // reversed x and y so that it creates cells row by row rather then column by column
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        cells[x][y] = new CellLabel(x, y, sudoku, this);
        rowCells.get(y).add(cells[x][y]);
        colCells.get(x).add(cells[x][y]);
        boxCells.get((x / 3) + (y / 3 * 3)).add(cells[x][y]);
        add(cells[x][y], "w 48lp, h 48lp");
      }
    }

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        Set<CellLabel> set = adjCells.get(i).get(j);
        set.addAll(rowCells.get(j));
        set.addAll(colCells.get(i));
        set.addAll(boxCells.get((i / 3) + (j / 3 * 3)));

        cells[i][j].setAdjacent(set);
      }
    }
  }

  public void setSelectedCell(int x, int y) {
    if (x < -1 || x > 8 || y < -1 || y > 8) {
      throw new IllegalArgumentException("x and y parameters must be >= -1 && <= 8");
    }

    selectedX = x;
    selectedY = y;

    sudokuChanged();
  }

  public void unselectCell() {
    int previousX = selectedX;
    int previousY = selectedY;
    selectedX = -1;
    selectedY = -1;

    if (previousX != -1 && previousY != -1) {
      sudokuChanged();
    }
  }

  public Coord getSelectedCoord() {
    return new Coord(selectedX, selectedY);
  }

  public Set<CellLabel> getRow(int i) {
    return rowCells.get(i);
  }

  public Set<CellLabel> getCol(int i) {
    return colCells.get(i);
  }

  public Set<CellLabel> getBox(int x, int y) {
    return boxCells.get((x / 3) + (y / 3 * 3));
  }

  public void placeNum(int num) {
    if (selectedX != -1 && selectedY != -1 && sudoku.isCellChangeable(selectedX, selectedY)) {
      int previousNum = sudoku.getCell(selectedX, selectedY);
      if (previousNum != num) {
        sudoku.setCell(selectedX, selectedY, num);
        numCells.get(previousNum - 1).remove(cells[selectedX][selectedY]);
        numCells.get(num - 1).add(cells[selectedX][selectedY]);
      }
    }
  }

  @Override
  public void sudokuChanged() {
    repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
      // handles top row of numbers
      int number = Integer.parseInt(KeyEvent.getKeyText(keyCode));
      placeNum(number);
    } else if (keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_BACK_SPACE
        || keyCode == KeyEvent.VK_DELETE || keyCode == KeyEvent.VK_NUMPAD0) {
      // handles unsetting a cell back to 0
      placeNum(0);
    } else {
      switch (keyCode) {
        case KeyEvent.VK_NUMPAD1:
          placeNum(1);
          break;
        case KeyEvent.VK_NUMPAD2:
          placeNum(2);
          break;
        case KeyEvent.VK_NUMPAD3:
          placeNum(3);
          break;
        case KeyEvent.VK_NUMPAD4:
          placeNum(4);
          break;
        case KeyEvent.VK_NUMPAD5:
          placeNum(5);
          break;
        case KeyEvent.VK_NUMPAD6:
          placeNum(6);
          break;
        case KeyEvent.VK_NUMPAD7:
          placeNum(7);
          break;
        case KeyEvent.VK_NUMPAD8:
          placeNum(8);
          break;
        case KeyEvent.VK_NUMPAD9:
          placeNum(9);
          break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_A:
          if (selectedX > 0) {
            setSelectedCell(selectedX - 1, selectedY);
          }
          break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_D:
          if (selectedX < 8) {
            setSelectedCell(selectedX + 1, selectedY);
          }
          break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_W:
          if (selectedY > 0) {
            setSelectedCell(selectedX, selectedY - 1);
          }
          break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_S:
          if (selectedY < 8) {
            setSelectedCell(selectedX, selectedY + 1);
          }
          break;
        case KeyEvent.VK_ESCAPE:
          setSelectedCell(-1, -1);
          break;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void sudokuFinished() {
    if (sudoku.isGridSolved()) {
      JOptionPane.showMessageDialog(this, "Congrats, you've won!", "Congratulations!",
          JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
