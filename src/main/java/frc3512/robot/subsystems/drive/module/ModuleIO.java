package frc3512.robot.subsystems.drive.module;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface ModuleIO {
  public static class ModuleIOInputs implements LoggableInputs {
    public double drivePositionMeter = 0.0;
    public double driveVelocityMeterPerSec = 0.0;
    public double driveAppliedVolts = 0.0;
    public double[] driveCurrentAmps = new double[] {};
    public double[] driveTempCelcius = new double[] {};

    public double turnAbsolutePositionDegree = 0.0;
    public double turnAbsolutePositionOffset = 0.0;
    public double turnPositionDegree = 0.0;
    public double turnVelocityDegreePerSec = 0.0;
    public double turnAppliedVolts = 0.0;
    public double[] turnCurrentAmps = new double[] {};
    public double[] turnTempCelcius = new double[] {};

    public void toLog(LogTable table) {
      table.put("DrivePositionMeter", drivePositionMeter);
      table.put("DriveVelocityMeterPerSec", driveVelocityMeterPerSec);
      table.put("DriveAppliedVolts", driveAppliedVolts);
      table.put("DriveCurrentAmps", driveCurrentAmps);
      table.put("DriveTempCelcius", driveTempCelcius);

      table.put("TurnAbsolutePositionDegree", turnAbsolutePositionDegree);
      table.put("TurnPositionDegree", turnPositionDegree);
      table.put("TurnVelocityDegreePerSec", turnVelocityDegreePerSec);
      table.put("TurnAppliedVolts", turnAppliedVolts);
      table.put("TurnCurrentAmps", turnCurrentAmps);
      table.put("TurnTempCelcius", turnTempCelcius);
    }

    public void fromLog(LogTable table) {
      drivePositionMeter = table.getDouble("DrivePositionMeter", drivePositionMeter);
      driveVelocityMeterPerSec =
          table.getDouble("DriveVelocityMeterPerSec", driveVelocityMeterPerSec);
      driveAppliedVolts = table.getDouble("DriveAppliedVolts", driveAppliedVolts);
      driveCurrentAmps = table.getDoubleArray("DriveCurrentAmps", driveCurrentAmps);
      driveTempCelcius = table.getDoubleArray("DriveTempCelcius", driveTempCelcius);

      turnAbsolutePositionDegree =
          table.getDouble("TurnAbsolutePositionDegree", turnAbsolutePositionDegree);
      turnAbsolutePositionOffset =
          table.getDouble("TurnAbsolutePositionOffset", turnAbsolutePositionOffset);
      turnPositionDegree = table.getDouble("TurnPositionDegree", turnPositionDegree);
      turnVelocityDegreePerSec =
          table.getDouble("TurnVelocityDegreePerSec", turnVelocityDegreePerSec);
      turnAppliedVolts = table.getDouble("TurnAppliedVolts", turnAppliedVolts);
      turnCurrentAmps = table.getDoubleArray("TurnCurrentAmps", turnCurrentAmps);
      turnTempCelcius = table.getDoubleArray("TurnTempCelcius", turnTempCelcius);
    }
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(ModuleIOInputs inputs) {}

  /** Run the drive motor using the onboard PID controller. */
  public default void setDrivePID(double velocity) {}

  /** Run the drive motor using a percentage. */
  public default void setDrivePercent(double percent) {}

  /** Run the turn motor using the onboard PID controller. */
  public default void setTurnPID(double position) {}

  /** Set the turn encoder. */
  public default void setTurnEncoder(double position) {}

  /** Stop the motors. */
  public default void stop() {}
}
