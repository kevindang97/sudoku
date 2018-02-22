package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import game.Sudoku;

public class CellLabel extends JLabel implements MouseListener {

  /**
   * Randomly generated serial version id
   */
  private static final long serialVersionUID = -6552405042955843824L;

  private static final String FONT_NAME = "Monospaced";
  private static final int FONT_SIZE = 30;
  private static final int BORDER_WIDTH = 1;
  private static final int BORDER_MULTIPLIER = 3;
  private static final Color UNSELECTED_COLOR = Color.WHITE;
  private static final Color SELECTED_COLOR = Color.CYAN;
  private static final Color UNCHANGEABLE_COLOR = Color.LIGHT_GRAY;

  private int x;
  private int y;
  private Sudoku sudoku;
  private GridPanel gridPanel;

  public CellLabel(int x, int y, Sudoku sudoku, GridPanel gridPanel) {
    super();
    this.x = x;
    this.y = y;
    this.sudoku = sudoku;
    this.gridPanel = gridPanel;
    addMouseListener(this);
    addKeyListener(gridPanel);

    if (sudoku.isCellChangeable(x, y)) {
      setBackground(UNSELECTED_COLOR);
    } else {
      setBackground(UNCHANGEABLE_COLOR);
    }
    setOpaque(true);
    setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
    setHorizontalAlignment(SwingConstants.CENTER);
    setPreferredSize(new Dimension(FONT_SIZE, FONT_SIZE));

    // draw borders
    if (x == 0 && y == 0) {
      // top left corner, draw all borders
      setBorder(BorderFactory.createMatteBorder(BORDER_WIDTH * BORDER_MULTIPLIER,
          BORDER_WIDTH * BORDER_MULTIPLIER, BORDER_WIDTH, BORDER_WIDTH, Color.BLACK));
    } else if (x == 0) {
      // leftmost column, draw all borders except top border
      setBorder(BorderFactory.createMatteBorder(0, BORDER_WIDTH * BORDER_MULTIPLIER,
          (y % 3 == 2) ? BORDER_WIDTH * BORDER_MULTIPLIER : BORDER_WIDTH, BORDER_WIDTH,
          Color.BLACK));
    } else if (y == 0) {
      // topmost row, draw all borders except left border
      setBorder(BorderFactory.createMatteBorder(BORDER_WIDTH * BORDER_MULTIPLIER, 0, BORDER_WIDTH,
          (x % 3 == 2) ? BORDER_WIDTH * BORDER_MULTIPLIER : BORDER_WIDTH, Color.BLACK));
    } else {
      // every other cell, draw only bottom and right borders
      setBorder(BorderFactory.createMatteBorder(0, 0,
          (y % 3 == 2) ? BORDER_WIDTH * BORDER_MULTIPLIER : BORDER_WIDTH,
          (x % 3 == 2) ? BORDER_WIDTH * BORDER_MULTIPLIER : BORDER_WIDTH, Color.BLACK));
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (sudoku.getCell(x, y) != 0) {
      setText(Integer.toString(sudoku.getCell(x, y)));
    } else {
      setText("");
    }

    if (!sudoku.isCellChangeable(x, y)) {
      setBackground(UNCHANGEABLE_COLOR);
    } else if (gridPanel.isCellSelected(x, y)) {
      setBackground(SELECTED_COLOR);
    } else {
      setBackground(UNSELECTED_COLOR);
    }
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    if (!sudoku.isCellChangeable(x, y) || gridPanel.isCellSelected(x, y)) {
      gridPanel.unselectCell();
    } else {
      gridPanel.setCellSelected(x, y);
      requestFocusInWindow();
    }
    repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}
}
