// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
// import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.CommandBeginWheels;
import frc.robot.commands.CommandChangeScoringMode;
import frc.robot.commands.CommandIntakeWheelsCollect;
import frc.robot.commands.ElevatorToPos;
// import frc.robot.commands.CommandToPos;
import frc.robot.commands.IntakePivotMoveToPos;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Beambreak;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LimitSwitch;
import frc.robot.subsystems.SubsystemLib;
import frc.robot.subsystems.TestIntakeFlywheels;
import frc.robot.subsystems.TestIntakePivot;
import frc.robot.Constants.*;



public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final Beambreak m_Beambreak = new Beambreak();

    private final LimitSwitch m_LimitSwitch = new LimitSwitch();

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    // public static final TestSubsystem m_testSubsystem = new TestSubsystem(false); // make sure to specify whether it is attached

    public static final TestIntakeFlywheels m_testIntakeFlywheelsMotor = new TestIntakeFlywheels(true);

    public static final TestIntakePivot m_TestIntakePivot = new TestIntakePivot(false);

    private final SendableChooser<Command> autoChooser;

    Map<String, Command> autonomousCommands = new HashMap<String, Command>() {
        {
            /* Single Commands Each Subsystem */
            
    
            /* Reset Commands */
            put("Reset All", new ParallelCommandGroup(
            
            ));
    
        }
    };

    public RobotContainer() {
        autoChooser = AutoBuilder.buildAutoChooser("Do Nothing");
        SmartDashboard.putData("Auto Mode", autoChooser);
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

        //joystick.x().onTrue(new CommandToPos(m_testSubsystem, 0));
        joystick.rightBumper().onTrue(new CommandIntakeWheelsCollect(m_testIntakeFlywheelsMotor, m_Beambreak, Constants.TestIntakeFlywheelsConstants.voltageOut));


        joystick.y().onTrue(new ElevatorToPos());
        // joystick.y().onTrue(new ElevatorToPos(Constants.Elevator1Constants.positionDown));

        joystick.pov(0).onTrue(new CommandChangeScoringMode(ElevatorStages.INTAKE));
        joystick.pov(90).onTrue(new CommandChangeScoringMode(ElevatorStages.L2));
        joystick.pov(180).onTrue(new CommandChangeScoringMode(ElevatorStages.L3));
        joystick.pov(270).onTrue(new CommandChangeScoringMode(ElevatorStages.L4));

        joystick.x().onTrue(AutoBuilder.pathfindToPose(
        new Pose2d(1.80, 7.6, Rotation2d.fromDegrees(90)), 
        new PathConstraints(
            4.0, 4.0, 
            Units.degreesToRadians(360), Units.degreesToRadians(540)
        ), 
        2.0
        ));

       // joystick.leftTrigger(0.1).whileTrue(new CommandBeginWheels(m_testIntakeFlywheelsMotor, 0.5, true, false));
    
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
        // return Commands.print("No autonomous command configured");
    }
}
