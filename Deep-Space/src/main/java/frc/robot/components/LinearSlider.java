package frc.robot.components;

import  edu.wpi.first.wpilibj.VictorSP;
import frc.robot.common.PID;

public class LinearSlider{
    VictorSP motor = new VictorSP(5); // Check if the motor port is correct
    double speed; 
    LinearSlider.State state;
    double height = 0; // Holds the height of linear Slider (0 is the down state)
    double sliderMaxHeight = 400; // Find this out 
    enum State {
        RAISING, LOWERING, STILL
    }
    enum Position {
        BOTTOM, MIDDLE, TOP
    }
    /*
    Motor profile for the linear slider 
    */

    private void setState(State state){
        this.state = state;
    }  
    public void moveToPositon(Position position){
        switch(position){
            case BOTTOM:
                break;
            case MIDDLE:
                break; 
            case TOP:
                break;
        }
    } 
    
    public void raise(){
    }
    public void lower(){
    }
    public double getHeight(){
        return this.height;
    }

    public LinearSlider.State getState(){
        return this.state;
    }
    public void setMotorSpeed(double speed){
        this.motor.set(speed);
    }
    public boolean isRaising(){
        if(getState() == State.RAISING){
            return true;
        }else{
            return false;
        }
    }
}   