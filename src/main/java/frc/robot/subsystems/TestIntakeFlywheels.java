package frc.robot.subsystems;

import com.ctre.phoenix6.controls.NeutralOut;

// import frc.robot.subsystems.SubsystemLib;

// import com.ctre.phoenix6.CANBus;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.TestIntakeFlywheelsConstants;

public class TestIntakeFlywheels extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */
        public final double rotations = TestIntakeFlywheelsConstants.position;
        public final double velocityKp = TestIntakeFlywheelsConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;

        public TestSubsystemConfig() {
            super("FlywheelsMotor", TestIntakeFlywheelsConstants.id, "canivore");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(true);
            //SetPositionVoltage(rotations);
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

    public void applyVoltage(double voltage)
    {
        setVoltage(voltage);
    }

    public void stopIntake(){
        motor.setControl(new NeutralOut());
    }

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }
}
