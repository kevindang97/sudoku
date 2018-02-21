package gui;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import game.Sudoku;

public class GridPanel extends JPanel {

  /**
   * Randomly generated serial version ID
   */
  private static final long serialVersionUID = -3386505273632057302L;

  private JFrame frame;
  private int gridXOffset = 10;
  private int gridYOffset = 10;
  private int cellWidth = 50;

  private Sudoku sudoku;

  public static void main(String[] args) {
    GridPanel gridPanel = new GridPanel();
    gridPanel.frame.setVisible(true);
  }

  public GridPanel() {
    super();
    initialise();
  }

  private void initialise() {
    sudoku = new Sudoku();
    for (int i = 0; i < 81; i++) {
      sudoku.setCell(i / 9, i % 9, i % 10);
    }

    // JFrame stuff
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);

    frame.setContentPane(this);
  }

  public void paintComponent(Graphics g) {
    drawGrid(g);
    drawNums(g);
  }

  private void drawGrid(Graphics g) {
    int gridWidth = (cellWidth * 9) + 4;

    // horizontal lines
    int y = gridYOffset;
    for (int i = 0; i < 3; i++) {
      g.drawLine(gridXOffset, y, gridXOffset + gridWidth, y);
      y++;
      for (int j = 0; j < 3; j++) {
        g.drawLine(gridXOffset, y, gridXOffset + gridWidth, y);
        y += cellWidth;
      }
    }
    g.drawLine(gridXOffset, y, gridXOffset + gridWidth, y);
    g.drawLine(gridXOffset, y + 1, gridXOffset + gridWidth, y + 1);

    // vertical lines
    int x = gridXOffset;
    for (int i = 0; i < 3; i++) {
      g.drawLine(x, gridYOffset, x, gridYOffset + gridWidth);
      x++;
      for (int j = 0; j < 3; j++) {
        g.drawLine(x, gridYOffset, x, gridYOffset + gridWidth);
        x += cellWidth;
      }
    }
    g.drawLine(x, gridYOffset, x, gridYOffset + gridWidth);
    g.drawLine(x + 1, gridYOffset, x + 1, gridYOffset + gridWidth);
  }

  private void drawNums(Graphics g) {
    // set number size
    g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, cellWidth));

    // draw numbers
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (sudoku.getGrid()[i][j] != 0) {
          g.drawString(Integer.toString(sudoku.getGrid()[i][j]),
              gridXOffset + (cellWidth / 4) + (i / 3) + (i * cellWidth),
              gridYOffset - (cellWidth / 12) + (j / 3) + (j * cellWidth) + cellWidth);
        }
      }
    }
  }

}
