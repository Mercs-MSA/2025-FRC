// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.TestIntakePivot;
import frc.robot.subsystems.TestLeaderFollower;
import frc.robot.subsystems.Elevator1;
import frc.robot.subsystems.Elevator2;


public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  public static final TestLeaderFollower m_TestLeaderFollower = new TestLeaderFollower(false);
  public static final Elevator1 m_Elevator1 = new Elevator1(true);
  public static final Elevator2 m_Elevator2 = new Elevator2(true);





  public Robot() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 

    var lastResult = LimelightHelpers.getLatestResults(Constants.VisionConstants.limelightFrontName);
    if (lastResult.valid) {
      m_robotContainer.drivetrain.addVisionMeasurement(LimelightHelpers.getBotPose2d_wpiBlue(Constants.VisionConstants.limelightFrontName), Timer.getFPGATimestamp());
      SmartDashboard.putBoolean("limelightFrontResultValid", true);
    } else {
      SmartDashboard.putBoolean("limelightFrontResultValid", false);
    }

    // SmartDashboard.putNumber("leader motor pos", m_TestLeaderFollower.testMotorGetPosition());
    // SmartDashboard.putNumber("follower motor pos", m_TestLeaderFollower.followerMotor.getPosition().getValueAsDouble());


    SmartDashboard.putNumber("Elevator1", m_Elevator1.testMotorGetPosition());
    SmartDashboard.putNumber("Elevator2", m_Elevator2.testMotorGetPosition());

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
