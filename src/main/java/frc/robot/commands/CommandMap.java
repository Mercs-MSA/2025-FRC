package frc.robot.commands;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class CommandMap {
    private Map<String, Command> m_map = Map.ofEntries(
        Map.entry("Command 111", Commands.print("Command one was selected!")),
        Map.entry("Command 222", Commands.print("Command two was selected!")),
        Map.entry("Command 333", Commands.print("Command three was selected!")),
        Map.entry("Command 444", Commands.print("Command four was selected!")),
        Map.entry("Command 555", Commands.print("Command five was selected!")));

    public Map<String, Command> getMap() {
        return m_map;
    }
}
