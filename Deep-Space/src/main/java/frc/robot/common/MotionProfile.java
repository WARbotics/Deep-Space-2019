package frc.robot.common;

import java.lang.Math;

public class MotionProfile{

    double thresholdPrecent = 0; 
    int motorCycle = 0;
    double percentIncrease;
    double limit; // The maxium speed the motor should gain to.

    public MotionProfile(double percentIncrease, double limit){
        this.percentIncrease = percentIncrease;
        this.limit = limit; 
    }
    private boolean isThresholdMax(){
        if(thresholdPrecent >= 1){
            return true;
        }else{
            return false;
        }
    }
    public double getMotorSpeed(){
        double motorSpeed = (motorCycle/10)* thresholdPrecent;
        if (isThresholdMax()){
            if (motorSpeed <= limit) {
                return motorSpeed;
            } else {
                return limit;
            }
        }else{
            return limit;
        }
    }

    public void cycle(){
        motorCycle++;
        thresholdPrecent += percentIncrease;
    }
    public void reset(){
        motorCycle = 0;
        thresholdPrecent = 0;
    }
    public int getMotorCycle(){
        return this.motorCycle;
    }

}