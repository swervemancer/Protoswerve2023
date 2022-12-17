package frc3512.lib.logging;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleArrayLogEntry;

public class SpartanDoubleArrayEntry implements SpartanLogEntry {

  private DoubleArrayLogEntry log;
  private double[] logValue = new double[] {};
  private long logTimestamp = 0;
  private final DataLog logInstance = SpartanLogManager.getCurrentLog();

  public SpartanDoubleArrayEntry(String name, long timestamp) {
    log = new DoubleArrayLogEntry(logInstance, name, timestamp);
    SpartanLogManager.addEntry(this);
  }

  public SpartanDoubleArrayEntry(String name, String metadata) {
    log = new DoubleArrayLogEntry(logInstance, name, metadata);
    SpartanLogManager.addEntry(this);
  }

  public SpartanDoubleArrayEntry(String name) {
    log = new DoubleArrayLogEntry(logInstance, name);
    SpartanLogManager.addEntry(this);
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   * @param timestamp Time stamp (may be 0 to indicate now)
   */
  public void append(double[] value, long timestamp) {
    logValue = value;
    logTimestamp = timestamp;
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   */
  public void append(double[] value) {
    logValue = value;
    logTimestamp = 0;
  }

  @Override
  public void processEntry() {
    log.append(logValue, logTimestamp);
  }
}
