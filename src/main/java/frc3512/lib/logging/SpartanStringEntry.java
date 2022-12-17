package frc3512.lib.logging;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.StringLogEntry;

/** Wrapper class around StringLogEntry for additional features. */
public class SpartanStringEntry implements SpartanLogEntry {

  private StringLogEntry log;
  private String logValue = "";
  private long logTimestamp = 0;
  private final DataLog logInstance = SpartanLogManager.getCurrentLog();

  public SpartanStringEntry(String name, long timestamp) {
    log = new StringLogEntry(logInstance, name, timestamp);
    SpartanLogManager.addEntry(this);
  }

  public SpartanStringEntry(String name, String metadata) {
    log = new StringLogEntry(logInstance, name, metadata);
    SpartanLogManager.addEntry(this);
  }

  public SpartanStringEntry(String name) {
    log = new StringLogEntry(logInstance, name);
    SpartanLogManager.addEntry(this);
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   * @param timestamp Time stamp (may be 0 to indicate now)
   */
  public void append(String value, long timestamp) {
    logValue = value;
    logTimestamp = timestamp;
  }

  /**
   * Appends a record to the log.
   *
   * @param value Value to record
   */
  public void append(String value) {
    logValue = value;
    logTimestamp = 0;
  }

  @Override
  public void processEntry() {
    log.append(logValue, logTimestamp);
  }
}
