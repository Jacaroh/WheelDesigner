package wheeldesigner;

public interface WheelRules {

  default Wheel getWheel() {
    return new Wheel(this, 1.5, 3.0, 8);
  }

  default Wheel getWheel(double hubDiameter, double spokeLength, int numberOfSpokes) {
    return new Wheel(this, hubDiameter, spokeLength, numberOfSpokes);
  }

  boolean isOKToChangeWheelDiameter(double hubDiameter, double nextWheelDiameter);

  boolean isOKToChangeHubDiameter(double wheelDiameter, double nextHubDiameter);

  boolean isOKToChangeNumberOfSpokes(int numberOfSpokes);

  void notifyHubDiameterChange();
}
