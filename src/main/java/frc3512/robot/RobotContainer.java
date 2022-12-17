package frc3512.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc3512.robot.auton.TestAuto;
import frc3512.robot.commands.driving.TeleopSwerve;
import frc3512.robot.subsystems.Swerve;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Auton Chooser
  private final SendableChooser<Command> m_autonChooser = new SendableChooser<Command>();

  // Robot subsystems
  private Swerve m_swerve = new Swerve();

  // Xbox controllers
  private final Joystick driver = new Joystick(Constants.Joysticks.xboxControllerPort);

  // Drive Controls
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  // Driver Buttons
  private final JoystickButton zeroGyro =
      new JoystickButton(driver, XboxController.Button.kX.value);
  private final JoystickButton robotCentric =
      new JoystickButton(driver, XboxController.Button.kRightStick.value);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();
    configureAxisActions();
    registerAutons();
  }

  /** Used for defining button actions. */
  private void configureButtonBindings() {

    /* Driver Buttons */
    zeroGyro.whenPressed(new InstantCommand(() -> m_swerve.zeroGyro()));
  }

  /** Used for joystick/xbox axis actions. */
  private void configureAxisActions() {
    m_swerve.setDefaultCommand(
        new TeleopSwerve(
            m_swerve,
            () -> -driver.getRawAxis(translationAxis),
            () -> -driver.getRawAxis(strafeAxis),
            () -> -driver.getRawAxis(rotationAxis),
            () -> robotCentric.get()));
  }

  /** Register the autonomous modes to the chooser for the drivers to select. */
  public void registerAutons() {

    // Register autons.
    m_autonChooser.setDefaultOption("No-op", new InstantCommand());
    m_autonChooser.addOption("TestAuton", new TestAuto(m_swerve));

    // Push the chooser to the dashboard.
    SmartDashboard.putData("Auton Chooser", m_autonChooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Get the selected auton from the chooser.
    return m_autonChooser.getSelected();
  }
}
