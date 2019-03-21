package frc.robot.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class Logger{
    // WA Robotics Logging system that logs to a text file.
    // FORMAT: [  MESSAGE TYPE  ] MATCHTIME : MESSAGE
    File file; 
    FileWriter writer; 
    boolean isFileCreated = false; 
    String dirFile; // The base directory were the file will stored
    int matchNumber = DriverStation.getInstance().getMatchNumber();
    String fileName = dirFile + "log-"+matchNumber+".txt"; 
    Double matchTime = Timer.getMatchTime();

    public Logger(String dirFile){
        this.dirFile = dirFile;
    }

    private void createFile(){
        // Creates the file if the file is not created 
        if(!isFileCreated){
            try{
                file.createNewFile();
                file = new File(fileName);
                writer = new FileWriter(file);
                this.isFileCreated = true;
            }catch(IOException e){
                this.isFileCreated = false;
                loggerError(e);
            }
        }
    }

    public void setFileDir(String fileDir){ 
        // Sets the file path for the create file 
        // There should be a defult path aswell 
        this.dirFile = fileDir;
    }

    public void debug(String message) {
        // Log a debug message
        if(isFileCreated){
            try{
                writer.write("[  DEBUG  ] "+matchTime +": "+message);
            }catch (IOException e){
                loggerError(e);
            }
        }else{
            createFile();
            debug(message);
        }
    }
    
    public void error(String message) {
        // Logs an error message
        if (isFileCreated) {
            try{
                writer.write("[  ERROR  ] " + matchTime + ": " + message);
            }catch(IOException e){
                loggerError(e);
            }
        } else {
            createFile();
            error(message);
        }
    }
    
    public void info(String message){
        // Log an info message
        if (isFileCreated) {
            try{
                writer.write("[  INFO  ] " + matchTime + ": " + message);
            } catch (IOException e){
                loggerError(e);
            }
        } else {
            createFile();
            info(message);
        }
    }

    public void warn(String message){
        // Log a warn message
        if (isFileCreated) {
            try{
                writer.write("[  WARN  ] " + matchTime + ": " + message); 
            } catch (IOException e){
                loggerError(e);
            }
            
        } else {
            
                createFile();
                warn(message);
        }
    }

    public void loggerError(IOException e){
        System.out.println("Logger Error: "+ e);
    }
}