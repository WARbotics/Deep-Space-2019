package frc.robot;

public class RobotState{

    /*
    Global robot state  
    */
    public enum Motion{
       // Holds the state of the robot if it is moving or turning 
        MOVING, TURNING;
    }

    public enum Vision{
        SCANNING, READY; 
    }


}