package frc.robot.components;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;

public class Shooter{ 
    /**
     *  A High level shooter object for a two motor intake and shooter
     */
    double latest;
    public PWMVictorSPX leftMotor = new PWMVictorSPX(1); 
    public PWMVictorSPX rightMotor = new PWMVictorSPX(5);
    public SpeedControllerGroup shooter = new SpeedControllerGroup(leftMotor, rightMotor);

    double motorTime = .5; // The default time the motor will spin for 

    public void setSpeed(double speed){
        shooter.set(speed);
    }

    public void shoot(){
        double now = Timer.getFPGATimestamp();
        if((now-latest) > motorTime){
            latest = now;
            shooter.set(0); 
        }else{
            shooter.set(.8);
        }
    }

    public void intake(){
        double now = Timer.getFPGATimestamp();
        if((now-latest) > motorTime){
            latest = now;
            shooter.set(0); 
        }else{
            shooter.set(-.8);
        }
    }
}