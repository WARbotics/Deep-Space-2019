package frc.robot.components;
import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Drivetrain {
    // TODO: Check that VictorSP port number are corret
    VictorSP frontRightMotor;
    VictorSP frontLeftMotor;
    VictorSP backLeftMotor;
    VictorSP backRightMotor;

    SpeedControllerGroup m_Right;
    SpeedControllerGroup m_Left;
    DifferentialDrive m_Drive = new DifferentialDrive(m_Left, m_Right);
    
    public double fowardSpeed; 
    public double rotationSpeed;

    public Drivetrain(VictorSP frontLeftMotor, VictorSP frontRightMotor, VictorSP backLeftMotor, VictorSP backRightMotor){
        this.m_Right = new SpeedControllerGroup(frontRightMotor, backRightMotor);
        this.m_Left = new SpeedControllerGroup(frontLeftMotor, backLeftMotor); 
    }

    public void setSpeed(double foward, double rotation){
        this.fowardSpeed = foward; 
        this.rotationSpeed = rotation;
    }

    public void move(){
        m_Drive.arcadeDrive(this.fowardSpeed, this.rotationSpeed);
    }
}