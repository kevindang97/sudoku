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
  // Custom colors sourced from: htmlcolorcodes.com
  public static final int CELL_FONT_SIZE = 30;
  public static final int BORDER_WIDTH = 1;
  public static final int BORDER_MULTIPLIER = 3;
  public static final Color UNSELECTED_COLOR = Color.WHITE;
  public static final Color HIGHLIGHTED_COLOR = new Color(144, 202, 249);
  public static final Color SELECTED_COLOR = new Color(100, 181, 246);
  public static final Color UNCHANGEABLE_COLOR = Color.LIGHT_GRAY;
  public static final Color UNCHANGEABLE_HIGHLIGHTED_COLOR = new Color(33, 150, 243);
  public static final Color UNCHANGEABLE_SELECTED_COLOR = new Color(25, 118, 210);
  public static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
  public static final Color SELECTED_TEXT_COLOR = new Color(255, 255, 0);
  public static final Color INVALID_TEXT_COLOR = Color.RED;

  // InputPanel specific stuff
  public static final int INPUT_FONT_SIZE = 20;
}
