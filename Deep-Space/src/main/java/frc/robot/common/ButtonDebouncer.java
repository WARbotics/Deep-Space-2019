package frc.robot.common;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
public class ButtonDebouncer{
    Joystick joystick; 
    int buttonNum; 
    double latest;
    double debouncePeriod;

    public ButtonDebouncer(Joystick joystick, int buttonNum, double debouncePeriod){
        this.joystick = joystick;
        this.buttonNum = buttonNum;
        this.latest = 0; 
        this.debouncePeriod = debouncePeriod;
    }

    public void setDebouncePeriod(double debouncePeriod){
        this.debouncePeriod = debouncePeriod;
    }
    public boolean isReady(){
        double now = Timer.getFPGATimestamp();
        if (joystick.getRawButton(buttonNum)) {
            if ((now - latest) > debouncePeriod) {
                latest = now;
                return true;
            }
        }
        return false;

    }
}