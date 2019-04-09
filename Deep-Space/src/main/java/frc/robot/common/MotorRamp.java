package frc.robot.common; 

//import edu.wpi.first.wpilibj.Timer;

public class MotorRamp{
    /**
     *  MotorRamp 
     */
    double acceleration;
    double maxChange = .01;
    double speed; 

    public MotorRamp(double acceleration){
        this.acceleration = acceleration; 
    }

    public double getSpeed(double input){
        if(input > (speed+acceleration)){
            if((speed+acceleration)> .5){
                 speed+= maxChange;
            }else{
                speed+=acceleration;
            }
        }else if(input < (speed - acceleration)){
            if((speed-acceleration)< -.5){
                speed += maxChange;
            }else{
                speed += acceleration;
            }
        }else{
            speed = input;
        }
        return speed;
    }
}   