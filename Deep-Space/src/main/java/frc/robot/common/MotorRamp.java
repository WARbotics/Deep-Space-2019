package frc.robot.common; 

import edu.wpi.first.wpilibj.Timer;

public class MotorRamp{
    /**
     *  MotorRamp 
     */
    double maxChange;
    double topChange = .01;
    double speed; 

    public MotorRamp(double maxChange){
        this.maxChange = maxChange; 
    }

    public double getSpeed(double input){
        if(input > (speed+ maxChange)){
            if((speed+maxChange)> .6){
                speed+= topChange;
            }else{
                speed += maxChange;
            }
        }else if(input < (speed - maxChange)){
            if((speed-maxChange)< -.6){
                speed += topChange;
            }else{
                speed -= maxChange;
            }
        }else{
            speed = input;
        }
        return speed;
    }
}   