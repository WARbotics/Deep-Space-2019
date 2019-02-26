package frc.robot.common; 

public class PID{
    double P, I, D;
    double setPoint = 0;


    public PID(double P, double I, double D){
        this.P = P; 
        this.I = I;
        this.D = D; 
    }

    public void setSetPoint(double setPoint ){
        this.setPoint = setPoint;
    }

    public void PID(){
        
    }
}