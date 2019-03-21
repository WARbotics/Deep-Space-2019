package frc.robot.common;

public class TurnPID{
    double P,I,D;
    double integral , previous_error;
    double setpoint = 0;
    double rcw;

    public TurnPID(double P, double I, double D){
        this.P = P;
        this.I = I; 
        this.D = D; 
    }
    public void setPoint(double setPoint){
        this.setpoint = setPoint; 
    }
    public void PID(double angle){
        double error = (setpoint - angle);// Error = Target - Actual
        this.integral += (error * .02);
        double derivative = (error - this.previous_error) / .02;
        this.rcw = P * error + I * this.integral + D * derivative;
    }

    public double getRCW(){
        return this.rcw;
    }

}