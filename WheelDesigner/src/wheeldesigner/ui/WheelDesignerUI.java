package wheeldesigner.ui;

import wheeldesigner.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class WheelDesignerUI extends JFrame {
  static WheelRules wheelRules;
  static Wheel wheel;
  static Hub hub;
  static List<Spoke> spokes;
  static WheelFileHandler wheelFileHandler;

  static JFrame frame;
  static JFrame errorFrame = new JFrame("ERROR");
  static JPanel wheelDataPanel;
  static JPanel wheelButtonsPanel;
  static DefaultTableModel wheelData = new DefaultTableModel(5, 2);
  static JTable wheelTable = new JTable(wheelData) {
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  };

  static JButton wheelDiameterButton = new JButton("Edit Wheel Diameter");
  static JButton hubDiameterButton = new JButton("Edit Hub Diameter");
  static JButton numberOfSpokesButton = new JButton("Edit Number of Spokes");
  static JButton loadWheelButton = new JButton("Load Wheel");
  static JButton saveWheelButton = new JButton("Save Wheel");

  static List<String> wheelIndex = Arrays.asList("Wheel Diameter", "Hub Diameter", "Spoke Length", "Number of Spokes", "Spoke Angle");
  static List<JButton> wheelButtons = Arrays.asList(wheelDiameterButton, hubDiameterButton, numberOfSpokesButton, loadWheelButton, saveWheelButton);

  public static String calculateSpokeAngle(int numberOfSpokes) {
    return Integer.toString(360 / numberOfSpokes) + (char)176;
  }

  public static void loadWheelInTable() {
    wheelData.setValueAt(wheel.getDiameter(), 0, 1);
    wheelData.setValueAt(hub.getDiameter(), 1, 1);
    wheelData.setValueAt(spokes.get(0).getLength(), 2, 1);
    wheelData.setValueAt(spokes.size(), 3, 1);
    wheelData.setValueAt(calculateSpokeAngle(spokes.size()), 4, 1);
    wheelData.fireTableDataChanged();
  }

  public static double getButtonInput(String variableName) {
    String input = JOptionPane.showInputDialog("Enter new " + variableName + ":");
    try {
      return Double.parseDouble(input);
    } catch(NumberFormatException nfe) {
      JOptionPane.showMessageDialog(errorFrame, "Input cannot be converted to number.");
    }
    return -1;
  }

  public static void displayErrorOrRefreshTable(double first, double second) {
    if(first != second){
       JOptionPane.showMessageDialog(errorFrame, "Wheel Rules violated; input disregarded.");
    }
    else {
      loadWheelInTable();
    }
  }

  public static void buttonSetup() {
    wheelDiameterButton.addActionListener(e -> {
      double nextWheelDiameter = getButtonInput("wheel diameter");
      wheel.setDiameter(nextWheelDiameter);
      displayErrorOrRefreshTable(wheel.getDiameter(), nextWheelDiameter);
    });

    hubDiameterButton.addActionListener(e -> {
      double nextHubDiameter = getButtonInput("hub diameter");
      hub.setDiameter(nextHubDiameter, wheel.getDiameter());
      displayErrorOrRefreshTable(hub.getDiameter(), nextHubDiameter);
    });

    numberOfSpokesButton.addActionListener(e -> {
      String input = JOptionPane.showInputDialog("Enter new number of spokes:");
      int nextNumberOfSpokes = -1;
      try {
        nextNumberOfSpokes = Integer.parseInt(input);
      } catch(NumberFormatException nfe) {
        JOptionPane.showMessageDialog(errorFrame, "Input cannot be converted to number.");
      }

      wheel.changeNumberOfSpokes(nextNumberOfSpokes);
      spokes = wheel.getSpokes();
      displayErrorOrRefreshTable(spokes.size(), nextNumberOfSpokes);
    });

    loadWheelButton.addActionListener(e -> {
      String input = JOptionPane.showInputDialog("Enter filename to load:");
      wheel = wheelFileHandler.loadWheelFromFile(input, wheel);
      hub = wheel.getHub();
      spokes = wheel.getSpokes();
      loadWheelInTable();
    });

    saveWheelButton.addActionListener(e -> {
      String input = JOptionPane.showInputDialog("Enter filename to save to:");
      if(!wheelFileHandler.saveWheelToFile(input, wheel)) {
        JOptionPane.showMessageDialog(errorFrame, "Failed to save to file: " + input);
      }
    });
  }

  public static void panelSetup() {
    wheelTable.setRowHeight(30);
    wheelTable.getColumnModel().getColumn(0).setPreferredWidth(200);
    wheelTable.getColumnModel().getColumn(1).setPreferredWidth(200);

    for(int count = 0; count < wheelIndex.size(); count++) {
      wheelData.setValueAt(wheelIndex.get(count), count, 0);
    }
    loadWheelInTable();
    wheelDataPanel.add(wheelTable);

    for(var button : wheelButtons) {
      wheelButtonsPanel.add(button);
    }
  }

  public static void frameSetup() {
    Container pane = frame.getContentPane();
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    frame.add(wheelDataPanel);
    frame.add(wheelButtonsPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 300);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    frame = new JFrame("Wheel Designer");
    wheelDataPanel = new JPanel();
    wheelButtonsPanel = new JPanel();

    wheelRules = new DefaultWheelRules();
    wheel = wheelRules.getWheel();
    hub = wheel.getHub();
    spokes = wheel.getSpokes();
    wheelFileHandler = new WheelFileHandler();

    buttonSetup();
    panelSetup();
    frameSetup();
  }
}
