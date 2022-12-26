package frc3512.lib.logging;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;

/** Sets up a double value in NetworkTables with the option to be logged */
public class SpartanDoubleEntry {

  private DoubleTopic topic;
  private DoublePublisher pub;
  private DoubleSubscriber sub;
  private DoubleLogEntry log;
  double defaultValue = 0.0;
  boolean logged = false;
  DataLog logInstance = SpartanLogManager.getCurrentLog();

  public SpartanDoubleEntry(String name) {
    this(name, 0.0);
  }

  public SpartanDoubleEntry(String name, double defaultValue) {
    this(name, defaultValue, false);
  }

  public SpartanDoubleEntry(String name, double defaultValue, boolean logged) {
    this.defaultValue = defaultValue;
    this.logged = logged;

    pub = topic.publish();
    sub = topic.subscribe(defaultValue);
  }

  public void set(double value) {
    pub.set(value);
    if (SpartanLogManager.isCompetition()) log.append(value);
  }

  public double get() {
    var currValue = sub.get();
    return currValue;
  }
}
