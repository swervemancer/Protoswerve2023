package frc3512.robot.subsystems.drive.gyro;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface GyroIO {
  public static class GyroIOInputs implements LoggableInputs {
    public boolean connected = false;
    public double positionDegree = 0.0;
    public double velocityDegreePerSec = 0.0;

    public void toLog(LogTable table) {
      table.put("Connected", connected);
      table.put("PositionDegree", positionDegree);
      table.put("VelocityDegreePerSec", velocityDegreePerSec);
    }

    public void fromLog(LogTable table) {
      connected = table.getBoolean("Connected", connected);
      positionDegree = table.getDouble("PositionDegree", positionDegree);
      velocityDegreePerSec = table.getDouble("VelocityDegreePerSec", velocityDegreePerSec);
    }
  }

  public default void updateInputs(GyroIOInputs inputs) {}

  public default void resetGyro() {}
}
