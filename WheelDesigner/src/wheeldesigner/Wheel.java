package wheeldesigner;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Wheel {
  private WheelRules wheelRules;
  private Hub hub;
  private double diameter;
  private List<Spoke> spokes;

  public Wheel(WheelRules initialWheelRules, double initialHubDiameter, double initialSpokeLength, int numberOfSpokes){
    wheelRules = initialWheelRules;
    hub = new Hub(initialWheelRules, initialHubDiameter);

    calculateDiameter(initialSpokeLength, initialHubDiameter);

    spokes = IntStream.range(0, numberOfSpokes)
      .mapToObj(index -> new Spoke(initialSpokeLength))
      .collect(toList());
  }

  public void calculateDiameter(double spokeLength, double hubDiameter) {
    diameter = (spokeLength * 2) + hubDiameter;
  }

  public void adjustSpokesLength() {
    for (Spoke spoke: spokes) {
      spoke.setSpoke((diameter - hub.getDiameter())/2);
    }
  }

  public void setDiameter(double nextDiameter) {
    if(wheelRules.isOKToChangeWheelDiameter(hub.getDiameter(), nextDiameter)) {
      diameter = nextDiameter;
      adjustSpokesLength();
    }
  }

  public void changeNumberOfSpokes(int nextNumberOfSpokes) {
    if (wheelRules.isOKToChangeNumberOfSpokes(nextNumberOfSpokes)) {
      double spokeLength = spokes.get(0).getLength();
      spokes = IntStream.range(0, nextNumberOfSpokes)
        .mapToObj(index -> new Spoke(spokeLength))
        .collect(toList());
    }
  }

  public double getDiameter() {
    return diameter;
  }

  public List<Spoke> getSpokes() {
    return spokes;
  }

  public Hub getHub() {
    return hub;
  }
}
