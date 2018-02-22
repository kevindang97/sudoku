package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import game.Sudoku;

public class CellLabel extends JLabel {

  /**
   * Randomly generated serial version id
   */
  private static final long serialVersionUID = -6552405042955843824L;

  private int x;
  private int y;
  private Sudoku sudoku;

  public CellLabel(int x, int y, Sudoku sudoku, int borderWidth) {
    super();
    this.x = x;
    this.y = y;
    this.sudoku = sudoku;

    setHorizontalAlignment(SwingConstants.CENTER);

    // draw borders
    if (x == 0 && y == 0) {
      // top left corner, draw all borders
      setBorder(BorderFactory.createMatteBorder(borderWidth * 2, borderWidth * 2, borderWidth,
          borderWidth, Color.BLACK));
    } else if (x == 0) {
      // leftmost column, draw all borders except top border
      setBorder(BorderFactory.createMatteBorder(0, borderWidth * 2,
          (y % 3 == 2) ? borderWidth * 2 : borderWidth, borderWidth, Color.BLACK));
    } else if (y == 0) {
      // topmost row, draw all borders except left border
      setBorder(BorderFactory.createMatteBorder(borderWidth * 2, 0, borderWidth,
          (x % 3 == 2) ? borderWidth * 2 : borderWidth, Color.BLACK));
    } else {
      // every other cell, draw only bottom and right borders
      setBorder(BorderFactory.createMatteBorder(0, 0, (y % 3 == 2) ? borderWidth * 2 : borderWidth,
          (x % 3 == 2) ? borderWidth * 2 : borderWidth, Color.BLACK));
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (sudoku.getCell(x, y) != 0) {
      setText(Integer.toString(sudoku.getCell(x, y)));
    } else {
      setText("");
    }
  }
}
