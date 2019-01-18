package frc.robot.components; 

import edu.wpi.first.wpilibj.Joystick;

public class OI{

    public Joystick driver = new Joystick(0);
    public boolean linearSliderManual = false; 
    public boolean linearSliderPreset = !linearSliderManual;

    public void setLinearSliderManualState(boolean state ){
        this.linearSliderManual = state;
    }
    
}