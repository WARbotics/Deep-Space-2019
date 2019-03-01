package frc.robot.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import edu.wpi.first.wpilibj.Timer;

public class Logger{
    // WA Robotics Logging system that logs to a text file.
    // FORMAT: [  MESSAGE TYPE  ] MATCHTIME : MESSAGE
    File file; 
    FileWriter writer;
    boolean isFileCreated = false;
    String dirFile;
    String fileName = dirFile + "log.txt";  // Find out what usb file path are 
    Double matchTime = Timer.getMatchTime();
    
    public Logger(){

    }
    private void createFile() throws IOException{
        // Creates the file if the file is not created 
        if(!isFileCreated){
            file = new File(fileName);
            writer = new FileWriter(file);
            try{
                file.createNewFile();
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
    public void debug(String message) throws IOException {
        // Log a debug message
        if(isFileCreated){
            writer.write("[  DEBUG  ] "+matchTime +": "+message);
        }else{
            try{
               createFile();
               debug(message);
            }catch(IOException e){
                loggerError(e);
            }
        }

    }
    public void error(String message) throws IOException{
        // Logs an error message
        if (isFileCreated) {
            writer.write("[  ERROR  ] " + matchTime + ": " + message);
        } else {
            try {
                createFile();
                error(message);
            } catch (IOException e) {
                loggerError(e);
            }
        }
        // Log a error  message
    }
    public void info(String message) throws IOException{
        // Log an info message
        if (isFileCreated) {
            writer.write("[  INFO  ] " + matchTime + ": " + message);
        } else {
            try {
                createFile();
                info(message);
            } catch (IOException e) {
                loggerError(e);
            }
        }
    }
    public void warn(String message) throws IOException{
        // Log a warn message
        if (isFileCreated) {
            writer.write("[  WARN  ] " + matchTime + ": " + message);
        } else {
            try {
                createFile();
                warn(message);
            } catch (IOException e) {
                loggerError(e);
            }
        }
    }
    public void loggerError(IOException e){
        System.out.println("Logger Error: "+ e);
    }
}