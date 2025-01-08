package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Beambreak {
    DigitalInput input = new DigitalInput(0);
    public Beambreak() {
    }

    public boolean checkBreak() {
        return input.get();
    }
}
