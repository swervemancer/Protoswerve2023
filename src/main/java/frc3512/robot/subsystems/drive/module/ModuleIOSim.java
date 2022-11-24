package frc3512.robot.subsystems.drive.module;

import edu.wpi.first.math.geometry.Rotation2d;

public class ModuleIOSim implements ModuleIO {

  private double velocity;
  private Rotation2d angle = Rotation2d.fromDegrees(0.0);

  @Override
  public void updateInputs(ModuleIOInputs inputs) {
    inputs.drivePositionMeter = inputs.drivePositionMeter + (velocity) * 0.02;
    inputs.driveVelocityMeterPerSec = velocity;
    inputs.driveAppliedVolts = 0.0;
    inputs.driveCurrentAmps = new double[] {};
    inputs.driveTempCelcius = new double[] {};

    inputs.turnAbsolutePositionDegree = 0.0;
    inputs.turnAbsolutePositionOffset = 0.0;
    inputs.turnPositionDegree = angle.getDegrees();
    inputs.turnVelocityDegreePerSec = 0.0;
    inputs.turnAppliedVolts = 0.0;
    inputs.turnCurrentAmps = new double[] {};
    inputs.turnTempCelcius = new double[] {};
  }

  @Override
  public void setDrivePID(double velocity) {
    this.velocity = velocity * 6.75;
  }

  @Override
  public void setDrivePercent(double percent) {
    velocity = percent * 6.75;
  }

  @Override
  public void setTurnPID(double position) {
    angle = Rotation2d.fromDegrees(position);
  }
}
