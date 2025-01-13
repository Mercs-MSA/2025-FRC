// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



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
XboxController ControlOne = new XboxController(0);
boolean moveElevator = false;
boolean spinIntake = false;
boolean activateClimber = false;

  private final RobotContainer m_robotContainer = new RobotContainer();
  private SendableChooser<XboxController.Button> controlChoiceElevator = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceIntake = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceClimber = new SendableChooser<>();

  @Override
  public void robotInit() {
    SmartDashboard.putData("Selectable Action Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("Move Elevator", () -> moveElevator, null);
        builder.addBooleanProperty("Spin Intake", () -> spinIntake, null);
        builder.addBooleanProperty("Activate Climb", () -> activateClimber, null);
      }});
    controlChoiceElevator.setDefaultOption("A Button", XboxController.Button.kA);
    controlChoiceElevator.addOption("B Button", XboxController.Button.kB);
    controlChoiceElevator.addOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Elevator Buttonmap", controlChoiceElevator);
    controlChoiceIntake.addOption("A Button", XboxController.Button.kA);
    controlChoiceIntake.setDefaultOption("B Button", XboxController.Button.kB);
    controlChoiceIntake.addOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Intake Buttonmap", controlChoiceIntake);
    controlChoiceClimber.addOption("A Button", XboxController.Button.kA);
    controlChoiceClimber.addOption("B Button", XboxController.Button.kB);
    controlChoiceClimber.setDefaultOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Climber Buttonmap", controlChoiceClimber);
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
    moveElevator = false;
    spinIntake = false;
    activateClimber = false;
    if (ControlOne.getAButton()) {
      if (controlChoiceElevator.getSelected() == XboxController.Button.kA) {
        moveElevator = true;
      }
      else if (controlChoiceClimber.getSelected() == XboxController.Button.kA) {
        activateClimber = true;
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kA) {
        spinIntake = true;
      }
    }

    if (ControlOne.getBButton()) {
      if (controlChoiceElevator.getSelected() == XboxController.Button.kB) {
        moveElevator = true;
      }
      else if (controlChoiceClimber.getSelected() == XboxController.Button.kB) {
        activateClimber = true;
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kB) {
        spinIntake = true;
      }
    }
    
    if (ControlOne.getXButton()) {
      if (controlChoiceElevator.getSelected() == XboxController.Button.kX) {
        moveElevator = true;
      }
      else if (controlChoiceClimber.getSelected() == XboxController.Button.kX) {
        activateClimber = true;
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kX) {
        spinIntake = true;
      }
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