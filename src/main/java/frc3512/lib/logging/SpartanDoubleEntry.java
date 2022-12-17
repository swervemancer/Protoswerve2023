package frc3512.lib.logging;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;

/** Wrapper class around DoubleLogEntry for additional features. */
public class SpartanDoubleEntry implements SpartanLogEntry {

  private DoubleLogEntry log;
  private double logValue = 0.0;
  private long logTimestamp = 0;
  private final DataLog logInstance = SpartanLogManager.getCurrentLog();

  public SpartanDoubleEntry(String name, long timestamp) {
    log = new DoubleLogEntry(logInstance, name, timestamp);
    SpartanLogManager.addEntry(this);
  }

  public SpartanDoubleEntry(String name, String metadata) {
    log = new DoubleLogEntry(logInstance, name, metadata);
    SpartanLogManager.addEntry(this);
  }

  public SpartanDoubleEntry(String name) {
    log = new DoubleLogEntry(logInstance, name);
    SpartanLogManager.addEntry(this);
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   * @param timestamp Time stamp (may be 0 to indicate now)
   */
  public void append(double value, long timestamp) {
    logValue = value;
    logTimestamp = timestamp;
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   */
  public void append(double value) {
    logValue = value;
    logTimestamp = 0;
  }

  @Override
  public void processEntry() {
    log.append(logValue, logTimestamp);
  }
}
