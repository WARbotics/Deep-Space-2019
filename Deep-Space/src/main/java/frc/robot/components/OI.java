package frc.robot.components; 

import edu.wpi.first.wpilibj.Joystick;

public class OI{
    public Joystick driver;
    public Joystick operator;
    public DriveModes driveMode = DriveModes.DEFAULT;

    public OI(Joystick driver, Joystick operator){
        this.driver = driver; 
        this.operator = operator; 
    }
    public enum DriveModes{ 
        PRECISION, SPEED, DEFAULT;
    }
    public void setDriveMode(DriveModes mode){ 
        this.driveMode = mode;
    }
    
}