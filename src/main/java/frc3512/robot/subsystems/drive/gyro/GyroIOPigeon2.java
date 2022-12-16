package frc3512.robot.subsystems.drive.gyro;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.Pigeon2;
import frc3512.robot.Constants;

public class GyroIOPigeon2 implements GyroIO {

  private final Pigeon2 gyro;
  private final double[] xyzDps = new double[3];

  public GyroIOPigeon2() {
    switch (Constants.General.getRobot()) {
      case ROBOT_2022_REAL:
        gyro = new Pigeon2(Constants.Swerve.pigeonID);
        break;
      default:
        throw new RuntimeException("Invalid robot for GyroIOPigeon2");
    }
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    gyro.getRawGyro(xyzDps);
    inputs.connected = gyro.getLastError().equals(ErrorCode.OK);
    inputs.positionDegree = gyro.getYaw();
    inputs.velocityDegreePerSec = xyzDps[2];
  }

  @Override
  public void resetGyro() {
    gyro.setYaw(0);
  }
}
