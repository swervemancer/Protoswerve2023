package frc3512.lib.logging;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.DataLogManager;
import frc3512.robot.Robot;

/** 
 * Wrapper class around the DataLogManager for additional features. 
 */
public class SpartanLogManager {

  private static boolean isCompetition = false;
  private static DataLog log = DataLogManager.getLog();

  private SpartanLogManager() {
    throw new AssertionError("No constructor needed!");
  }

  /**
   * Set whether to enable competition mode. This disables logging to free up network bandwith.
   *
   * @param isCompeting Whether or not your robot is competing at an offical event.
   */
  public static void setCompetitionMode(boolean isCompeting) {
    isCompetition = isCompeting;
  }

  /** Start logging (if running on real hardware). */
  public static void startLogging() {
    if (Robot.isReal()) {
      DataLogManager.logNetworkTables(false);
      DataLogManager.start();
    }
  }

  /**
   * Log a specified string text into the messages" entry. Also prints out to standard output.
   *
   * @param message Message text.
   */
  public static void logMessage(String message) {
    DataLogManager.log(message);
  }

  /**
   * Returns the DataLog object for creating custom log entries.
   *
   * @return Instance of the DataLog.
   */
  public static DataLog getCurrentLog() {
    return log;
  }

  /**
   * Returns if the robot is set to run in competition mode. Disables logging if thats the case.
   *
   * @return Whether or not competition mode is enabled.
   */
  public static boolean isCompetition() {
    return isCompetition;
  }
}
