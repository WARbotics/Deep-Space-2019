package frc.robot.components;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
public class Shooter{ 
    
    PWMVictorSPX motor = new PWMVictorSPX(6); 
    PWMVictorSPX motor1 = new PWMVictorSPX(7); // Check position
    SpeedControllerGroup shooter = new SpeedControllerGroup(motor, motor1);
    /*
        WIP
        I coming back to this I just need to figure out how we are going to shoot the ball.
        bang-bang 
    */
    public void setSpeed(double speed){
        shooter.set(speed);
    }
    public void shoot(){
        shooter.set(.8);
    }
    public void intake(){
        shooter.set(-.8);
    }
}