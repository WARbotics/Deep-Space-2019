package frc.robot.components;

import  edu.wpi.first.wpilibj.VictorSP;


public class LinearSlider{
    VictorSP motor = new VictorSP(5); // Check if the motor port is correct
    double speed; 
    LinearSlider.SliderState state;
    double height = 0; // Holds the height of linear Slider (0 is the down state)
    double sliderMaxHeight = 400; // Find this out 
    enum SliderState {
        RAISING, LOWERING, STILL
    }

    private void setState(SliderState state){
        this.state = state;
    }   
    public void raise(){
        if(getState() == SliderState.RAISING){
            // Makes sure that you don't raise the slider higher than the max 
            if(0 <= height && height < sliderMaxHeight){
            }else{
                setState(SliderState.STILL);
            }
        }else{
            setState(SliderState.RAISING);
        }

    }
    public void lower(){
        if (getState() == SliderState.LOWERING){
            // You cannot lower below the bottom 
            if(0< height){
            }else{
                setState(SliderState.STILL);
            }
        }else{
            setState(SliderState.LOWERING);
        }

    }
    public double getHeight(){
        return this.height;
    }
    public LinearSlider.SliderState getState(){
        return this.state;
    }
    public void setMotorSpeed(double speed){
        this.motor.set(speed);
    }
    public boolean isRaising(){
        if(getState() == SliderState.RAISING){
            return true;
        }else{
            return false;
        }
    }
}   