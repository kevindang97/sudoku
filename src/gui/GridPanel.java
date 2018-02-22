package gui;

import java.awt.GridLayout;
import javax.swing.JPanel;
import game.Sudoku;
import game.SudokuListener;

public class GridPanel extends JPanel implements SudokuListener {

  /**
   * Randomly generated serial version id
   */
  private static final long serialVersionUID = -3101919069301561588L;

  private Sudoku sudoku;
  private CellLabel[][] cells;

  private int borderWidth = 1;

  public GridPanel(Sudoku sudoku) {
    super();
    this.sudoku = sudoku;
    this.sudoku.addListener(this);
    setLayout(new GridLayout(9, 9));

    cells = new CellLabel[9][9];

    // reversed x and y so that it creates cells row by row rather then column by column
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        cells[x][y] = new CellLabel(x, y, sudoku, borderWidth);
        add(cells[x][y]);
      }
    }
  }

  @Override
  public void sudokuChanged() {
    repaint();
  }

}
