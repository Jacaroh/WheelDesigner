package wheeldesigner;

public class Hub {
  private WheelRules wheelRules;
  private double diameter;

  public Hub(WheelRules initialWheelRules, double initialDiameter) {
    wheelRules = initialWheelRules;
    diameter = initialDiameter;
  }

  public void setDiameter(double nextDiameter, double wheelDiameter) {
    if (wheelRules.isOKToChangeHubDiameter(wheelDiameter, nextDiameter)){
      diameter = nextDiameter;
      wheelRules.notifyHubDiameterChange();
    }
  }

  public double getDiameter() {
    return diameter;
  }
}
