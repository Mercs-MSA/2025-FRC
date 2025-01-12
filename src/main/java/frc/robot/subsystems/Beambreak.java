package frc.robot.subsystems;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.AsynchronousInterrupt;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.beambreakConstants;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Beambreak extends SubsystemBase{
    private final DigitalInput m_beamBreak = new DigitalInput(Constants.beambreakConstants.beamBreakChannel);

    private boolean detectsCoral = false;

    BiConsumer<Boolean, Boolean> callback = (risingEdge, fallingEdge) -> {
        if (risingEdge){
            this.detectsCoral = false;
            // RobotContainer.stopEverything();
        }
        if (fallingEdge){
            this.detectsCoral = true;
            //RobotContainer.stopEverything();
            // RobotContainer.prepShooter();
        }
        // RobotContainer.stopEverything();
    };

    //this.detectsCoral = false;


    private AsynchronousInterrupt asynchronousInterrupt = new AsynchronousInterrupt(m_beamBreak, callback);



    

    public Beambreak() {
        asynchronousInterrupt.enable();

    }

    public boolean checkBreak() {
        return m_beamBreak.get();
    }

@Override
public void periodic(){
    this.detectsCoral = m_beamBreak.get();
    // SmartDashboard.putBoolean("Detects coral", detectsCoral);

}
}
