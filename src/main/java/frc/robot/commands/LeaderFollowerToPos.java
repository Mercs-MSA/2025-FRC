package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TestIntakePivot;
import frc.robot.subsystems.TestLeaderFollower;
import frc.robot.Constants.LeaderFollowerConstants;
import frc.robot.Constants;
import frc.robot.Constants.*;

public class LeaderFollowerToPos extends Command {
    private TestLeaderFollower m_testLeaderFollower = new TestLeaderFollower(LeaderFollowerConstants.attached);
    private double pos;

    public LeaderFollowerToPos(double pos) {
        this.pos = pos;
        addRequirements(m_testLeaderFollower);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        m_testLeaderFollower.testMotorGoToPosition(pos);
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
        // return Constants.isWithinTol(pos, m_testLeaderFollower.GetPosition(), Constants.LeaderFollowerConstants.tol);
        return true;
    }
}
