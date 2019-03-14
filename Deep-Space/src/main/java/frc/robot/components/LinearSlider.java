package frc.robot.components;

import  edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.common.LinearPID;

public class LinearSlider{
    /**
     * LinerSlider object used with one motor
     */
    public PWMVictorSPX motor = new PWMVictorSPX(6);; // Check if the motor port is correct
    LinearPID PID = new LinearPID(0, 0, 0); // Tune this
    public float height = 0; // Data from a potentionometer that we dont  have  <--- need to implement
    double maxHeight; // Find this height out 
    boolean isMoving; 
    public Position position = Position.BOTTOM;


    public enum Position{
        BOTTOM(0), MIDDLE(200), TOP(400);

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



}   


