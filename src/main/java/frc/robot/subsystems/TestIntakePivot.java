package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;

// import frc.robot.subsystems.SubsystemLib;

// import com.ctre.phoenix6.CANBus;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestIntakePivot extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */
        public final double rotations = 0;

        public final double velocityKp = 0;
        public final double velocityKs = 0;
        public final double velocityKv = 0;

        public TestSubsystemConfig() {
            super("TestMotor", 0, "rio");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            configInvert(true); //true if you want it to spin clockwise
            // configMotionMagic(147000, 161000, 0);
            // SetPositionVoltage(rotations);
        }
    }

    public TestSubsystemConfig config;

    public TestIntakePivot(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void testMotorGoToPosition(double pos) {
        SetPositionVoltage(pos); // doesnt actually go anywhere
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
