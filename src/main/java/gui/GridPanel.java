package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

    // reversed x and y so that it creates cells row by row rather then column by column
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        cells[x][y] = new CellLabel(x, y, sudoku, this);
        add(cells[x][y], "w 40lp, h 40lp");
      }
    }
  }

  public void setSelectedCell(int x, int y) {
    if (x < -1 || x > 9 || y < -1 || y > 9) {
      throw new IllegalArgumentException("x and y parameters must be >= -1 && <= 9");
    }

    unselectCell();
    selectedX = x;
    selectedY = y;

    if (selectedX != -1 && selectedY != -1) {
      cells[selectedX][selectedY].repaint();
    }
  }

  public Coord getSelectedCoord() {
    return new Coord(selectedX, selectedY);
  }

  public void unselectCell() {
    int previousX = selectedX;
    int previousY = selectedY;
    selectedX = -1;
    selectedY = -1;

    if (previousX != -1 && previousY != -1) {
      cells[previousX][previousY].repaint();
    }
  }

  public void placeNum(int num) {
    if (selectedX != -1 && selectedY != -1 && sudoku.isCellChangeable(selectedX, selectedY)) {
      sudoku.setCell(selectedX, selectedY, num);
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
