package frc.robot.components;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class Pnumatics{
    /**
     * Higher level control over the Pnumatics using a  single DoubleSolenoid
     */
    public DoubleSolenoid m_solenoid;
    double latest = 0;
    boolean isMoving = false;

    public Pnumatics(DoubleSolenoid Solenoid){
        this.m_solenoid = Solenoid;
    }

    public void setFoward(){
        m_solenoid.set(DoubleSolenoid.Value.kForward);
        isMoving = true;
    }

    public void setReversed(){
        m_solenoid.set(DoubleSolenoid.Value.kReverse);
        isMoving = true;
    }

    public void setOff(){
        m_solenoid.set(DoubleSolenoid.Value.kOff);
    }

    public boolean isOpen(){
        // Returns if the pnuamtics are in a open state
        if(m_solenoid.get() == DoubleSolenoid.Value.kForward){
            return true;
        }else{ return false; }
    }

    public boolean isState(DoubleSolenoid.Value value){
        // Returns if the solenoid is at a certain state 
        if(m_solenoid.get() == value){
            return true; 
        }else{ return false; } 
    }

    public boolean isReady(){
        // Return if the pnumatics as fully closed or opened 
        if(isMoving){
            double now = Timer.getFPGATimestamp();
            if ((now-latest) > .5) {
                latest = now;
                isMoving = false; 
                return true;
            }
            return false;
        }else{
             return true;
        }
    }
}