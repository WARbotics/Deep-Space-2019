package frc.robot.components;

import  edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.common.LinearPID;

public class LinearSlider{
    /*
     - A function that makes the robot return back down to the base 
    */
    PWMVictorSPX motor;; // Check if the motor port is correct
    LinearPID PID = new LinearPID(0, 0, 0); // Tune this
    public float height = 0; 
    public float maxHeight; // Find this height out 
    boolean isMoving; 
    public Position position = Position.BOTTOM;

    public LinearSlider(PWMVictorSPX motor){
        this.motor = motor; 
    }

    public enum Position{
        BOTTOM(0), MIDDLE(255), TOP(400);

        private final int value;

        Position(final int newValue) {
            value = newValue;
        }

        public int getValue(){
            return value;
        }

    }

}   