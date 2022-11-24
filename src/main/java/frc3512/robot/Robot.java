package frc3512.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc3512.lib.config.CTREConfigs;
import frc3512.robot.Constants.RunningMode;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggedNetworkTables;
import org.littletonrobotics.junction.io.ByteLogReceiver;
import org.littletonrobotics.junction.io.ByteLogReplay;
import org.littletonrobotics.junction.io.LogSocketServer;

public class Robot extends LoggedRobot {
  public static CTREConfigs ctreConfigs;
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private boolean runningAuton = false;

  @Override
  public void robotInit() {
    Logger logger = Logger.getInstance();
    setUseTiming(Constants.General.getMode() != RunningMode.REPLAY);
    LoggedNetworkTables.getInstance().addTable("/SmartDashboard");

    switch (Constants.General.getMode()) {
      case REAL:
        logger.addDataReceiver(new ByteLogReceiver("/home/lvuser"));
        logger.addDataReceiver(new LogSocketServer(5900));
        break;

      case SIM:
        logger.addDataReceiver(new LogSocketServer(5900));
        break;

      case REPLAY:
        String path = ByteLogReplay.promptForPath();
        logger.setReplaySource(new ByteLogReplay(path));
        logger.addDataReceiver(new ByteLogReceiver(ByteLogReceiver.addPathSuffix(path, "_sim")));
        break;
    }

    logger.start();

    // Silence joystick connection warnings.
    // Also diable LiveWindow as we don't find use in it.
    DriverStation.silenceJoystickConnectionWarning(true);
    LiveWindow.disableAllTelemetry();

    ctreConfigs = new CTREConfigs();

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    // If not running after an auton, then constantly reset the swerve modules.
    if (!runningAuton) {
      m_robotContainer.disabledActions();
    }
  }

  @Override
  public void autonomousInit() {
    runningAuton = true;
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    runningAuton = false;

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
