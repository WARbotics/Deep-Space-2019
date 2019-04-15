package frc.robot.common;

public class PID{ 
    double P, I, D;
    float setPoint = 0;
    double integral, previous_error;
    double rate;

    public PID(double P, double I, double D){ 
        this.P = P;
        this.I = I;
        this.D = D;
    }

    public void setPoint(int setPoint) {
        this.setPoint = setPoint;
    }

    public void setActual(double  actual){
        double error = (setPoint - actual);
        this.integral += (error * I);
        double derivative = (error - this.previous_error) / .02;
        this.rate = (P * error) + (I * this.integral)+  (D *derivative);
    }

    public double getRate() {
        return this.rate;
    }
}