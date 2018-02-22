package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import game.Sudoku;
import game.SudokuListener;

public class GridPanel extends JPanel implements SudokuListener, KeyListener {

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

    setLayout(new GridBagLayout());

    sudoku = s;
    sudoku.addListener(this);
    cells = new CellLabel[9][9];
    selectedX = -1;
    selectedY = -1;

    GridBagConstraints c = new GridBagConstraints();
    c.ipadx = 10;
    c.ipady = 10;

    // reversed x and y so that it creates cells row by row rather then column by column
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        cells[x][y] = new CellLabel(x, y, sudoku, this);
        c.gridx = x;
        c.gridy = y;
        add(cells[x][y], c);
      }
    }
  }

  public void setCellSelected(int x, int y) {
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

  public void unselectCell() {
    int previousX = selectedX;
    int previousY = selectedY;
    selectedX = -1;
    selectedY = -1;

    if (previousX != -1 && previousY != -1) {
      cells[previousX][previousY].repaint();
    }
  }

  public boolean isCellSelected(int x, int y) {
    return (selectedX == x) && (selectedY == y);
  }

  @Override
  public void sudokuChanged() {
    repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (selectedX == -1 || selectedY == -1) {
      return;
    }

    int keyCode = e.getKeyCode();
    if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
      int number = Integer.parseInt(KeyEvent.getKeyText(keyCode));
      sudoku.setCell(selectedX, selectedY, number);
      unselectCell();
    } else if (keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_BACK_SPACE
        || keyCode == KeyEvent.VK_DELETE) {
      sudoku.setCell(selectedX, selectedY, 0);
      unselectCell();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void keyTyped(KeyEvent e) {}

}
