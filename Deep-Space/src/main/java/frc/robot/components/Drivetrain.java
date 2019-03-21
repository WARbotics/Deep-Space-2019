package frc.robot.components;

import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import java.util.HashMap;

public class Drivetrain {
    /**
     *  A hight object of the drivetrain
     */
    public PWMVictorSPX leftMotor;
    public PWMVictorSPX leftMotor1;
    public PWMVictorSPX rightMotor;
    public PWMVictorSPX rightMotor1;
    PWMVictorSPX[] motors = {leftMotor, leftMotor1, rightMotor, rightMotor1};
    HashMap <PWMVictorSPX, Boolean> motorStatus = new HashMap<>();
    public SpeedControllerGroup m_Right;
    public SpeedControllerGroup m_Left;
    public DifferentialDrive m_Drive;
    

    public Drivetrain(PWMVictorSPX leftMotor, PWMVictorSPX leftMotor1, PWMVictorSPX rightMotor, PWMVictorSPX rightMotor1){
        this.leftMotor = leftMotor; 
        this.leftMotor1 = leftMotor1;
        this.rightMotor = rightMotor;
        this.rightMotor1 = rightMotor1;
        this.m_Right = new SpeedControllerGroup(rightMotor, rightMotor1);
        this.m_Left = new SpeedControllerGroup(leftMotor, leftMotor1);
        this.m_Drive = new DifferentialDrive(m_Left, m_Right);
    }

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