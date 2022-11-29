package frc3512.robot.auton;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc3512.robot.Constants;
import frc3512.robot.subsystems.drive.Swerve;

public class TestAuto extends SequentialCommandGroup {
  public TestAuto(Swerve swerve) {
    PathConstraints constraints = new PathConstraints(1.0, 4.0);

    PathPlannerTrajectory trajectory = PathPlanner.loadPath("TestPath", constraints);

    PPSwerveControllerCommand swerveControllerCommand =
        new PPSwerveControllerCommand(
            trajectory,
            swerve::getPose,
            Constants.Swerve.swerveKinematics,
            new PIDController(1.0, 0, 0),
            new PIDController(1.0, 0, 0),
            new PIDController(1.0, 0, 0),
            swerve::setModuleStates,
            swerve);

    addCommands(
        new InstantCommand(() -> swerve.resetOdometry(trajectory.getInitialHolonomicPose())),
        swerveControllerCommand);
  }
}
