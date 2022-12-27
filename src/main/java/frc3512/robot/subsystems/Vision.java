package frc3512.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc3512.lib.logging.SpartanDoubleEntry;
import frc3512.robot.Constants;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

public class Vision extends SubsystemBase {

  PhotonCamera m_visionCamera;
  boolean m_haveTargets = false;
  boolean m_useAprilTags = true;

  PhotonPipelineResult m_result;
  PhotonTrackedTarget m_bestTarget;

  private static final List<Pose3d> targetPoses =
      Collections.unmodifiableList(
          List.of(
              new Pose3d(
                  3.0, 1.165, 0.287 + 0.165, new Rotation3d(0, 0, Units.degreesToRadians(180.0))),
              new Pose3d(
                  3.0, 0.0, 0.287 + .165, new Rotation3d(0, 0, Units.degreesToRadians(180.0)))));

  /** Container for global measurements. */
  public class GlobalMeasurements {
    double yaw;
    double pitch;
    double range;
    int targetID;
    double poseAmbiguity;
  }

  double m_yaw;
  double m_pitch;
  double m_range;
  int m_targetID;
  double m_poseAmbiguity;
  List<TargetCorner> corners;
  Transform3d bestCameraToTarget;
  Pose3d robotPose;

  LinkedList<GlobalMeasurements> measurements = new LinkedList<>();

  SpartanDoubleEntry yawEntry = new SpartanDoubleEntry("/Diagnostics/Vision/Yaw");
  SpartanDoubleEntry rangeEntry = new SpartanDoubleEntry("/Diagnostics/Vision/Estimated Range");
  SpartanDoubleEntry timestampEntry = new SpartanDoubleEntry("/Diagnostics/Vision/Timestamp");
  SpartanDoubleEntry targetIDEntry = new SpartanDoubleEntry("/Diagnostics/Vision/Target ID");
  SpartanDoubleEntry poseAmbiguityEntry =
      new SpartanDoubleEntry("/Diagnostics/Vision/Pose Ambiguity");

  public Vision() {
    m_visionCamera = new PhotonCamera(Constants.Vision.visionCameraName);
  }

  public double getTimestamp() {
    return Timer.getFPGATimestamp() - m_result.getLatencyMillis();
  }

  public double getYaw() {
    if (!(measurements == null) && !(measurements.isEmpty())) {
      GlobalMeasurements measurement = measurements.getLast();
      return measurement.yaw;
    } else {
      return 0.0;
    }
  }

  public double getPitch() {
    if (!(measurements == null) && !(measurements.isEmpty())) {
      GlobalMeasurements measurement = measurements.getLast();
      return measurement.pitch;
    } else {
      return 0.0;
    }
  }

  public double getRange() {
    if (!(measurements == null) && !(measurements.isEmpty())) {
      GlobalMeasurements measurement = measurements.getLast();
      return measurement.range;
    } else {
      return 0.0;
    }
  }

  public double getTargetID() {
    if (!(measurements == null) && !(measurements.isEmpty()) && m_useAprilTags) {
      GlobalMeasurements measurement = measurements.getLast();
      return measurement.targetID;
    } else {
      return 0.0;
    }
  }

  public double getPoseAmbiguity() {
    if (!(measurements == null) && !(measurements.isEmpty()) && m_useAprilTags) {
      GlobalMeasurements measurement = measurements.getLast();
      return measurement.poseAmbiguity;
    } else {
      return 0.0;
    }
  }

  public Pose2d getRobotPose() {
    if (!(robotPose == null) && m_useAprilTags) {
      return robotPose.toPose2d();
    } else {
      return new Pose2d();
    }
  }

  public void updateVisionMeasurements() {
    m_result = m_visionCamera.getLatestResult();
    m_bestTarget = m_result.getBestTarget();
    m_haveTargets = m_result.hasTargets();

    GlobalMeasurements currMeasurement = new GlobalMeasurements();

    var timestamp = Timer.getFPGATimestamp() - m_result.getLatencyMillis();
    timestampEntry.set(timestamp);

    if (m_bestTarget.getYaw() < 0.0) {
      m_yaw = m_bestTarget.getYaw() - Constants.Vision.cameraYawOffset;
    } else if (m_bestTarget.getYaw() > 0.0) {
      m_yaw = m_bestTarget.getYaw() + Constants.Vision.cameraYawOffset;
    } else {
      m_yaw = m_bestTarget.getYaw();
    }

    yawEntry.set(m_yaw);
    currMeasurement.yaw = m_yaw;

    m_pitch = m_bestTarget.getPitch();
    currMeasurement.pitch = m_pitch;

    m_range =
        PhotonUtils.calculateDistanceToTargetMeters(
            Constants.Vision.cameraHeightMeters,
            Constants.Vision.targetHeightMeters,
            Constants.Vision.cameraPitchDegrees,
            m_pitch);
    rangeEntry.set(m_range);
    currMeasurement.range = m_range;

    corners = m_bestTarget.getCorners();

    if (m_useAprilTags) {
      m_targetID = m_bestTarget.getFiducialId();
      m_poseAmbiguity = m_bestTarget.getPoseAmbiguity();

      currMeasurement.targetID = m_targetID;
      currMeasurement.poseAmbiguity = m_poseAmbiguity;

      if (m_bestTarget.getPoseAmbiguity() <= .2
          && m_targetID >= 0
          && m_targetID < targetPoses.size()) {
        var targetPose = targetPoses.get(m_targetID);
        bestCameraToTarget = m_bestTarget.getBestCameraToTarget();

        Pose3d camPose = targetPose.transformBy(bestCameraToTarget.inverse());

        var measurement = camPose.transformBy(Constants.Vision.CAMERA_TO_ROBOT);
        robotPose = measurement;
      }

      measurements.add(currMeasurement);
    } else {
      m_targetID = 0;
      m_poseAmbiguity = 0.0;

      currMeasurement.targetID = m_targetID;
      currMeasurement.poseAmbiguity = m_poseAmbiguity;

      bestCameraToTarget = new Transform3d();
      robotPose = new Pose3d();

      measurements.add(currMeasurement);
    }
  }

  @Override
  public void periodic() {
    if (RobotBase.isReal()) {
      updateVisionMeasurements();
    }
  }
}
