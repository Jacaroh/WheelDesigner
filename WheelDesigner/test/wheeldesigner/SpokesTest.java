package wheeldesigner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SpokesTest {
  WheelRules spyWheelRules;
  Wheel wheel;

  @BeforeEach
  public void setup() {
    spyWheelRules = Mockito.spy(WheelRules.class);
    wheel = spyWheelRules.getWheel();
  }

  @Test
  public void wheelRulesCreatesWheelWith8Spokes() {
    assertEquals(8, wheel.getSpokes().size());
  }

  @Test
  public void wheelRulesCreatesSpokeOfCorrectLength() {
    assertEquals(3, wheel.getSpokes().get(0).getLength());
  }

  @Test
  public void successfullyIncreaseNumberOfSpokesInWheel() {
    when(spyWheelRules.isOKToChangeNumberOfSpokes(9)).thenReturn(true);

    wheel.changeNumberOfSpokes(9);

    assertEquals(9, wheel.getSpokes().size());
  }

  @Test
  public void successfullyReduceNumberOfSpokesInWheel() {
    when(spyWheelRules.isOKToChangeNumberOfSpokes(7)).thenReturn(true);

    wheel.changeNumberOfSpokes(7);

    assertEquals(7, wheel.getSpokes().size());
  }

  @Test
  public void defaultWheelRulesFailsToAdjustNumberOfSpokesInWheelBelow8() {
    WheelRules defaultWheelRules = new DefaultWheelRules();
    wheel = defaultWheelRules.getWheel();

    wheel.changeNumberOfSpokes(7);

    assertEquals(8, wheel.getSpokes().size());
  }

  @Test
  public void defaultWheelRulesFailsToAdjustNumberOfSpokesInWheelOver16() {
    WheelRules defaultWheelRules = new DefaultWheelRules();
    wheel = defaultWheelRules.getWheel();

    wheel.changeNumberOfSpokes(16);
    wheel.changeNumberOfSpokes(17);

    assertEquals(16, wheel.getSpokes().size());
  }

  @Test
  public void changeInWheelDiameterEffectsSpokeLengthChange() {
    when(spyWheelRules.isOKToChangeWheelDiameter(1.5, 8)).thenReturn(true);

    wheel.setDiameter(8);

    assertEquals(3.25, wheel.getSpokes().get(0).getLength());
  }

  @Test
  public void changeInHubDiameterEffectsSpokeLengthChangeInDefaultWheelRules() {
    WheelRules defaultWheelRules = new DefaultWheelRules();
    wheel = defaultWheelRules.getWheel();
    Hub hub = wheel.getHub();

    hub.setDiameter(0.5, wheel.getDiameter());

    assertEquals(3.5, wheel.getSpokes().get(0).getLength());
  }
}
