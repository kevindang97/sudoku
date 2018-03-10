package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import game.Sudoku;

public class CellLabel extends JLabel implements MouseListener {

  /**
   * Randomly generated serial version id
   */
  private static final long serialVersionUID = -6552405042955843824L;

  private final int x;
  private final int y;
  private Sudoku sudoku;
  private GridPanel gridPanel;
  private Set<CellLabel> adjacent;
  private Border border;
  private Border selectedBorder;
  private Border unchangeableBorder;

  public CellLabel(int x, int y, Sudoku sudoku, GridPanel gridPanel) {
    super();
    this.x = x;
    this.y = y;
    this.sudoku = sudoku;
    this.gridPanel = gridPanel;
    adjacent = new HashSet<CellLabel>();
    addMouseListener(this);
    addKeyListener(gridPanel);

    if (sudoku.isCellChangeable(x, y)) {
      setBackground(Style.UNSELECTED_COLOR);
    } else {
      setBackground(Style.UNCHANGEABLE_COLOR);
    }
    setOpaque(true);
    setFont(new Font(Style.FONT_NAME, Font.PLAIN, Style.CELL_FONT_SIZE));
    setHorizontalAlignment(SwingConstants.CENTER);
    setPreferredSize(new Dimension(Style.CELL_FONT_SIZE, Style.CELL_FONT_SIZE));

    // create borders
    if (x == 0 && y == 0) {
      // top left corner, draw all borders
      border = BorderFactory.createMatteBorder(Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER,
          Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER, Style.BORDER_WIDTH, Style.BORDER_WIDTH,
          Style.BORDER_COLOR);
    } else if (x == 0) {
      // leftmost column, draw all borders except top border
      border = BorderFactory.createMatteBorder(0, Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER,
          (y % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Style.BORDER_WIDTH, Style.BORDER_COLOR);
    } else if (y == 0) {
      // topmost row, draw all borders except left border
      border = BorderFactory.createMatteBorder(Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER, 0,
          Style.BORDER_WIDTH,
          (x % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Style.BORDER_COLOR);
    } else {
      // every other cell, draw only bottom and right borders
      border = BorderFactory.createMatteBorder(0, 0,
          (y % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          (x % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Style.BORDER_COLOR);
    }

    selectedBorder = BorderFactory.createCompoundBorder(border,
        BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED,
                Style.SELECTED_BORDER_COLOR_HIGHLIGHT, Style.SELECTED_BORDER_COLOR_SHADOW),
            BorderFactory.createBevelBorder(BevelBorder.LOWERED,
                Style.SELECTED_BORDER_COLOR_HIGHLIGHT, Style.SELECTED_BORDER_COLOR_SHADOW)));

    unchangeableBorder =
        BorderFactory.createCompoundBorder(border,
            BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                Style.UNCHANGEABLE_BORDER_COLOR_HIGHLIGHT, Style.UNCHANGEABLE_BORDER_COLOR_SHADOW),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED,
                    Style.UNCHANGEABLE_BORDER_COLOR_HIGHLIGHT,
                    Style.UNCHANGEABLE_BORDER_COLOR_SHADOW)));
  }

  public int getNum() {
    return sudoku.getCell(x, y);
  }

  public void setAdjacent(Set<CellLabel> s) {
    adjacent = s;
  }

  private boolean isInvalid() {
    int currentNum = sudoku.getCell(x, y);

    if (currentNum == 0) {
      return false;
    }

    for (CellLabel c : adjacent) {
      if (c == this) {
        continue;
      }
      if (c.getNum() == currentNum) {
        return true;
      }
    }

    return false;
  }

  private int apple = 0;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    System.out.println("yo, from (" + x + ", " + y + "), I've been called : " + apple++ + " times");

    int[][] grid = sudoku.getGrid();
    int currentNum = grid[x][y];

    Coord selectedCoord = gridPanel.getSelectedCoord();
    int selectedNum = sudoku.getCell(selectedCoord.x, selectedCoord.y);
    int selectedBoxMinX = (selectedCoord.x == -1) ? -1 : selectedCoord.x / 3 * 3;
    int selectedBoxMaxX = (selectedCoord.x == -1) ? -1 : selectedBoxMinX + 2;
    int selectedBoxMinY = (selectedCoord.y == -1) ? -1 : selectedCoord.y / 3 * 3;
    int selectedBoxMaxY = (selectedCoord.y == -1) ? -1 : selectedBoxMinY + 2;

    // set cell background colour
    boolean changeable = sudoku.isCellChangeable(x, y);
    boolean selected = (x == selectedCoord.x && y == selectedCoord.y);
    boolean highlighted = (x == selectedCoord.x || y == selectedCoord.y || (x >= selectedBoxMinX
        && x <= selectedBoxMaxX && y >= selectedBoxMinY && y <= selectedBoxMaxY));
    boolean invalid = isInvalid();


    if (invalid) {
      if (changeable) {
        setBackground(Style.INVALID_COLOR);
      } else {
        setBackground(Style.UNCHANGEABLE_INVALID_COLOR);
      }
    } else if (selectedNum != 0 && selectedNum == currentNum) {
      if (changeable) {
        setBackground(Style.NUM_HIGHLIGHTED_COLOR);
      } else {
        setBackground(Style.UNCHANGEABLE_NUM_HIGHLIGHTED_COLOR);
      }
    } else if (changeable && selected && currentNum == 0) {
      setBackground(Style.UNSELECTED_COLOR);
    } else if (changeable && selected && currentNum != 0) {
      setBackground(Style.SELECTED_COLOR);
    } else if (!changeable && selected) {
      setBackground(Style.UNCHANGEABLE_SELECTED_COLOR);
    } else if (changeable && highlighted) {
      setBackground(Style.ADJ_HIGHLIGHTED_COLOR);
    } else if (!changeable && highlighted) {
      setBackground(Style.UNCHANGEABLE_ADJ_HIGHLIGHTED_COLOR);
    } else if (!changeable) {
      setBackground(Style.UNCHANGEABLE_COLOR);
    } else {
      setBackground(Style.UNSELECTED_COLOR);
    }

    // set cell text color
    if (invalid) {
      setForeground(Style.INVALID_TEXT_COLOR);
    } else if (selectedNum != 0 && selectedNum == sudoku.getCell(x, y)) {
      setForeground(Style.NUM_HIGHLIGHTED_TEXT_COLOR);
    } else {
      setForeground(Style.DEFAULT_TEXT_COLOR);
    }

    // set cell border
    if (changeable && selected) {
      setBorder(selectedBorder);
    } else if (!changeable && selected) {
      setBorder(unchangeableBorder);
    } else {
      setBorder(border);
    }

    // set cell text
    if (currentNum != 0) {
      setText(Integer.toString(sudoku.getCell(x, y)));
    } else {
      setText("");
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
    if (gridPanel.getSelectedCoord().x == x && gridPanel.getSelectedCoord().y == y) {
      gridPanel.unselectCell();
    } else {
      gridPanel.setSelectedCell(x, y);
      requestFocusInWindow();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {}
}
