package wheeldesigner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class HubTest {
  WheelRules spyWheelRules;
  Wheel wheel;
  Hub hub;

  @BeforeEach
  public void setup() {
    spyWheelRules = Mockito.spy(WheelRules.class);
    wheel = spyWheelRules.getWheel();
    hub = wheel.getHub();
  }

  @Test
  public void wheelRulesCreatesWheelWithHubDiameter1 () {
    assertEquals(1.5, hub.getDiameter());
  }

  @Test
  public void wheelRulesChangesHubDiameterOnApproval() {
    when(spyWheelRules.isOKToChangeHubDiameter(7.5, 2)).thenReturn(true);

    hub.setDiameter(2, wheel.getDiameter());

    assertEquals(2, hub.getDiameter());
  }

  @Test
  public void wheelRulesDoesNotChangeHubDiameterOnDisapproval() {
    when(spyWheelRules.isOKToChangeHubDiameter(7.5, 2)).thenReturn(false);

    hub.setDiameter(2, wheel.getDiameter());

    assertEquals(1.5, hub.getDiameter());
  }

  @Test
  public void defaultWheelRulesWheelChangesHubDiameterOnApproval() {
    WheelRules defaultWheelRules = new DefaultWheelRules();
    wheel = defaultWheelRules.getWheel();
    hub = wheel.getHub();

    hub.setDiameter(1, wheel.getDiameter());

    assertEquals(1, hub.getDiameter());
  }

  @Test
  public void defaultWheelRulesWheelDoesNotChangeHubDiameterOnDisapproval() {
    WheelRules defaultWheelRules = new DefaultWheelRules();
    wheel = defaultWheelRules.getWheel();
    hub = wheel.getHub();

    hub.setDiameter(5, wheel.getDiameter());

    assertEquals(1.5, hub.getDiameter());
  }

  @Test
  public void wheelRulesHubDiameterChangeDisapprovalPropagatesRejection() {
    when(spyWheelRules.isOKToChangeHubDiameter(7.5, 4)).thenReturn(false);
    doThrow(new RuntimeException("ERROR: New hub size invalid")).when(spyWheelRules).isOKToChangeHubDiameter(7.5, 4);

    Exception exception = assertThrows(RuntimeException.class,() -> hub.setDiameter(4, wheel.getDiameter()));

    assertEquals("ERROR: New hub size invalid", exception.getMessage());
  }
}
