// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.TestIntakePivot;
import frc.robot.subsystems.TestLeaderFollower;
import frc.robot.subsystems.Elevator1;
import frc.robot.subsystems.Elevator2;
import frc.robot.subsystems.TestIntakeFlywheels;
import frc.robot.subsystems.Beambreak;



public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer = new RobotContainer();
  public static final TestLeaderFollower m_TestLeaderFollower = new TestLeaderFollower(false);
  public static final Elevator1 m_Elevator1 = new Elevator1(false);
  public static final Elevator2 m_Elevator2 = new Elevator2(false);
  public static final TestIntakeFlywheels m_Intake = new TestIntakeFlywheels(true);
  // public static final Beambreak m_Beambreak = new Beambreak();





  public Robot() {
    //m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 
    boolean doRejectUpdate = false;
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightFrontName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    
    LimelightHelpers.PoseEstimate mt_front = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightFrontName);
    LimelightHelpers.PoseEstimate mt_back = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightBackName);

    SmartDashboard.putBoolean("FrontLimelightOnline", mt_front != null);
    SmartDashboard.putBoolean("BackLimelightOnline", mt_back != null);

    if (mt_front != null) {
      Pose2d mt_front_pose = mt_front.pose;
    }
    if (mt_back != null) {
      Pose2d mt_back_pose = mt_back.pose;
    }

    m_robotContainer.drivetrain.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
    if (mt_front != null && mt_back != null) {
      if (mt_front.avgTagArea > mt_back.avgTagArea) {
        if(Math.abs(m_robotContainer.drivetrain.getPigeon2().getRate()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
        {
          doRejectUpdate = true;
        }
        if(mt_front.tagCount == 0)
        {
          doRejectUpdate = true;
        }
        if(!doRejectUpdate)
        {
          m_robotContainer.drivetrain.addVisionMeasurement(
              mt_front.pose,
              Utils.getCurrentTimeSeconds());
        }
      } else {
        if(Math.abs(m_robotContainer.drivetrain.getPigeon2().getRate()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
        {
          doRejectUpdate = true;
        }
        if(mt_back.tagCount == 0)
        {
          doRejectUpdate = true;
        }
        if(!doRejectUpdate)
        {
          m_robotContainer.drivetrain.addVisionMeasurement(
              mt_back.pose,
              Utils.getCurrentTimeSeconds());
        }
      }
    } else if (mt_front != null) {
      if(Math.abs(m_robotContainer.drivetrain.getPigeon2().getRate()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
      {
        doRejectUpdate = true;
      }
      if(mt_front.tagCount == 0)
      {
        doRejectUpdate = true;
      }
      if(!doRejectUpdate)
      {
        m_robotContainer.drivetrain.addVisionMeasurement(
            mt_front.pose,
            Utils.getCurrentTimeSeconds());
      }
    } else if (mt_back != null) {
      if(Math.abs(m_robotContainer.drivetrain.getPigeon2().getRate()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
      {
        doRejectUpdate = true;
      }
      if(mt_back.tagCount == 0)
      {
        doRejectUpdate = true;
      }
      if(!doRejectUpdate)
      {
        m_robotContainer.drivetrain.addVisionMeasurement(
          mt_back.pose,
            Utils.getCurrentTimeSeconds());
      }
    }

    // SmartDashboard.putNumber("leader motor pos", m_TestLeaderFollower.testMotorGetPosition());
    // SmartDashboard.putNumber("follower motor pos", m_TestLeaderFollower.followerMotor.getPosition().getValueAsDouble());


    SmartDashboard.putNumber("Elevator1", m_Elevator1.testMotorGetPosition());
    SmartDashboard.putNumber("Elevator2", m_Elevator2.testMotorGetPosition());

    // SmartDashboard.putBoolean("Beambreak broken", m_Beambreak.checkBreak());

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    // var lastResult = LimelightHelpers.getLatestResults(Constants.VisionConstants.limelightFrontName);
       
    // if (lastResult.valid) {
    //   m_robotContainer.drivetrain.addVisionMeasurement(LimelightHelpers.getBotPose2d_wpiBlue(Constants.VisionConstants.limelightFrontName), Timer.getFPGATimestamp());
    //   SmartDashboard.putBoolean("limelightFrontResultValid", true);
    //   double[] pose = {LimelightHelpers.getBotPose2d_wpiBlue(Constants.VisionConstants.limelightFrontName).getTranslation().getX(), LimelightHelpers.getBotPose2d_wpiBlue(Constants.VisionConstants.limelightFrontName).getTranslation().getY(), LimelightHelpers.getBotPose2d_wpiBlue(Constants.VisionConstants.limelightFrontName).getRotation().getRadians()};
    //   SmartDashboard.putNumberArray("llPos", pose);
    // } else {
    //   SmartDashboard.putBoolean("limelightFrontResultValid", false);
    // }
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}

