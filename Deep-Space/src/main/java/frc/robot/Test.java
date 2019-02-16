package frc.robot;

import edu.wpi.first.wpilibj.Compressor;

public class Test {
    // The test case for the robot go inside of here so it makes it easy to hold all in one place
    /*
    TODO:

     -Create a network table here to hold values to check things
    */ 

    public boolean isTestFinished = false;

    public String testCompressor(){
        Compressor testCompressor = new Compressor(0); 
        if (!testCompressor.enabled()){
            testCompressor.close();
            return "[X] Compressor has is not enabled";
        }else {
            testCompressor.close();
            return "[√] Compressor has enabled is working";
        }
    }






}