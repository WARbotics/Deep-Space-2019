package frc.robot.components;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class Pnumatics{


    public DoubleSolenoid m_solenoid;
    double latest = 0;
    public Pnumatics(DoubleSolenoid Solenoid){
        this.m_solenoid = Solenoid;
    }
    public void setFoward(){
        m_solenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void setReversed(){
        m_solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setOff(){
        m_solenoid.set(DoubleSolenoid.Value.kOff);
    }
    public boolean isOpen(){
        if(m_solenoid.get() == DoubleSolenoid.Value.kForward){
            return true;
        }else{
            return false;
        }
    }
    public boolean isReady(){
        double now = Timer.getFPGATimestamp();
        if ((now-latest) > .5) {
            latest = now;
            return true;
        }
        return false;
    }
}