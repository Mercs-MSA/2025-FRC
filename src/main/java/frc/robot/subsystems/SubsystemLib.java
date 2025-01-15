package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.PositionVoltage;
// import com.ctre.phoenix6.controls.PositionVoltage;


import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public abstract class SubsystemLib extends SubsystemBase{
    protected boolean attached = false;
    protected TalonFX motor;
    public Config config;  //Creates the config object for every subsystem

    public SubsystemLib(boolean attached) {
        this.attached = attached;
        this.config = setConfig(); // Calls the setConfig function so that the configuration can be set
    }

    protected abstract Config setConfig(); // abstract method that forces all subclasses to define config

    protected void setConfig(Config config) { //The actual function that sets the config
        this.config = config;
    };

    public void stop() {
        if (attached) {
            motor.stopMotor();
        }
    }

    /** Here is a super simple tare motor function. Maybe run this at the start of every match*/
    public void tareMotor() {
        if (attached) {
            setMotorPosition(0);
        }
    }

    /**
     * Sets the mechanism position of the motor
     *
     * @param position rotations
     */
    public void setMotorPosition(double position) {
        if (attached) {
            motor.setPosition(position);
        }
    }

    public double getPivotMotorPosition(){
        return motor.getPosition().getValueAsDouble();
    }


    /**
     * velocity control
     *
     * @param velocity rotations per second i think
     */
    public void setVelocity(double velocity) {
        if (attached) {
            VelocityVoltage output = config.velocityControl.withVelocity(velocity);
            motor.setControl(output);
        }
    }


    /**
     * voltage control
     *
     * @param voltage volts. Duh.
     */
    public void setVoltage(double voltage) {
        if (attached) {
            VoltageOut output = config.voltageControl.withOutput(voltage);
            motor.setControl(output);
        }
    }

    public void SetPositionVoltage(double position){
        System.out.println(position);
        System.out.println(attached);
        if (attached) {
            PositionVoltage output = config.positionVoltage.withPosition(position);
            motor.setControl(output);
        }
    }

    public double GetPosition() {
        if (attached) {
            return motor.getPosition().getValueAsDouble();
        } else {
            return 0.0;
        }
    }

    /*This is more spectrum 3847 Motion Magic. I did not write all of this. But i did implement their Param stuff.*/

    /**
     * Closed-loop Position Motion Magic with torque control (requires Pro)
     *
     * @param position rotations
     */
    public void setMMPositionFOC(double position) {
        if (attached) {
            MotionMagicTorqueCurrentFOC mm = config.mmPositionFOC.withPosition(position);
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position Motion Magic
     *
     * @param position rotations
     */
    public void setMMPosition(double position) {
        if (attached) {
            MotionMagicVoltage mm = config.mmPositionVoltage.withPosition(position);
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position Motion Magic
     *
     * @param position rotations
     */
    public void setMMPosition(DoubleSupplier position) {
        if (attached) {
            MotionMagicVoltage mm = config.mmPositionVoltage.withPosition(position.getAsDouble());
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position Motion Magic using a slot other than 0
     *
     * @param position rotations
     * @param slot gains slot
     */
    public void setMMPosition(double position, int slot) {
        if (attached) {
            MotionMagicVoltage mm =
                    config.mmPositionVoltageSlot.withSlot(slot).withPosition(position);
            motor.setControl(mm);
        }
    }

    /**
     * Open-loop Percent output control with voltage compensation
     *
     * @param percent fractional units between -1 and +1
     */
    public void setPercentOutput(double percent) {
        if (attached) {
            VoltageOut output =
                    config.voltageControl.withOutput(12 * percent);
            motor.setControl(output);
        }
    }

    public void setBrakeMode(boolean isInBrake) {
        if (attached) {
            config.configNeutralBrakeMode(isInBrake);
            config.applyTalonConfig(motor);
        }
    }

    


    public static class Config {
        public String name;
        public CanDeviceId id;
        public TalonFXConfiguration talonConfig;
        // public double voltageCompSaturation; // 12V by default

        public MotionMagicVelocityTorqueCurrentFOC mmVelocityFOC = new MotionMagicVelocityTorqueCurrentFOC(0);
        public MotionMagicTorqueCurrentFOC mmPositionFOC = new MotionMagicTorqueCurrentFOC(0);
        public MotionMagicVelocityVoltage mmVelocityVoltage = new MotionMagicVelocityVoltage(0);
        public MotionMagicVoltage mmPositionVoltage = new MotionMagicVoltage(0);
        public MotionMagicVoltage mmPositionVoltageSlot = new MotionMagicVoltage(0).withSlot(1);
        public VoltageOut voltageControl = new VoltageOut(0);
        public VelocityVoltage velocityControl = new VelocityVoltage(0);
        public VelocityTorqueCurrentFOC velocityTorqueCurrentFOC = new VelocityTorqueCurrentFOC(0);
        public PositionVoltage positionVoltage = new PositionVoltage(0);
        
        

        public Config(String name, int id, String canbus) {
            this.name = name;
            this.id = new CanDeviceId(id, canbus);
            talonConfig = new TalonFXConfiguration();

            /* Put default config settings for all mechanisms here */
            talonConfig.HardwareLimitSwitch.ForwardLimitEnable = false;
            talonConfig.HardwareLimitSwitch.ReverseLimitEnable = false;
        }

        public void applyTalonConfig(TalonFX talon) {
            StatusCode result = talon.getConfigurator().apply(talonConfig);
            if (!result.isOK()) {
                DriverStation.reportWarning(
                        "Could not apply config changes to motor!", false); //Maybe this will help fix the chain problem?
            }
        }

        public void isClockwise(boolean clockwise) {  //ALWAYS test this! We do not want chains snapping. Run the motros before we put the mechanism on
            if(!clockwise){
                talonConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
            }
            else {
                talonConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
            }
        }

        
        public void configStatorCurrentLimit(double statorLimit, boolean enabled) {
            talonConfig.CurrentLimits.StatorCurrentLimit = statorLimit;
            talonConfig.CurrentLimits.StatorCurrentLimitEnable = enabled;
        }



        /*Brady- I sourced most of these motion magic configs from Spectrum 3847 */
       
        // Configure optional motion magic velocity parameters
        public void configMotionMagicVelocity(double acceleration, double feedforward) {
            mmVelocityFOC = mmVelocityFOC.withAcceleration(acceleration).withFeedForward(feedforward);
            mmVelocityVoltage = mmVelocityVoltage.withAcceleration(acceleration).withFeedForward(feedforward);
        }

        // Configure optional motion magic position parameters
        public void configMotionMagicPosition(double feedforward) {
            mmPositionFOC = mmPositionFOC.withFeedForward(feedforward);
            mmPositionVoltage = mmPositionVoltage.withFeedForward(feedforward);
        }

        public void configMotionMagic(double cruiseVelocity, double acceleration, double jerk) {
            talonConfig.MotionMagic.MotionMagicCruiseVelocity = cruiseVelocity;
            talonConfig.MotionMagic.MotionMagicAcceleration = acceleration;
            talonConfig.MotionMagic.MotionMagicJerk = jerk;
        }

       
        public void configGearRatio(double gearRatio) {  //find the number of gearboxes here for an accurate number of shaft rotations
            talonConfig.Feedback.SensorToMechanismRatio = gearRatio;
        }

        public double getGearRatio() {     //Outputs gear ratio. Easy.
            return talonConfig.Feedback.SensorToMechanismRatio;
        }

        public void configNeutralBrakeMode(boolean isInBrake) { //If we have a mechanims that can not hold itself set the neutral mode to Brake(true)
            if (isInBrake) {
                talonConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
            } else {
                talonConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
            }
        }

        /**
         * Defaults to slot 0  //Some other teams have a complicated slot configuration but I just have this simple default until I learn more 
         *
         * @param kP //Params are unnecessary, they are just used for navigation and readability here. 
         * @param kI
         * @param kD
         */
       
        public void configPIDGains(double kP, double kI, double kD) { //Super simple functions to set the PID gains. 
            configPID(kP, kI, kD);
        }

        public void configForwardGains(double kV, double kA, double kS, double kG){
            configGains(kV, kA, kS, kG);
        }

        private void configPID(double kP, double kI, double kD) {
            talonConfig.Slot0.kP = kP;
            talonConfig.Slot0.kI = kI;
            talonConfig.Slot0.kD = kD;
        }

        public void configGains(double  kV, double kA, double kS, double kG) {
            talonConfig.Slot0.kV = kV;
            talonConfig.Slot0.kA = kA;
            talonConfig.Slot0.kS = kS;
            talonConfig.Slot0.kG = kG;



        }
    }
}


  






    

