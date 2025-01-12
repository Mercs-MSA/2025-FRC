package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.None;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.Measure;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.units.Units.Meters;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Constants {

    public static final class TestSubsystemConstants{
        public static final int id = 0;

        public static final boolean attached = false;

        public static final double kP = 100; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double position = 0;
    }
    

    public static final class TestIntakeFlywheelsConstants{
        public static final int id = 26;

        public static final boolean attached = true;

        public static final double kP = 5; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 2;
        public static final double position = 0;
    }


    public static final class TestIntakePivotConstants{
        public static final int id = 21;

        public static final boolean attached = false;

        public static final double kP = 5; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double positionUp = 1;
        public static final double positionDown = 0;

        public static final double tol = 0.4;
    }


    public static final class Elevator1Constants{
        public static final int id = 11;

        public static final boolean attached = false;

        public static final double kP = 1.6; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double positionUp = 90; //change this
        public static final double positionDown = 0;

        public static final double tol = 0.4;
    }

    public static final class Elevator2Constants{
        public static final int id = 21;

        public static final boolean attached = false;

        public static final double kP = 1.6; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double positionUp = 50; // change this
        public static final double positionDown = 0;

        public static final double tol = 0.4;
    }

    public static final class mmConstants{
        public static final double acceleration = 1;
        public static final double speed = 30;
        public static final double jerk = 0;



    }

    public static final class LeaderFollowerConstants{
        public static final int leaderID = 21;
        public static final int followerID = 11;

        public static final boolean attached = false;

        public static final double kP = 5; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double positionUp = 10;
        public static final double positionDown = 0;

        public static final double tol = 0.01;
    }

    public static final class beambreakConstants {
        public static final boolean breakAttached = true;
        public static final String beamBreakName = "beambreak";
        public static final int beamBreakChannel = 1;

    }

    public static final class VisionConstants {
        public static final String limelightFrontName = "limelight-front";
    }


    public static boolean isWithinTol(double targetPose, double currentPose, double tolerance) {
        return (Math.abs(targetPose - currentPose) <= tolerance);
    }
    
}
