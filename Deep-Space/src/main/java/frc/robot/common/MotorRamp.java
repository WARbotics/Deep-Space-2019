package frc.robot.common; 

import edu.wpi.first.wpilibj.Timer;

public class MotorRamp{
    /**
     *  MotorRamp 
     */
    double dChange;
    double topChange = .01;
    double speed; 

    public MotorRamp(double dChange){
        this.dChange = dChange; 
    }

    public double getSpeed(double input){
        if(input > (speed+ dChange)){
            if((speed+dChange)> .4){
                speed+= topChange;
            }else{
                speed += dChange;
            }
        }else if(input < (speed - dChange)){
            if((speed-dChange)< -.4){
                speed += topChange;
            }else{
                speed -= dChange;
            }
        }else{
            speed = input;
        }
        return speed;
    }
}   