package frc.robot.components; 

import edu.wpi.first.wpilibj.Joystick;

public class OI{

    // public Joystick driver = new Joystick(0);
    public Joystick driver = new Joystick(0);
    public Joystick operator = new Joystick(1);

    public DriveModes driveMode = DriveModes.DEFAULT;
    boolean isSliderManual = false;
    
    public enum DriveModes{ 
        PRESCION, SPEED, DEFAULT;
    }
    public void setSliderToManual(){
        this.isSliderManual = true;
    }

    public void setDriveMode(DriveModes mode){ 
        this.driveMode = mode;
    }
}