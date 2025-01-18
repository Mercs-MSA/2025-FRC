package frc.robot.commands;

import java.util.Optional;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class CommandToPose extends Command {

  private final CommandSwerveDrivetrain drivetrain;
  private Pose2d targetPose;
  SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric();

  private final ProfiledPIDController thetaController =
    new ProfiledPIDController(2, 0, 0, new TrapezoidProfile.Constraints(Math.PI, Math.PI));
  private final PIDController xVelController =
    // new ProfiledPIDController(2, 0, 0, new TrapezoidProfile.Constraints(Constants.DriveToPoseConstants.linearMetersMaxVel, Constants.DriveToPoseConstants.linearMetersMaxAccel));
    new PIDController(2, 0, 0);
  private final PIDController yVelController =
    new PIDController(2, 0, 0);
    // new ProfiledPIDController(2, 0, 0, new TrapezoidProfile.Constraints(Constants.DriveToPoseConstants.linearMetersMaxVel, Constants.DriveToPoseConstants.linearMetersMaxAccel));

  public CommandToPose(CommandSwerveDrivetrain swerve, Pose2d pose) {
    this.drivetrain = swerve;
    this.targetPose = pose;

    thetaController.setTolerance(Units.degreesToRadians(Constants.DriveToPoseConstants.angularDegreesTolerance));
    xVelController.setTolerance(Constants.DriveToPoseConstants.linearMetersTolerance);
    yVelController.setTolerance(Constants.DriveToPoseConstants.linearMetersTolerance);
    
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    addRequirements(swerve);
  }

  @Override
  public void initialize() {    
    var currPose = drivetrain.getState().Pose;
    thetaController.reset(currPose.getRotation().getRadians());
    // xVelController.reset(currPose.getX());
    // yVelController.reset(currPose.getY());
  }

  @Override
  public void execute() {
    var currPose = drivetrain.getState().Pose;
    SmartDashboard.putNumber("currPose", currPose.getRotation().getRadians());
    final Optional<Alliance> alliance = DriverStation.getAlliance();
    final double thetaVelocity = atGoal() ? 0.0 :
        thetaController.calculate(
            currPose.getRotation().getRadians(), targetPose.getRotation().getRadians());
    final double xVelocity = atGoal() ? 0.0 :
        xVelController.calculate(
            currPose.getX(), targetPose.getX()
        );
    final double yVelocity = atGoal() ? 0.0 :
        yVelController.calculate(
            currPose.getY(), targetPose.getY()
        );

    SmartDashboard.putNumber("xVel", xVelocity);
    SmartDashboard.putNumber("yVel", yVelocity);
    SmartDashboard.putNumber("thetaVel", thetaVelocity);

    SmartDashboard.putNumber("tgtPoseX", targetPose.getX());
    SmartDashboard.putNumber("tgtPoseY", targetPose.getY());

    SmartDashboard.putNumber("test", currPose.getX());
    SmartDashboard.putNumber("test2", targetPose.getX());

    drivetrain.setControl(
        driveRequest
        .withVelocityX(xVelocity * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? -1 : 1))
        .withVelocityY(yVelocity * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? -1 : 1))
        .withRotationalRate(thetaVelocity)
    );
}
  public boolean atGoal() {
    return thetaController.atGoal() && xVelController.atSetpoint() && yVelController.atSetpoint();
  }

  @Override
  public boolean isFinished(){
    return atGoal();
    // return true;
  }

//   @Override
//   public void end(boolean interrupted){
//     Constants.Vision.autoAlignActice = false;
//   }
}