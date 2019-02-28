package frc.robot.common;


public class TurnRamp{

    double maxChange;
    double topChange = 0.02;
    double turnSpeed;
    public TurnRamp(double maxChange){
        this.maxChange = maxChange;
    }

    public double getSpeed(double input){
        if(input > (turnSpeed + maxChange)){
            if((turnSpeed - maxChange) > .6 ){
                turnSpeed += topChange;
            }
            turnSpeed += maxChange;
        }else if(input < (turnSpeed - maxChange)){
            if ((turnSpeed - maxChange) < -.6){
                turnSpeed -= topChange;
            }
        }else{
            turnSpeed= input;
        }
        return turnSpeed;
    }

}