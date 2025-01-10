package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TestIntakeFlywheels;
import frc.robot.subsystems.TestIntakePivot;
import frc.robot.Beambreak;
import frc.robot.Constants.TestIntakeFlywheelsConstants;
import frc.robot.Constants.TestIntakePivotConstants;

public class CommandIntakeWheelsCollect extends Command {
    private TestIntakeFlywheels m_testIntakeFlywheels = new TestIntakeFlywheels(TestIntakeFlywheelsConstants.attached);
    private Beambreak m_breambreak = new Beambreak();
    private double voltage;

    public CommandIntakeWheelsCollect(double voltage) {
        this.voltage = voltage;
        addRequirements(m_testIntakeFlywheels);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        m_testIntakeFlywheels.testVoltage(voltage);
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
        m_testIntakeFlywheels.testVoltage(0);
    }

    @Override 
    public boolean isFinished() {
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        return m_breambreak.checkBreak(); // Will check beambreak until it returns true (meaning it got broke)
    }
}
