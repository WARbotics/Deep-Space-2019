package frc.robot.components;

import  edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.common.LinearPID;

public class LinearSlider{
    /*
     - A function that makes the robot return back down to the base 
    */
    PWMVictorSPX motor = new PWMVictorSPX(5); // Check if the motor port is correct
    LinearPID PID = new LinearPID(0, 0, 0); // Tune this
    public float height = 0; 
    public float maxHeight; // Find this height out 
    boolean isMoving; 

    public Position position = Position.BOTTOM;

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
        // A write a function that make sure they only go up or down one level

        PID.setPoint(position.getValue());
        PID.PID(height);
        motor.set(PID.getRCW());
        if(height < position.getValue()){
            this.isMoving = true;
        }
    }

    private void setPosition(Position position){
        this.position = position;
    }

    public void resetToBottom(){
        if(height>0){
            PID.setPoint(this.position.getValue());
            PID.PID(height);
            motor.set(PID.getRCW());
            this.isMoving = true;
        }
    }
}   