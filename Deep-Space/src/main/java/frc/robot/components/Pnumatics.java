package frc.robot.components;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;

public class Pnumatics{

    public DoubleSolenoid m_solenoid = new DoubleSolenoid(0,1);
    public DoubleSolenoid.Value state; 
    double latest = 0;
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
        if(state == DoubleSolenoid.Value.kForward){
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