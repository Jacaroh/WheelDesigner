package wheeldesigner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WheelTest {
  WheelRules spyWheelRules;
  Wheel wheel;

  @BeforeEach
  public void setup() {
    spyWheelRules = Mockito.spy(WheelRules.class);
    wheel = spyWheelRules.getWheel();
  }

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void wheelRulesCreatesWheelWithDiameter7pnt5() {
    assertEquals(7.5, wheel.getDiameter());
  }

  @Test
  public void wheelRulesChangesDiameterOnApproval() {
    when(spyWheelRules.isOKToChangeWheelDiameter(1.5, 5)).thenReturn(true);

    wheel.setDiameter(5);

    assertEquals(5, wheel.getDiameter());
  }

  @Test
  public void wheelRulesDoesNotChangeDiameterOnDisapproval() {
    when(spyWheelRules.isOKToChangeWheelDiameter(1.5, 5)).thenReturn(false);

    wheel.setDiameter(5);

    assertEquals(7.5, wheel.getDiameter());
  }

  @Test
  public void wheelRulesDiameterChangeDisapprovalPropagatesRejection() {
    when(spyWheelRules.isOKToChangeWheelDiameter(1.5, 5)).thenReturn(false);
    doThrow(new RuntimeException("ERROR: New diameter invalid")).when(spyWheelRules).isOKToChangeWheelDiameter(1.5, 5);

    Exception exception = assertThrows(RuntimeException.class, () -> wheel.setDiameter(5));

    assertEquals("ERROR: New diameter invalid", exception.getMessage());
  }

  @Test
  public void defaultWheelRulesChangesDiameterOnApproval() {
    WheelRules defaultWheelRules = new DefaultWheelRules();
    wheel = defaultWheelRules.getWheel();

    wheel.setDiameter(10);

    assertEquals(10, wheel.getDiameter());
  }

  @Test
  public void defaultWheelRulesDoesNotChangeDiameterOnDisapproval() {
    WheelRules defaultWheelRules = new DefaultWheelRules();
    wheel = defaultWheelRules.getWheel();

    wheel.setDiameter(4);

    assertEquals(7.5, wheel.getDiameter());
  }

  @Test
  public void wheelRulesCanCreateCustomWheelWithDiameter13() {
    wheel = spyWheelRules.getWheel(1, 6, 9);

    assertEquals(13, wheel.getDiameter());
  }
}