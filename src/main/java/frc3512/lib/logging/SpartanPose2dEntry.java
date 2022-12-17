package frc3512.lib.logging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleArrayLogEntry;

public class SpartanPose2dEntry implements SpartanLogEntry {

  private Pose2d pose = new Pose2d();
  private DoubleArrayLogEntry log;
  private long logTimestamp = 0;
  private final DataLog logInstance = SpartanLogManager.getCurrentLog();

  public SpartanPose2dEntry(String name, long timestamp) {
    log = new DoubleArrayLogEntry(logInstance, name, timestamp);
    SpartanLogManager.addEntry(this);
  }

  public SpartanPose2dEntry(String name, String metadata) {
    log = new DoubleArrayLogEntry(logInstance, name, metadata);
    SpartanLogManager.addEntry(this);
  }

  public SpartanPose2dEntry(String name) {
    log = new DoubleArrayLogEntry(logInstance, name);
    SpartanLogManager.addEntry(this);
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   * @param timestamp Time stamp (may be 0 to indicate now)
   */
  public void append(Pose2d value, long timestamp) {
    pose = value;
    logTimestamp = timestamp;
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   */
  public void append(Pose2d value) {
    pose = value;
    logTimestamp = 0;
  }

  @Override
  public void processEntry() {
    log.append(
        new double[] {pose.getX(), pose.getY(), pose.getRotation().getDegrees()}, logTimestamp);
  }
}
