package frc.robot.common; 

public class LinearPID{
    double P, I, D;
    float setPoint = 0;
    int integral, previous_error;
    double rcw;

    public LinearPID(double P, double I, double D){
        this.P = P; 
        this.I = I;
        this.D = D; 
    }

    public void setPoint(int setPoint){
        this.setPoint = setPoint;
    }

    public void PID(float actual){
        /*
        find error 
        */
        double error = (setPoint - actual);
        this.integral += (error * .02);
        double derivative = (error - this.previous_error) /.02;
        this.rcw = P * error + I * this.integral + D *derivative;
    }

    public double getRCW() {
        return this.rcw;
    }
}