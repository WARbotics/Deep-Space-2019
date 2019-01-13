package frc.robot.components; 

import edu.wpi.first.wpilibj.Joystick;

public class OI{

    public Joystick driver;
    public boolean linearSliderManual = false; 
    public boolean linearSliderPreset = !linearSliderManual;

    public OI(Joystick driver){
        this.driver = driver;
    }
    public void setLinearSliderManual(){
        this.linearSliderManual = true;
    }
    
}