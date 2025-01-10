package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TestIntakePivot;
import frc.robot.Constants.TestIntakePivotConstants;
import frc.robot.Constants;
import frc.robot.Constants.*;

public class IntakePivotMoveToPos extends Command {
    private TestIntakePivot m_testIntakePivot = new TestIntakePivot(TestIntakePivotConstants.attached);
    private double pos;

    public IntakePivotMoveToPos(double pos) {
        this.pos = pos;
        addRequirements(m_testIntakePivot);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        m_testIntakePivot.testMotorGoToPosition(pos);
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
    }

    @Override 
    public boolean isFinished() {
        
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        // In this example, it will just instantly come out as true and stop the command as soon as it's called.
        return Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol);
    }
}
