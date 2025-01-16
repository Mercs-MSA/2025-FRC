package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.Elevator1Constants;
import frc.robot.Constants.TestIntakePivotConstants;
import frc.robot.Constants.mmConstants;
import frc.robot.subsystems.LimitSwitch;

// import frc.robot.subsystems.SubsystemLib;

// import com.ctre.phoenix6.CANBus;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator1 extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final double velocityKp = Elevator1Constants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        public final double rotations = Elevator1Constants.positionUp;

        public TestSubsystemConfig() {
            super("ELevatorMotor1", Elevator1Constants.id, "canivore");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(true); //true if you want it to spin clockwise
            // configStatorCurrentLimit(10, true);
            configMotionMagic(mmConstants.speed, mmConstants.acceleration, mmConstants.jerk);
            // SetPositionVoltage(rotations);
        }
    }


    private boolean hasTared = false;

    public TestSubsystemConfig config;

    // public boolean isPressed;

    public Elevator1(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void motorToPosMM(double pos) {
        setMMPosition(pos);
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


    @Override 
    public void periodic(){

        if (LimitSwitch.checkSwitch() && motor != null && !hasTared && Constants.isWithinTol(0, getPivotMotorPosition(), 0.3)) {
            tareMotor(); 
            hasTared = true; 
        }

        // If the limit switch is released, reset the taring flag
        if (!LimitSwitch.checkSwitch()) {
            hasTared = false; 
        }

        // Update motor position on the SmartDashboard
        SmartDashboard.putNumber("Elevator 1 Pos", testMotorGetPosition());
    }

    

}
