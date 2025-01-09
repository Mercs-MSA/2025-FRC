package frc.robot.subsystems;

// import frc.robot.subsystems.SubsystemLib;

// import com.ctre.phoenix6.CANBus;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestIntakeFlywheels extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */
        public final double rotations = 0;
        public final double velocityKp = 0;
        public final double velocityKs = 0;
        public final double velocityKv = 0;

        public TestSubsystemConfig() {
            super("FlywheelsMotor", 0, "rio");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            configInvert(true);
            SetPositionVoltage(rotations);
        }



    }

    public TestSubsystemConfig config;

    public TestIntakeFlywheels(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void testMotorGoToPosition(double pos) {
        SetPositionVoltage(pos); // doesnt actually go anywhere
    }

    public void testVoltage(double voltage)
    {
        setVoltage(voltage);
    }

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }
}
