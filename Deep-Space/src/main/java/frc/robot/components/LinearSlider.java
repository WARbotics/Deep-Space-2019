package frc.robot.components;
import  edu.wpi.first.wpilibj.VictorSP;

public class LinearSlider{
    VictorSP motor; 
    double speed;
    String state; 

    public LinearSlider(VictorSP motor){
        this.motor = motor;
    }
    private void setState(String state){
        this.state = state;
    }
    public void setSpeed(double speed){
        if (speed > 0){
            this.setState("raising");
        }else if( speed < 0){
            this.setState("lowering");
        }else{
            this.setState("Not moving");
        }
        this.speed = speed;
    }
    public void move(){
        this.setState("moving");
        this.motor.set(this.speed);
    }
}   