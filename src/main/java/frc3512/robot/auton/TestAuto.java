package frc3512.robot.auton;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc3512.robot.Constants;
import frc3512.robot.subsystems.Swerve;

public class TestAuto extends SequentialCommandGroup {
  public TestAuto(Swerve swerve) {
    PathConstraints constraints = new PathConstraints(1.0, 4.0);
    PathPlannerTrajectory trajectory1 =
        PathPlanner.generatePath(
            constraints,
            new PathPoint(
                new Translation2d(1.72, 1.94),
                Rotation2d.fromDegrees(0.0),
                Rotation2d.fromDegrees(0.0)),
            new PathPoint(
                new Translation2d(3.42, 3.42),
                Rotation2d.fromDegrees(90.0),
                Rotation2d.fromDegrees(-90.0)));
    PathPlannerTrajectory trajectory2 =
        PathPlanner.generatePath(
            constraints,
            true,
            new PathPoint(
                new Translation2d(1.72, 1.94),
                Rotation2d.fromDegrees(0.0),
                Rotation2d.fromDegrees(0.0)),
            new PathPoint(
                new Translation2d(4.55, 1.47),
                Rotation2d.fromDegrees(-90.0),
                Rotation2d.fromDegrees(88.78)),
            new PathPoint(
                new Translation2d(6.02, 3.46),
                Rotation2d.fromDegrees(-1.71),
                Rotation2d.fromDegrees(0.0)));

    PPSwerveControllerCommand swerveControllerCommand =
        new PPSwerveControllerCommand(
            trajectory1,
            swerve::getPose,
            Constants.Swerve.swerveKinematics,
            new PIDController(1.0, 0, 0),
            new PIDController(1.0, 0, 0),
            new PIDController(1.0, 0, 0),
            swerve::setModuleStates,
            swerve);

    PPSwerveControllerCommand swerveControllerCommand2 =
        new PPSwerveControllerCommand(
            trajectory2,
            swerve::getPose,
            Constants.Swerve.swerveKinematics,
            new PIDController(1.0, 0, 0),
            new PIDController(1.0, 0, 0),
            new PIDController(1.0, 0, 0),
            swerve::setModuleStates,
            swerve);

    addCommands(
        new InstantCommand(() -> swerve.resetOdometry(trajectory1.getInitialHolonomicPose())),
        swerveControllerCommand,
        swerveControllerCommand2);
  }
}
