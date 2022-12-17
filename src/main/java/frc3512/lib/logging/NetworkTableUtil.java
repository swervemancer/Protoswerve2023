package frc3512.lib.logging;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Utility class for creating NetworkTable entries.
 *
 * <p>Note: These values are not included in the DataLog.
 */
public class NetworkTableUtil {

  /**
   * Creates a NetworkTable entry for a double value.
   *
   * @param name Path of network table entry.
   * @param defaultValue The entry's initial value.
   */
  public static NetworkTableEntry makeDoubleEntry(String name, double defaultValue) {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTableEntry entry = instance.getEntry(name);
    entry.setDefaultDouble(defaultValue);

    return entry;
  }

  /**
   * Creates a NetworkTable entry with a default double value of 0.
   *
   * @param name Path of network table entry.
   */
  public static NetworkTableEntry makeDoubleEntry(String name) {
    return makeDoubleEntry(name, 0.0);
  }

  /**
   * Creates a NetworkTable entry for a boolean value.
   *
   * @param name Path of network table entry.
   * @param defaultValue The entry's initial value.
   */
  public static NetworkTableEntry makeBooleanEntry(String name, boolean defaultValue) {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTableEntry entry = instance.getEntry(name);
    entry.setDefaultBoolean(defaultValue);

    return entry;
  }

  /**
   * Creates a NetworkTable entry with a default double value of false.
   *
   * @param name Path of network table entry.
   */
  public static NetworkTableEntry makeBooleanEntry(String name) {
    return makeBooleanEntry(name, false);
  }

  /**
   * Creates a NetworkTable entry for a string value.
   *
   * @param name Path of network table entry.
   * @param defaultValue The entry's initial value.
   */
  public static NetworkTableEntry makeStringEntry(String name, String defaultValue) {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTableEntry entry = instance.getEntry(name);
    entry.setDefaultString(defaultValue);

    return entry;
  }

  /**
   * Creates a NetworkTable entry with a default empty String value.
   *
   * @param name Path of network table entry.
   */
  public static NetworkTableEntry makeStringEntry(String name) {
    return makeStringEntry(name, "");
  }

  /**
   * Creates a NetworkTable entry for a double array value.
   *
   * @param name Path of network table entry.
   * @param defaultValue The entry's initial value.
   */
  public static NetworkTableEntry makeDoubleArrayEntry(String name, double... defaultValue) {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTableEntry entry = instance.getEntry(name);
    entry.setDefaultDoubleArray(defaultValue);

    return entry;
  }

  /**
   * Creates a NetworkTable entry with a default value of an empty double array.
   *
   * @param name Path of network table entry.
   */
  public static NetworkTableEntry makeDoubleArrayEntry(String name) {
    double[] array = {};
    return makeDoubleArrayEntry(name, array);
  }
}
