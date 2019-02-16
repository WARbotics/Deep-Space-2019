package frc.robot.components;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Pnumatics{

    DoubleSolenoid m_solenoid = new DoubleSolenoid(0,1);
    // public Compressor compressor = new Compressor();
    // Add a thing that return if the presure is too low and what the preasure actually is
    public void setFoward(){
        m_solenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void setReversed(){
        m_solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setOff(){
        m_solenoid.set(DoubleSolenoid.Value.kOff);
    }
    /*
    public boolean isCompressorOn(){
        return compressor.enabled();
    }
    */
}