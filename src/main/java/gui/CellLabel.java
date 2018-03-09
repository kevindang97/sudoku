package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import game.Sudoku;

public class CellLabel extends JLabel implements MouseListener {

  /**
   * Randomly generated serial version id
   */
  private static final long serialVersionUID = -6552405042955843824L;

  private final int x;
  private final int y;
  private final int boxMinX;
  private final int boxMinY;
  private final int boxMaxX;
  private final int boxMaxY;
  private Sudoku sudoku;
  private GridPanel gridPanel;
  private Border border;
  private Border selectedBorder;

  public CellLabel(int x, int y, Sudoku sudoku, GridPanel gridPanel) {
    super();
    this.x = x;
    this.y = y;
    boxMinX = x / 3 * 3;
    boxMinY = y / 3 * 3;
    boxMaxX = boxMinX + 2;
    boxMaxY = boxMinY + 2;
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
    setFont(new Font(Style.FONT_NAME, Font.PLAIN, Style.CELL_FONT_SIZE));
    setHorizontalAlignment(SwingConstants.CENTER);
    setPreferredSize(new Dimension(Style.CELL_FONT_SIZE, Style.CELL_FONT_SIZE));

    // create borders
    if (x == 0 && y == 0) {
      // top left corner, draw all borders
      border = BorderFactory.createMatteBorder(Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER,
          Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER, Style.BORDER_WIDTH, Style.BORDER_WIDTH,
          Color.BLACK);
    } else if (x == 0) {
      // leftmost column, draw all borders except top border
      border = BorderFactory.createMatteBorder(0, Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER,
          (y % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Style.BORDER_WIDTH, Color.BLACK);
    } else if (y == 0) {
      // topmost row, draw all borders except left border
      border = BorderFactory.createMatteBorder(Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER, 0,
          Style.BORDER_WIDTH,
          (x % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Color.BLACK);
    } else {
      // every other cell, draw only bottom and right borders
      border = BorderFactory.createMatteBorder(0, 0,
          (y % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          (x % 3 == 2) ? Style.BORDER_WIDTH * Style.BORDER_MULTIPLIER : Style.BORDER_WIDTH,
          Color.BLACK);
    }

    selectedBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createCompoundBorder(
        BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Coord selectedCoord = gridPanel.getSelectedCoord();
    int[][] grid = sudoku.getGrid();
    int currentNum = grid[x][y];
    int selectedBoxMinX = (selectedCoord.x == -1) ? -1 : selectedCoord.x / 3 * 3;
    int selectedBoxMaxX = (selectedCoord.x == -1) ? -1 : selectedBoxMinX + 2;
    int selectedBoxMinY = (selectedCoord.y == -1) ? -1 : selectedCoord.y / 3 * 3;
    int selectedBoxMaxY = (selectedCoord.y == -1) ? -1 : selectedBoxMinY + 2;

    // determine cell background colour
    boolean changeable = sudoku.isCellChangeable(x, y);
    boolean selected = (x == selectedCoord.x && y == selectedCoord.y);
    boolean highlighted = (x == selectedCoord.x || y == selectedCoord.y || (x >= selectedBoxMinX
        && x <= selectedBoxMaxX && y >= selectedBoxMinY && y <= selectedBoxMaxY));

    if (changeable && selected && currentNum == 0) {
      setBackground(Style.UNSELECTED_COLOR);
    } else if (changeable && selected && currentNum != 0) {
      setBackground(Style.SELECTED_COLOR);
    } else if (!changeable && selected) {
      setBackground(Style.UNCHANGEABLE_SELECTED_COLOR);
    } else if (changeable && highlighted) {
      setBackground(Style.HIGHLIGHTED_COLOR);
    } else if (!changeable && highlighted) {
      setBackground(Style.UNCHANGEABLE_HIGHLIGHTED_COLOR);
    } else if (!changeable) {
      setBackground(Style.UNCHANGEABLE_COLOR);
    } else {
      setBackground(Style.UNSELECTED_COLOR);
    }

    // determine cell border
    if (changeable && selected && currentNum == 0) {
      setBorder(selectedBorder);
    } else {
      setBorder(border);
    }


    if (currentNum != 0) {
      setText(Integer.toString(sudoku.getCell(x, y)));
    } else {
      setText("");
    }

    // determine cell text color
    boolean invalid = false;
    for (int i = 0; i < 9; i++) {
      if ((i != x && grid[i][y] == currentNum) || (i != y && grid[x][i] == currentNum)) {
        invalid = true;
        break;
      }
    }
    for (int i = boxMinX; i <= boxMaxX; i++) {
      for (int j = boxMinY; j <= boxMaxY; j++) {
        if (i == x && j == y) {
          continue;
        }
        if (grid[i][j] == currentNum) {
          invalid = true;
          break;
        }
      }
      if (invalid) {
        break;
      }
    }

    int selectedNum = sudoku.getCell(selectedCoord.x, selectedCoord.y);
    Map<TextAttribute, Object> fontAttributes = new HashMap<TextAttribute, Object>();
    if (invalid) {
      fontAttributes.put(TextAttribute.FOREGROUND, Style.INVALID_TEXT_COLOR);
    } else if (selectedNum != 0 && selectedNum == sudoku.getCell(x, y)) {
      fontAttributes.put(TextAttribute.FOREGROUND, Style.SELECTED_TEXT_COLOR);
    } else {
      fontAttributes.put(TextAttribute.FOREGROUND, Style.DEFAULT_TEXT_COLOR);
    }
    setFont(getFont().deriveFont(fontAttributes));
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
    repaint();
  }

  @Override
  public void mouseReleased(MouseEvent e) {}
}
