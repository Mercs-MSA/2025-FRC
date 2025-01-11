package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.TestIntakePivotConstants;
import frc.robot.Constants.TestSubsystemConstants;

// import frc.robot.subsystems.SubsystemLib;

// import com.ctre.phoenix6.CANBus;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestIntakePivot extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final double velocityKp = TestIntakePivotConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        public final double rotations = Constants.TestIntakePivotConstants.positionUp;

        public TestSubsystemConfig() {
            super("IntakePivotMotor", TestIntakePivotConstants.id, "canivore");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(true); //true if you want it to spin clockwise
            // configStatorCurrentLimit(10, true);
            configMotionMagic(80, 30, 0);
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

    public void pivotToPosMM(double pos) {
        setMMPositionFOC(pos);
    }

    public void testMotorGoToPosition(double pos) {
        SetPositionVoltage(pos); // doesnt actually go anywhere
    }

    public double testMotorGetPosition() {
        return GetPosition();
    }

    // public Command runPosition(double pos) {
    //     return run(() -> (pos)).withName("PivotGoUp");
    // }

   

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }
}
