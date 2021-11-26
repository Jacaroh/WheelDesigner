package wheeldesigner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class WheelFileHandlerTest {
  WheelRules spyWheelRules;
  Wheel wheel;
  WheelFileHandler spyWheelFileHandler;

  @BeforeEach
  public void setup() {
    spyWheelRules = Mockito.spy(WheelRules.class);
    wheel = spyWheelRules.getWheel();
    spyWheelFileHandler = Mockito.spy(WheelFileHandler.class);
  }

  @Test
  public void successfullyLoadWheelFromFile() {
    wheel = spyWheelFileHandler.loadWheelFromFile("TestWheelLoading.txt", wheel);

    assertEquals(13, wheel.getDiameter());
    assertEquals(1, wheel.getHub().getDiameter());
    assertEquals(6, wheel.getSpokes().get(0).getLength());
    assertEquals(9, wheel.getSpokes().size());
  }

  @Test
  public void loadWheelFromFileReturnsPreviousWheelOnFailure() {
    wheel = spyWheelFileHandler.loadWheelFromFile("brokenName", wheel);

    assertEquals(7.5, wheel.getDiameter());
    assertEquals(1.5, wheel.getHub().getDiameter());
    assertEquals(3, wheel.getSpokes().get(0).getLength());
    assertEquals(8, wheel.getSpokes().size());
  }

  @Test
  public void successfullySaveWheelToFile() {
    spyWheelFileHandler.saveWheelToFile("TestWheelSaving.txt", wheel);
    String line = "";

    try (BufferedReader br = new BufferedReader(new FileReader("TestWheelSaving.txt"))) {
      line = br.readLine();
      assertEquals("wheeldesigner.DefaultWheelRules 1.5 3.0 8", line);
    } catch(Exception e) {
      fail();
    }
  }

  @Test
  public void saveWheelToFileFailsGracefully() {
    boolean saved = spyWheelFileHandler.saveWheelToFile("", wheel);

    assertFalse(saved);
  }

  @Test
  public void successfullyExportWheelDataToString() {
    String results = spyWheelFileHandler.exportString(wheel);

    assertEquals("wheeldesigner.DefaultWheelRules 1.5 3.0 8", results);
  }
}
