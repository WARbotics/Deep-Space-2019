package frc.robot.components; 

import edu.wpi.first.wpilibj.Joystick;

public class OI{

    public Joystick driver = new Joystick(0);

    public enum State{
        PRECISION, 
    }
    
    public double sinControl(){
        // Takes the controller values at makes them into SINE wave valeus 
    }

}