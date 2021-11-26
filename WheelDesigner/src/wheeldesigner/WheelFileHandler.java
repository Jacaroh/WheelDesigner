package wheeldesigner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class WheelFileHandler {
  public Wheel loadWheelFromFile(String fileName, Wheel theWheel) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      line = br.readLine();
      String[] wheelDetails = line.split(" ");

      Class<?> wheelRulesClass = Class.forName(wheelDetails[0]);
      Constructor<?> wheelRulesConstructor;
      wheelRulesConstructor = wheelRulesClass.getConstructor();
      WheelRules theWheelRules = (WheelRules) wheelRulesConstructor.newInstance();

      theWheel = theWheelRules.getWheel(parseDouble(wheelDetails[1]), parseDouble(wheelDetails[2]), parseInt(wheelDetails[3]));
    } catch (Exception e) {}
    return theWheel;
  }

  public String exportString(Wheel theWheel) {
    return "wheeldesigner.DefaultWheelRules " + theWheel.getHub().getDiameter() + " " + theWheel.getSpokes().get(0).getLength() +
      " " + theWheel.getSpokes().size();
  }

  public boolean saveWheelToFile(String fileName, Wheel theWheel) {
    PrintWriter writer;
    try {
      writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
      writer.println(exportString(theWheel));
      writer.close();

      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
