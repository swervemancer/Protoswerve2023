package frc3512.lib.config;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.Pigeon2Configuration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;
import frc3512.robot.Constants;

/** Configuration for the CANCoders for the swerve drive modules. */
public final class CTREConfigs {
  public CANCoderConfiguration swerveCanCoderConfig;
  public Pigeon2Configuration pigeonConfig;

  public CTREConfigs() {
    swerveCanCoderConfig = new CANCoderConfiguration();
    pigeonConfig = new Pigeon2Configuration();

    /* Pigeon 2 Configuration */
    pigeonConfig.MountPoseYaw = 90.0;

    /* Swerve CANCoder Configuration */
    swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
    swerveCanCoderConfig.sensorDirection = Constants.Swerve.canCoderInvert;
    swerveCanCoderConfig.initializationStrategy =
        SensorInitializationStrategy.BootToAbsolutePosition;
    swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
  }
}
