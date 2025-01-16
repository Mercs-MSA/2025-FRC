package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ScoringConstants;
import frc.robot.Constants.ElevatorStages;

public class CommandChangeScoringMode extends Command {
    private final ElevatorStages target;
  
  public CommandChangeScoringMode(ElevatorStages t) {
    target = t;
  }

  @Override
  public void initialize() {
    ScoringConstants.currentScoringMode = target;
  }

  @Override
  public boolean isFinished(){
    return true;
  }
}