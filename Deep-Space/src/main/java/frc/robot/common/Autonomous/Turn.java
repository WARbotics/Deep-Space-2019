package frc.robot.common.Autonomous; 

import com.kauailabs.navx.frc.AHRS;
import frc.robot.common.TurnPID;

public class Turn{
    /*
        A autonmouse function for turning 
    */

    AHRS navBoard;
    double actualAngle = navBoard.getAngle(); 
    TurnPID PID = new TurnPID(0,0,0); // Tune this
    public Turn(AHRS navBoard){
        this.navBoard = navBoard; 
    }

    public double angle(Double angle){
        PID.setPoint(angle);
        PID.PID(actualAngle);
        return PID.getRCW();
    }
}