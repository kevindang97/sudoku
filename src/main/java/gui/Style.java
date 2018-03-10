package gui;

import java.awt.Color;
import javax.swing.UIManager;

public class Style {
  // General stuff
  public static final String FONT_NAME = "Monospaced";

  // Frame specific stuff
  public static final int MIN_WINDOW_WIDTH = 500;
  public static final int MIN_WINDOW_HEIGHT = 500;

  // GridPanel specific stuff
  public static final Color DEFAULT_BACKGROUND = UIManager.getColor("InternalFrame.background");
  public static final Color WIN_BACKGROUND = Color.GREEN;

  // Cell specific stuff
  public static final int CELL_FONT_SIZE = 30;
  public static final int BORDER_WIDTH = 1;
  public static final int BORDER_MULTIPLIER = 3;
  public static final Color BORDER_COLOR = Color.BLACK;
  public static final Color SELECTED_BORDER_COLOR_HIGHLIGHT = Color.WHITE;
  public static final Color SELECTED_BORDER_COLOR_SHADOW = Color.GRAY;
  public static final Color UNCHANGEABLE_BORDER_COLOR_HIGHLIGHT = Color.GRAY.darker();
  public static final Color UNCHANGEABLE_BORDER_COLOR_SHADOW = Color.BLACK;

  // Cell types:
  // Unselected - Standard cell that can be changed
  // Unchangeable - Cell that can't be changed
  // Selected - Cell that is explicitly selected by the user
  // Adj_highlighted - Other cells in the same row, column and box as the selected cell
  // Num_highlighted - Other cells containing the same number as the selected cell
  // Invalid - Cells that conflict with other cells, i.e. two 4s in the same row

  // Cell background colors
  // Custom colors sourced from: https://htmlcolorcodes.com/color-chart/
  public static final Color UNSELECTED_COLOR = Color.WHITE;
  public static final Color SELECTED_COLOR = UNSELECTED_COLOR;
  public static final Color ADJ_HIGHLIGHTED_COLOR = new Color(224, 224, 224);
  public static final Color NUM_HIGHLIGHTED_COLOR = new Color(13, 71, 161);
  public static final Color INVALID_COLOR = new Color(255, 0, 0);
  public static final Color UNCHANGEABLE_COLOR = new Color(189, 189, 189);
  public static final Color UNCHANGEABLE_SELECTED_COLOR = UNCHANGEABLE_COLOR;
  public static final Color UNCHANGEABLE_ADJ_HIGHLIGHTED_COLOR = new Color(158, 158, 158);
  public static final Color UNCHANGEABLE_NUM_HIGHLIGHTED_COLOR = new Color(0, 0, 180);
  public static final Color UNCHANGEABLE_INVALID_COLOR = new Color(180, 0, 0);

  // Cell text colors
  public static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
  public static final Color NUM_HIGHLIGHTED_TEXT_COLOR = Color.WHITE;
  public static final Color INVALID_TEXT_COLOR = Color.WHITE;

  // InputPanel specific stuff
  public static final int INPUT_FONT_SIZE = 20;
}
