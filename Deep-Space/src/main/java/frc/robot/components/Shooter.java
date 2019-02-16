package frc.robot.components;

import edu.wpi.first.wpilibj.VictorSP;

public class Shooter{ 

    public VictorSP shooterMotor1 = new VictorSP(4); 
    
    public void shoot(){
        shooterMotor1.set(.5);
    }
    public void noShoot(){
        shooterMotor1.set(0);
    }
}