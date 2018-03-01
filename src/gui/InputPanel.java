package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class InputPanel extends JPanel implements ActionListener {

  /**
   * Randomly generated serial version id
   */
  private static final long serialVersionUID = 7603090258673682140L;

  GridPanel gridPanel;

  public InputPanel(GridPanel g) {
    super();

    setLayout(new MigLayout("wrap 3, gap 0 0"));

    this.gridPanel = g;

    for (int i = 1; i < 11; i++) {
      JButton b;
      String layoutText = "w 50lp, h 50lp";
      if (i < 10) {
        b = new JButton(Integer.toString(i));
      } else {
        b = new JButton("Erase");
        layoutText += ", span 2, grow";
      }
      b.setActionCommand(b.getName());
      b.addActionListener(this);
      b.setDefaultCapable(false);
      b.setFont(new Font(Style.FONT_NAME, Font.PLAIN, Style.INPUT_FONT_SIZE));
      add(b, layoutText);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "Erase") {
      gridPanel.placeNum(0);
    } else {
      gridPanel.placeNum(Integer.parseInt(e.getActionCommand()));
    }
  }

}
