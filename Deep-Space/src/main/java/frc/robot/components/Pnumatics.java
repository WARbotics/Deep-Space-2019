package frc.robot.components;
import edu.wpi.first.wpilibj.DoubleSolenoid;
public class Pnumatics{

    public DoubleSolenoid.Value state; 
    DoubleSolenoid m_solenoid;

    public Pnumatics(DoubleSolenoid solenoid){
        this.m_solenoid = solenoid;
    }

    public void setFoward(){
        this.m_solenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void setReversed(){
        this.m_solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public DoubleSolenoid.Value getState(){
        this.state = this.m_solenoid.get();
        return this.state;
    }
    
}