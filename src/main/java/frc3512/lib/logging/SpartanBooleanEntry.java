package frc3512.lib.logging;

import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;

/** Wrapper class around BooleanLogEntry for additional features. */
public class SpartanBooleanEntry implements SpartanLogEntry {

  private BooleanLogEntry log;
  private boolean logValue = false;
  private long logTimestamp = 0;
  private final DataLog logInstance = SpartanLogManager.getCurrentLog();

  public SpartanBooleanEntry(String name, long timestamp) {
    log = new BooleanLogEntry(logInstance, name, timestamp);
    SpartanLogManager.addEntry(this);
  }

  public SpartanBooleanEntry(String name, String metadata) {
    log = new BooleanLogEntry(logInstance, name, metadata);
    SpartanLogManager.addEntry(this);
  }

  public SpartanBooleanEntry(String name) {
    log = new BooleanLogEntry(logInstance, name);
    SpartanLogManager.addEntry(this);
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   * @param timestamp Time stamp (may be 0 to indicate now)
   */
  public void append(boolean value, long timestamp) {
    logValue = value;
    logTimestamp = timestamp;
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   */
  public void append(boolean value) {
    logValue = value;
    logTimestamp = 0;
  }

  @Override
  public void processEntry() {
    log.append(logValue, logTimestamp);
  }
}
