package frc3512.robot.subsystems.drive.module;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotController;
import frc3512.lib.config.SwerveModuleConstants;
import frc3512.lib.util.CANCoderUtil;
import frc3512.lib.util.CANCoderUtil.CANCoderUsage;
import frc3512.lib.util.CANSparkMaxUtil;
import frc3512.lib.util.CANSparkMaxUtil.Usage;
import frc3512.robot.Constants;
import frc3512.robot.Robot;

public class ModuleIOSparkMAX implements ModuleIO {

  private SwerveModuleConstants moduleConstants;
  private Rotation2d angleOffset;

  private CANSparkMax angleMotor;
  private CANSparkMax driveMotor;

  private RelativeEncoder driveEncoder;
  private RelativeEncoder integratedAngleEncoder;
  private CANCoder angleEncoder;

  private final SparkMaxPIDController driveController;
  private final SparkMaxPIDController angleController;

  private final SimpleMotorFeedforward feedforward =
      new SimpleMotorFeedforward(
          Constants.Swerve.driveKS, Constants.Swerve.driveKV, Constants.Swerve.driveKA);

  public ModuleIOSparkMAX(int index) {
    switch (Constants.General.getRobot()) {
      case ROBOT_2022_REAL:
        switch (index) {
          case 0:
            moduleConstants = Constants.Swerve.Mod0.constants;
            angleOffset = moduleConstants.angleOffset;

            /* Angle Encoder Config */
            angleEncoder = new CANCoder(moduleConstants.cancoderID);
            configAngleEncoder();

            /* Angle Motor Config */
            angleMotor = new CANSparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
            integratedAngleEncoder = angleMotor.getEncoder();
            angleController = angleMotor.getPIDController();
            configAngleMotor();

            /* Drive Motor Config */
            driveMotor = new CANSparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
            driveEncoder = driveMotor.getEncoder();
            driveController = driveMotor.getPIDController();
            configDriveMotor();

            break;
          case 1:
            moduleConstants = Constants.Swerve.Mod1.constants;
            angleOffset = moduleConstants.angleOffset;

            /* Angle Encoder Config */
            angleEncoder = new CANCoder(moduleConstants.cancoderID);
            configAngleEncoder();

            /* Angle Motor Config */
            angleMotor = new CANSparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
            integratedAngleEncoder = angleMotor.getEncoder();
            angleController = angleMotor.getPIDController();
            configAngleMotor();

            /* Drive Motor Config */
            driveMotor = new CANSparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
            driveEncoder = driveMotor.getEncoder();
            driveController = driveMotor.getPIDController();
            configDriveMotor();

            break;
          case 2:
            moduleConstants = Constants.Swerve.Mod2.constants;
            angleOffset = moduleConstants.angleOffset;

            /* Angle Encoder Config */
            angleEncoder = new CANCoder(moduleConstants.cancoderID);
            configAngleEncoder();

            /* Angle Motor Config */
            angleMotor = new CANSparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
            integratedAngleEncoder = angleMotor.getEncoder();
            angleController = angleMotor.getPIDController();
            configAngleMotor();

            /* Drive Motor Config */
            driveMotor = new CANSparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
            driveEncoder = driveMotor.getEncoder();
            driveController = driveMotor.getPIDController();
            configDriveMotor();

            break;

          case 3:
            moduleConstants = Constants.Swerve.Mod3.constants;
            angleOffset = moduleConstants.angleOffset;

            /* Angle Encoder Config */
            angleEncoder = new CANCoder(moduleConstants.cancoderID);
            configAngleEncoder();

            /* Angle Motor Config */
            angleMotor = new CANSparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
            integratedAngleEncoder = angleMotor.getEncoder();
            angleController = angleMotor.getPIDController();
            configAngleMotor();

            /* Drive Motor Config */
            driveMotor = new CANSparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
            driveEncoder = driveMotor.getEncoder();
            driveController = driveMotor.getPIDController();
            configDriveMotor();

            break;
          default:
            throw new RuntimeException("Invalid module index for ModuleIOSparkMAX");
        }
        break;
      default:
        throw new RuntimeException("Invalid robot for ModuleIOSparkMAX");
    }
  }

