package frc.robot.components;

import  edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.common.LinearPID;

public class LinearSlider{
    PWMVictorSPX motor = new PWMVictorSPX(5); // Check if the motor port is correct
    LinearPID PID = new LinearPID(1, 1, 1);
    public float height = 0; 
    public float maxHeight; // Find this height out 
    boolean isMoving; 

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

    public void moveToPosition(LinearSlider.Position position){
        PID.setPoint(position.getValue());
        PID.PID(height);
        motor.set(PID.getRCW());
    }
    /*
    manual mode for up and down (raise and lower)

    */
}   