package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.LeaderFollowerConstants;
import com.ctre.phoenix6.hardware.TalonFX;


public class TestLeaderFollower extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. */

        public final double velocityKp = LeaderFollowerConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;

        public TestSubsystemConfig() {
            super("IntakePivotMotor", LeaderFollowerConstants.leaderID, "canivore");
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(true); // true if you want it to spin clockwise
            configMotionMagic(5,5, 0);
        }
    }

    public TestSubsystemConfig config;
    private TalonFX followerMotor; // Declare the follower motor

    public TestLeaderFollower(boolean attached) {
        super(attached);
        if (attached) {
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); // Leader motor

            // Create and configure the follower motor
            followerMotor = TalonFXFactory.createPermanentFollowerTalon(new CanDeviceId(LeaderFollowerConstants.followerID, "canivore"), TalonFXFactory.createConfigTalon(config.id, config.talonConfig), true);
        }
    }

    public void pivotToPosMM(double pos) {
        setMMPositionFOC(pos);
    }

    public void testMotorGoToPosition(double pos) {
        SetPositionVoltage(pos); // Doesn't actually go anywhere
    }

    public double testMotorGetPosition() {
        return GetPosition();
    }

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }
}