  private void configAngleEncoder() {
    angleEncoder.configFactoryDefault();
    CANCoderUtil.setCANCoderBusUsage(angleEncoder, CANCoderUsage.kMinimal);
    angleEncoder.configAllSettings(Robot.ctreConfigs.swerveCanCoderConfig);
  }

  private void configAngleMotor() {
    angleMotor.restoreFactoryDefaults();
    CANSparkMaxUtil.setCANSparkMaxBusUsage(angleMotor, Usage.kPositionOnly);
    angleMotor.setSmartCurrentLimit(Constants.Swerve.angleContinuousCurrentLimit);
    angleMotor.setInverted(Constants.Swerve.angleInvert);
    angleMotor.setIdleMode(Constants.Swerve.angleNeutralMode);
    integratedAngleEncoder.setPositionConversionFactor(Constants.Swerve.angleConversionFactor);
    angleController.setP(Constants.Swerve.angleKP);
    angleController.setI(Constants.Swerve.angleKI);
    angleController.setD(Constants.Swerve.angleKD);
    angleController.setFF(Constants.Swerve.angleKFF);
    angleMotor.enableVoltageCompensation(Constants.General.voltageComp);
    angleMotor.burnFlash();
  }

  private void configDriveMotor() {
    driveMotor.restoreFactoryDefaults();
    CANSparkMaxUtil.setCANSparkMaxBusUsage(driveMotor, Usage.kAll);
    driveMotor.setSmartCurrentLimit(Constants.Swerve.driveContinuousCurrentLimit);
    driveMotor.setInverted(Constants.Swerve.driveInvert);
    driveMotor.setIdleMode(Constants.Swerve.driveNeutralMode);
    driveEncoder.setVelocityConversionFactor(Constants.Swerve.driveConversionVelocityFactor);
    driveEncoder.setPositionConversionFactor(Constants.Swerve.driveConversionPositionFactor);
    driveController.setP(Constants.Swerve.angleKP);
    driveController.setI(Constants.Swerve.angleKI);
    driveController.setD(Constants.Swerve.angleKD);
    driveController.setFF(Constants.Swerve.angleKFF);
    driveMotor.enableVoltageCompensation(Constants.General.voltageComp);
    driveMotor.burnFlash();
    driveEncoder.setPosition(0.0);
  }

  @Override
  public void updateInputs(ModuleIOInputs inputs) {
    inputs.drivePositionMeter = driveEncoder.getPosition();
    inputs.driveVelocityMeterPerSec = driveEncoder.getVelocity();
    inputs.driveAppliedVolts = driveMotor.getAppliedOutput() * RobotController.getBatteryVoltage();
    inputs.driveCurrentAmps = new double[] {driveMotor.getOutputCurrent()};
    inputs.driveTempCelcius = new double[] {driveMotor.getMotorTemperature()};

    inputs.turnAbsolutePositionDegree = angleEncoder.getAbsolutePosition();
    inputs.turnAbsolutePositionOffset = angleOffset.getDegrees();
    inputs.turnPositionDegree = integratedAngleEncoder.getPosition();
    inputs.turnVelocityDegreePerSec = integratedAngleEncoder.getVelocity();
    inputs.turnAppliedVolts = angleMotor.getAppliedOutput() * RobotController.getBatteryVoltage();
    inputs.turnCurrentAmps = new double[] {angleMotor.getOutputCurrent()};
    inputs.turnTempCelcius = new double[] {angleMotor.getMotorTemperature()};
  }

  @Override
  public void setDrivePID(double velocity) {
    driveController.setReference(
        velocity, ControlType.kVelocity, 0, feedforward.calculate(velocity));
  }

  @Override
  public void setDrivePercent(double percent) {
    driveMotor.set(percent);
  }

  @Override
  public void setTurnPID(double position) {
    angleController.setReference(position, ControlType.kPosition);
  }

  @Override
  public void setTurnEncoder(double position) {
    integratedAngleEncoder.setPosition(position);
  }

  @Override
  public void stop() {
    driveMotor.stopMotor();
    angleMotor.stopMotor();
  }
}
