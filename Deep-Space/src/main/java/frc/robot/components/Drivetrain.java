package frc.robot.components;
import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import java.util.HashMap;

public class Drivetrain {
    // TODO: Check that VictorSP port number are corret

    public PWMVictorSPX motor0 = new PWMVictorSPX(1);
    public PWMVictorSPX motor1 = new PWMVictorSPX(2);
    public PWMVictorSPX motor2 = new PWMVictorSPX(3);
    public PWMVictorSPX motor3 = new PWMVictorSPX(4);
    public PWMVictorSPX[] motors = {motor0, motor1, motor2, motor3};
    HashMap <PWMVictorSPX, Boolean> motorStatus = new HashMap<>();
    public SpeedControllerGroup m_Right = new SpeedControllerGroup(motor3, motor2);
    public SpeedControllerGroup m_Left = new SpeedControllerGroup(motor0, motor1);
    public DifferentialDrive m_Drive = new DifferentialDrive(m_Left, m_Right);
    
    public double speed = 0; 
    public double rotation = 0;
    
    public boolean isMoving = !((speed == 0) && (speed== 0)); 

    public void setSpeed(double speed){
       this.speed = speed; 
    }
    public void setRotation(double rotation){
        this.rotation = rotation; 
    }
    public boolean isMoving(){
        return isMoving;
    }
    public void move(){
        m_Drive.arcadeDrive(speed, rotation);
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
    public void setMotorsInverted(){
        motor0.setInverted(true);
        motor1.setInverted(true);
    }
}