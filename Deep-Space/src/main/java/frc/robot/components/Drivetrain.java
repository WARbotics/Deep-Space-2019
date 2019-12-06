package frc.robot.components;

import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import java.util.HashMap;

public class Drivetrain {
    /**
     *  A hight object of the drivetrain
     */
    private VictorSP leftMotor;
    private VictorSP leftMotor1;
    private VictorSP rightMotor;
    private VictorSP rightMotor1;
    VictorSP[] motors = {leftMotor, leftMotor1, rightMotor, rightMotor1};
    HashMap <VictorSP, Boolean> motorStatus = new HashMap<>();
    public SpeedControllerGroup m_Right;
    public SpeedControllerGroup m_Left;
    public DifferentialDrive m_Drive;
    

    public Drivetrain(VictorSP leftMotor, VictorSP leftMotor1, VictorSP rightMotor, VictorSP rightMotor1){
        this.leftMotor = leftMotor; 
        this.leftMotor1 = leftMotor1;
        this.rightMotor = rightMotor;
        this.rightMotor1 = rightMotor1;
        this.m_Right = new SpeedControllerGroup(rightMotor, rightMotor1);
        this.m_Left = new SpeedControllerGroup(leftMotor, leftMotor1);
        this.m_Drive = new DifferentialDrive(m_Left, m_Right);
    }

    private void setMotorStatus(){
        for(VictorSP motor : motors){
            motorStatus.put(motor ,motor.isAlive());
        }
    }

    public HashMap<VictorSP, Boolean> getMotorStatus(){
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