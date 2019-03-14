package frc.robot.components;

import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import java.util.HashMap;

public class Drivetrain {
    /**
     *  A hight object of the drivetrain
     */
    public PWMVictorSPX leftMotor = new PWMVictorSPX(1);
    public PWMVictorSPX leftMotor1 = new PWMVictorSPX(2);
    public PWMVictorSPX rightMotor = new PWMVictorSPX(3);
    public PWMVictorSPX rightMotor1 = new PWMVictorSPX(5);
    PWMVictorSPX[] motors = {leftMotor, leftMotor1, rightMotor, rightMotor1};
    HashMap <PWMVictorSPX, Boolean> motorStatus = new HashMap<>();
    public SpeedControllerGroup m_Right = new SpeedControllerGroup(rightMotor, rightMotor1);
    public SpeedControllerGroup m_Left = new SpeedControllerGroup(leftMotor,leftMotor1);
    public DifferentialDrive m_Drive = new DifferentialDrive(m_Left,m_Right);

    private void setMotorStatus(){
        for(PWMVictorSPX motor : motors){
            motorStatus.put(motor ,motor.isAlive());
        }
    }

    public HashMap<PWMVictorSPX, Boolean> getMotorStatus(){
        setMotorStatus();
        return motorStatus; 
    }

    public void invertLeftMotors(){
        leftMotor.setInverted(true);
        leftMotor1.setInverted(true);
    }
    
    public void invertRightMotors(){
        rightMotor.setInverted(true);
        rightMotor1.setInverted(true);
    }
}