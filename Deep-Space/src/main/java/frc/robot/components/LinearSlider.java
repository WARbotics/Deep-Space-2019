package frc.robot.components;
import  edu.wpi.first.wpilibj.VictorSP;

public class LinearSlider{
    /*
     The state of the linear could be done by if the linear slider is the moving up and down via 
     constant rate then that means that we can do the math and have a varible that is being added 
     and when it hits a value it means it is at the top and will have flag. idk 
    */
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