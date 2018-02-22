package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
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
    frame.setSize(700, 700);

    fileChooser = new JFileChooser();
    FileFilter sudokuFileFilter = new FileNameExtensionFilter("Sudoku files (.sud)", "sud");
    fileChooser.setFileFilter(sudokuFileFilter);

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

    sudoku = new Sudoku();
    for (int i = 0; i < 81; i++) {
      sudoku.setCell(i / 9, i % 9, i % 10);
    }

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

  private void sudokuCheck() {
    // TODO temporary to allow me to manually do stuff
    System.out.println(sudoku.isGridSolved());
  }
}
