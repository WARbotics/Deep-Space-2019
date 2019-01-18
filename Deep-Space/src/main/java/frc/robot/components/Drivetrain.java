package frc.robot.components;
import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Drivetrain {
    // TODO: Check that VictorSP port number are corret
    public VictorSP frontLeftMotor = new VictorSP(0);
    public VictorSP frontRightMotor = new VictorSP(1);
    public VictorSP backLeftMotor = new VictorSP(2);
    public VictorSP backRightMotor = new VictorSP(3);

    public SpeedControllerGroup m_Right = new SpeedControllerGroup(frontRightMotor, backRightMotor);
    public SpeedControllerGroup m_Left = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
    public DifferentialDrive m_Drive = new DifferentialDrive(m_Left, m_Right);
    
    public double fowardSpeed; 
    public double rotationSpeed;
    
    public boolean isMoving = (fowardSpeed == 0) && (rotationSpeed== 0); 

    public void setSpeed(double foward, double rotation){
        fowardSpeed = foward; 
        rotationSpeed = rotation;
    }
    public boolean isMoving(){
        return isMoving;
    }
    public void move(){
        m_Drive.arcadeDrive(fowardSpeed, rotationSpeed);
    }
}