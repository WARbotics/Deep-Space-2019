package frc.robot.common;

import java.util.HashMap;
import java.lang.Math;

public class MotorProfile{
    /*
    State machine 
     - that holds different Profiles 
    Create an s-curve that modifies the input and returns the speed 
    Create a function that increases the percent gain per iteration 
    create a function that creates the speed and gives it to the manuplator -1.0 -> 1.0
    */
    int cycle;
    float percentIncrease;
    float percentMax; 

    public MotorProfile(float percentIncrease, ){
        this.percentIncrease = percentIncrease;
    }

    public void cycle(){
        if(percentMax<1){percentMax += percentIncrease;}
    }
    public void reset(){
        percentMax = 0; 
    }

    private double sCurve(double inputSpeed){
        // For going up
        if(inputSpeed>0){
            // Positive
            return (1/1+(Math.pow(Math.E, (-inputSpeed*2))
        }else{
            // negative
        }
    }
}