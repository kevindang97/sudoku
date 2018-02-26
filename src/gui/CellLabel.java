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
      setBackground(Style.UNSELECTED_COLOR);
    } else {
      setBackground(Style.UNCHANGEABLE_COLOR);
    }
    setOpaque(true);
    setFont(new Font(Style.FONT_NAME, Font.PLAIN, Style.FONT_SIZE));
    setHorizontalAlignment(SwingConstants.CENTER);
    setPreferredSize(new Dimension(Style.FONT_SIZE, Style.FONT_SIZE));

    // draw borders
    if (x == 0 && y == 0) {
      // top left corner, draw all borders
      setBorder(BorderFactory.createMatteBorder(Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER,
          Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER, Style.BORDER_WIDTH, Style.BORDER_WIDTH,
          Color.BLACK));
    } else if (x == 0) {
      // leftmost column, draw all borders except top border
      setBorder(BorderFactory.createMatteBorder(0, Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER,
          (y % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Style.BORDER_WIDTH, Color.BLACK));
    } else if (y == 0) {
      // topmost row, draw all borders except left border
      setBorder(BorderFactory.createMatteBorder(Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER, 0,
          Style.BORDER_WIDTH,
          (x % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Color.BLACK));
    } else {
      // every other cell, draw only bottom and right borders
      setBorder(BorderFactory.createMatteBorder(0, 0,
          (y % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          (x % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Color.BLACK));
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
      setBackground(Style.UNCHANGEABLE_COLOR);
    } else if (gridPanel.isCellSelected(x, y)) {
      setBackground(Style.SELECTED_COLOR);
    } else {
      setBackground(Style.UNSELECTED_COLOR);
    }
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    if (!sudoku.isCellChangeable(x, y) || gridPanel.isCellSelected(x, y)) {
      gridPanel.unselectCell();
    } else {
      gridPanel.setCellSelected(x, y);
      requestFocusInWindow();
    }
    repaint();
  }

  @Override
  public void mouseReleased(MouseEvent e) {}
}
