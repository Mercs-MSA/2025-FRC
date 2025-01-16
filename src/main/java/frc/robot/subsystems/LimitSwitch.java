package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.DigitalOutput;

import edu.wpi.first.math.filter.Debouncer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Elevator1;
import frc.robot.subsystems.Elevator2;




public class LimitSwitch extends SubsystemBase{
    static DigitalInput m_limitSwitch = new DigitalInput(0);  /// make sure to specify the channel


    Debouncer m_debouncer = new Debouncer(0.05, Debouncer.DebounceType.kBoth);

    
    



    Boolean output, debouncedOutput;


    public LimitSwitch() {
    }

    public static boolean checkSwitch() {
        return m_limitSwitch.get(); //I think that the beambreak is in an open state most of the time
    }

    

    @Override
    public void periodic() {

        output = m_limitSwitch.get();   // Read the digital output directly
        debouncedOutput = m_debouncer.calculate(m_limitSwitch.get());  // Read the same input, but filter it
        SmartDashboard.putBoolean("Switch Triggered", checkSwitch());

        


    }

}
