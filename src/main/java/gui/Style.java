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
  public static final Color SELECTED_BORDER_COLOR = Color.WHITE;

  // Cell types:
  // Unselected - Standard cell that can be changed
  // Unchangeable - Cell that can't be changed
  // Selected - Cell that is explicitly selected by the user
  // Adj_highlighted - Other cells in the same row, column and box as the selected cell
  // Num_highlighted - Other cells containing the same number as the selected cell
  // Invalid - Cells that conflict with other cells, i.e. two 4s in the same row

  // Cell background colors
  // Custom colors sourced from: htmlcolorcodes.com
  public static final Color UNSELECTED_COLOR = Color.WHITE;
  public static final Color SELECTED_COLOR = UNSELECTED_COLOR;
  public static final Color ADJ_HIGHLIGHTED_COLOR = new Color(224, 224, 224);
  public static final Color NUM_HIGHLIGHT_COLOR = Color.BLUE.brighter();
  public static final Color INVALID_COLOR = Color.RED;
  public static final Color UNCHANGEABLE_COLOR = new Color(189, 189, 189);
  public static final Color UNCHANGEABLE_SELECTED_COLOR = UNCHANGEABLE_COLOR;
  public static final Color UNCHANGEABLE_HIGHLIGHTED_COLOR = new Color(158, 158, 158);

  // Cell text colors
  public static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
  public static final Color NUM_HIGHLIGHT_TEXT_COLOR = Color.WHITE;
  public static final Color INVALID_TEXT_COLOR = Color.WHITE;

  // InputPanel specific stuff
  public static final int INPUT_FONT_SIZE = 20;
}
