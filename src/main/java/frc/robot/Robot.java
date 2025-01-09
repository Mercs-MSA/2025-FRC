// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final RobotContainer m_robotContainer = new RobotContainer();

  @Override
  public void robotInit() {
    SmartDashboard.putData("Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Funnier");
    
        builder.addBooleanProperty("Is Funny", () -> ControlOne.getAButton(), null);
        }
        });
        SmartDashboard.putData("Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Funnier");
    
        builder.addBooleanProperty("Is Funny", () -> ControlOne.getAButton(), null);
        }
        });
    
        SmartDashboard.putData("Test2", new Sendable() {
          @Override
          public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType("Funnier");
        
            builder.addDoubleProperty("Slider from mario 64", () -> ControlOne.getLeftX(), null);
            builder.addDoubleProperty("Slider from mario odyessy", () -> ControlOne.getLeftY(), null);
            }
            });
    
            SmartDashboard.putData("Test3", new Sendable() {
              @Override
              public void initSendable(SendableBuilder builder) {
                builder.setSmartDashboardType("Funnier");
            
                builder.addDoubleProperty("Slider from mario galaxy", () -> funnyMeter, null);
                builder.addBooleanProperty("Funny meters are off the charts", () -> offTheCharts, null);
                }
                });
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  double funnyMeter = 0;
  XboxController ControlOne = new XboxController(0); 
  boolean offTheCharts = false;


  @Override
  public void robotPeriodic() {
    
    if (funnyMeter > 3) {
      offTheCharts = true;
    }
    else {
      offTheCharts = false;
    }
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }
  
  @Override
  public void disabledPeriodic() {
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
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
  public void teleopPeriodic(){
  }

  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}