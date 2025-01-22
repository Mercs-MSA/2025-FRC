package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.CommandMap;

public class ControlMapper {
    // This is the dashboard widget with a dropdown to select a Command
    private SendableChooser<String> mappingChooser = new SendableChooser<>();
    private Command mappedCommand =
        new SelectCommand<>(
            new CommandMap().getMap(),
            this::getMappedCommandKey
        );

    public ControlMapper(Trigger buttonTrigger, String title) {
        new CommandMap().getMap().forEach((key, value) -> {
            mappingChooser.setDefaultOption(key, key);
        });

        SmartDashboard.putData(title, mappingChooser);

        buttonTrigger.onTrue(mappedCommand);
    }

    public String getMappedCommandKey() {
        return mappingChooser.getSelected();
    }

    public Command getCommand() {
        return mappedCommand;
    }
}
