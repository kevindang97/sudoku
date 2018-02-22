package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import game.Sudoku;
import game.SudokuListener;

public class GridPanel extends JPanel implements SudokuListener {

  /**
   * Randomly generated serial version ID
   */
  private static final long serialVersionUID = -3386505273632057302L;

  private JFrame frame;
  private JFileChooser fileChooser;
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
    sudoku.addListener(this);

    fileChooser = new JFileChooser();
    FileFilter sudokuFileFilter = new FileNameExtensionFilter("Sudoku files (.sud)", "sud");
    fileChooser.setFileFilter(sudokuFileFilter);

    // JFrame stuff
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 500);

    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

    JButton btnOpen = new JButton("Open");
    btnOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        fileOpen();
      }
    });
    menuBar.add(btnOpen);

    JButton btnCheck = new JButton("Check");
    btnCheck.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        sudokuCheck();
      }
    });
    menuBar.add(btnCheck);

    frame.getContentPane().add(this, BorderLayout.CENTER);
    setSize(700, 500);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
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

  private void fileOpen() {
    int fileChooserReturnValue = fileChooser.showDialog(frame, "Open");

    if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try (FileReader fileReader = new FileReader(file);) {
        sudoku.openFile(fileReader);
      } catch (IOException e) {
        // TODO proper error message
        e.printStackTrace();
      }
    }
  }

  private void sudokuCheck() {
    // TODO temporary to allow me to manually do stuff
    System.out.println(sudoku.isGridSolved());
  }

  @Override
  public void sudokuChanged() {
    repaint();
  }

}
