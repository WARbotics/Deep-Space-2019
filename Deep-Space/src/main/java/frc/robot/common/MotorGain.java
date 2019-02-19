package frc.robot.common;

public class MotorGain{

    double thresholdGain= 0; 
    int motorCycle = 0;
    double percentGain;
    double limit; // The maxium speed the motor should gain to.

    public MotorGain(double percentGain, double limit){
        this.percentGain = percentGain;
        this.limit = limit; 
    }

    public double getMotorSpeed(){
        double motorSpeed = (motorCycle/10)*thresholdGain;
        if(motorSpeed <= limit){
            return motorSpeed;
        }else{
            return limit;
        }
    }

    public void cycle(){
        motorCycle++;
        thresholdGain += percentGain;
    }
    public void reset(){
        motorCycle = 0;
        thresholdGain = 0;
    }
    public int getMotorCycle(){
        return this.motorCycle;
    }

}