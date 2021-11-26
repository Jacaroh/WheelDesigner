package wheeldesigner;

public class DefaultWheelRules implements WheelRules {
  Wheel currentWheel;

  public Wheel getWheel() {
    currentWheel = new Wheel(this, 1.5, 3.0, 8);
    return currentWheel;
  }

  public Wheel getWheel(double hubDiameter, double spokeLength, int numberOfSpokes) {
    currentWheel = new Wheel(this, hubDiameter, spokeLength, numberOfSpokes);
    return currentWheel;
  }

  public boolean isOKToChangeWheelDiameter(double hubDiameter, double nextWheelDiameter) {
    return nextWheelDiameter >= 4 * hubDiameter;
  }

  public boolean isOKToChangeHubDiameter(double wheelDiameter, double nextHubDiameter) {
    return nextHubDiameter <= 0.25 * wheelDiameter;
  }

  public boolean isOKToChangeNumberOfSpokes(int numberOfSpokes) {
    return numberOfSpokes >= 8 && numberOfSpokes <= 16;
  }

  @Override
  public void notifyHubDiameterChange() {
    currentWheel.adjustSpokesLength();
  }
}
