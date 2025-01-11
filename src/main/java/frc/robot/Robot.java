// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
DoublePublisher xPub;
DoublePublisher yPub;
double x = 0;
double y = 0;
double slider = 0;
XboxController ControlOne = new XboxController(0);
boolean aboveThree = false;

  private final RobotContainer m_robotContainer = new RobotContainer();

  private NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
  private NetworkTable ntTable = ntInst.getTable("datatable");
  private DoubleTopic xSub = ntTable.getDoubleTopic("x");
  private DoubleEntry xEntry = xSub.getEntry(0);
  // private SendableChooser controlChoice = new SendableChooser<>();
  // private void addOption(String commandtest, V CommandTest)

  @Override
  public void robotInit() {
    SmartDashboard.putData("Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Test");
        builder.addBooleanProperty("A button", () -> ControlOne.getAButton(), null);
        builder.addDoubleProperty("Controller x", () -> ControlOne.getLeftX(), null);
        builder.addDoubleProperty("Controller y", () -> ControlOne.getLeftY(), null);
        builder.addDoubleProperty("Tester controlled slider", () -> slider, null);
        builder.addBooleanProperty("Is above 3", () -> aboveThree, null);
      }});

  xSub.publish();
  }


  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and
   * test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */


  @Override
  public void robotPeriodic() {
    double x = xEntry.get();
    if (x > 3) {
      aboveThree = true;
    } else {
      aboveThree = false;
    }
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}