package frc.robot.components;
import edu.wpi.first.wpilibj.drive.DifferentialDrive; 
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import java.util.HashMap;

public class Drivetrain {
    // TODO: Check that VictorSP port number are corret

    public VictorSP motor0 = new VictorSP(0);
    public VictorSP motor1 = new VictorSP(13);
    public VictorSP motor2 = new VictorSP(14);
    public VictorSP motor3 = new VictorSP(1);
    public VictorSP[] motors = {motor0, motor1, motor2, motor3};
    HashMap <VictorSP, Boolean> motorStatus = new HashMap<>();
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
        for(VictorSP motor : motors){
            motorStatus.put(motor ,motor.isAlive());
        }
    }
    public HashMap<VictorSP, Boolean> getMotorStatus(){
        setMotorStatus();
        return motorStatus; 
    }
    public void setMotorsInverted(){
        motor0.setInverted(true);
        motor1.setInverted(true);
    }
}