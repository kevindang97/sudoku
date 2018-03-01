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
  public static final Color UNSELECTED_COLOR = Color.WHITE;
  public static final Color SELECTED_COLOR = Color.CYAN;
  public static final Color UNCHANGEABLE_COLOR = Color.LIGHT_GRAY;

  // InputPanel specific stuff
  public static final int INPUT_FONT_SIZE = 20;
}
