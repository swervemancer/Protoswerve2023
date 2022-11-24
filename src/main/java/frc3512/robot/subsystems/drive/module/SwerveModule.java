package frc3512.robot.subsystems.drive.module;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc3512.lib.math.OnboardModuleState;
import frc3512.robot.Constants;
import frc3512.robot.subsystems.drive.module.ModuleIO.ModuleIOInputs;
import org.littletonrobotics.junction.Logger;

public class SwerveModule {
  public int moduleNumber;
  private Rotation2d lastAngle;

  private final ModuleIO moduleIO;
  private final ModuleIOInputs moduleInputs = new ModuleIOInputs();

  /**
   * Creates a new swerve module with NEO motors and a CTRE CANCoder.
   *
   * @param moduleNumber - Number of the module (0-3)
   * @param moduleIO - Module IO for the appropriate module
   */
  public SwerveModule(int moduleNumber, ModuleIO moduleIO) {
    this.moduleNumber = moduleNumber;
    this.moduleIO = moduleIO;

    lastAngle = getState().angle;
  }

  public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
    // Custom optimize command, since default WPILib optimize assumes continuous controller which
    // REV and CTRE are not
    desiredState = OnboardModuleState.optimize(desiredState, getState().angle);

    setAngle(desiredState);
    setSpeed(desiredState, isOpenLoop);
  }

  private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
    if (isOpenLoop) {
      double percentOutput = desiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed;
      moduleIO.setDrivePercent(percentOutput);
    } else {
      moduleIO.setDrivePID(desiredState.speedMetersPerSecond);
    }
  }

  private void setAngle(SwerveModuleState desiredState) {
    // Prevent rotating module if speed is less then 1%. Prevents jittering.
    Rotation2d angle =
        (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01))
            ? lastAngle
            : desiredState.angle;

    moduleIO.setTurnPID(angle.getDegrees());
    lastAngle = angle;
  }

  private Rotation2d getAngle() {
    return Rotation2d.fromDegrees(moduleInputs.turnPositionDegree);
  }

  public Rotation2d getCanCoder() {
    return Rotation2d.fromDegrees(moduleInputs.turnAbsolutePositionDegree);
  }

  public void stop() {
    moduleIO.stop();
  }

  public void resetToAbsolute() {
    double absolutePosition = getCanCoder().getDegrees() - moduleInputs.turnAbsolutePositionOffset;
    moduleIO.setTurnEncoder(absolutePosition);
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(moduleInputs.driveVelocityMeterPerSec, getAngle());
  }

  public void updateModule() {
    moduleIO.updateInputs(moduleInputs);
    Logger.getInstance().processInputs("Swerve/Mod " + moduleNumber, moduleInputs);
  }
}
