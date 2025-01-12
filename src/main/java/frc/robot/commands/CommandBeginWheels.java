package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.TestIntakeFlywheels;

public class CommandBeginWheels extends Command {

    private TestIntakeFlywheels m_testSubsystem;
    private double voltage;
    private boolean isStart;
    private boolean isIntake;

    public CommandBeginWheels(TestIntakeFlywheels i, double voltage, boolean isStart, boolean isIntake) {
        m_testSubsystem = i;
        this.voltage = voltage;
        this.isStart = isStart;
        this.isIntake = isIntake;
        addRequirements(m_testSubsystem);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        m_testSubsystem.applyVoltage(voltage * (isIntake ? -1.0 : 1.0) * (isStart ? 1 : 0));
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over

    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
        m_testSubsystem.applyVoltage(0);
    }

    @Override 
    public boolean isFinished() {
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        // In this example, it will just instantly come out as true and stop the command as soon as it's called.
        return true;
    }
}
