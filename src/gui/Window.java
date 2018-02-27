package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import game.Sudoku;

public class Window {

  JFrame frame;
  JFileChooser fileChooser;
  GridPanel gridPanel;

  Sudoku sudoku;

  public static void main(String[] args) {
    new Window();
  }

  public Window() {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        initialise();
        frame.setVisible(true);
      }
    });
  }

  private void initialise() {
    frame = new JFrame("Sudoku");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setMinimumSize(new Dimension(Style.MIN_WINDOW_WIDTH, Style.MIN_WINDOW_HEIGHT));
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    fileChooser = new JFileChooser();
    FileFilter sudokuFileFilter = new FileNameExtensionFilter("Sudoku files (.sud)", "sud");
    fileChooser.setFileFilter(sudokuFileFilter);

    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

    JMenu menu = new JMenu("Menu");
    menuBar.add(menu);

    JMenuItem menuItemOpen = new JMenuItem("Open");
    menuItemOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        fileOpen();
      }
    });
    menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    menu.add(menuItemOpen);

    JMenuItem menuItemCheck = new JMenuItem("Check");
    menuItemCheck.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        sudokuCheck();
      }
    });
    menuItemCheck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    menu.add(menuItemCheck);

    JMenuItem menuItemQuit = new JMenuItem("Quit");
    menuItemQuit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        quit();
      }
    });
    menuItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
    menu.add(menuItemQuit);

    sudoku = new Sudoku();
    loadDefaultGrid();

    gridPanel = new GridPanel(sudoku);
    frame.add(gridPanel);
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

  private void loadDefaultGrid() {
    try {
      sudoku.openFile(
          new StringReader("0 6 0 3 0 0 8 0 4 " + "5 3 7 0 9 0 0 0 0 " + "0 4 0 0 0 6 3 0 7 "
              + "0 9 0 0 5 1 2 3 8 " + "0 0 0 0 0 0 0 0 0 " + "7 1 3 6 2 0 0 4 0 "
              + "3 0 6 4 0 0 0 1 0 " + "0 0 0 0 6 0 5 2 3 " + "1 0 2 0 0 9 0 8 0"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void sudokuCheck() {
    // TODO temporary to allow me to manually do stuff
    frame.repaint();
    System.out.println(sudoku.isGridSolved());
  }

  private void quit() {
    System.exit(0);
  }
}
