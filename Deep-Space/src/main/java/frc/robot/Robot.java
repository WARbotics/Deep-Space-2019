/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.Drivetrain;
import frc.robot.components.OI;
import frc.robot.components.Pnumatics;
import frc.robot.common.ButtonDebouncer;
import frc.robot.components.OI.DriveModes;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.common.MotorRamp;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.common.PID;
//import frc.robot.common.Logger;

/**
 *  [] Places logger points through out the files
 *  [] Linear slider
 *  [] Auto turn 
 *  [] auto play generator based abstrasct moves 
 *  [] Manual mode 
 *  [] controll modes 
 *  [] Display this data
*/

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 **/
public class Robot extends TimedRobot {
  Drivetrain drive;
  OI input;
  Pnumatics beak;  
  AHRS navXMicro; 
  MotorRamp fowardRamp;
  ButtonDebouncer beakButtonOpen; 
  ButtonDebouncer beakButtonClose;
  ButtonDebouncer shootButtton;
  ButtonDebouncer intakeButton;
  PWMVictorSPX shooterWinch;
  PID drivetrainPID;
  double lastestAutoTime;

  PowerDistributionPanel PDP; 
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  //Logger logger; 


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Camera 
    CameraServer.getInstance().startAutomaticCapture();

    // Drivetrain
    PWMVictorSPX driveMotorLeft = new PWMVictorSPX(8);
    PWMVictorSPX driveMotorLeft1 = new PWMVictorSPX(3);
    PWMVictorSPX driveMotorRight = new PWMVictorSPX(7);
    PWMVictorSPX driveMotorRight1 = new PWMVictorSPX(6);
    drive = new Drivetrain(driveMotorLeft, driveMotorLeft1, driveMotorRight, driveMotorRight1);


    // Drivetrain PID
    drivetrainPID = new PID(.1, 0, .01);

    //Joysticks 
    Joystick driverStick = new Joystick(0);
    Joystick operatorStick = new Joystick(1);
    input = new OI(driverStick, operatorStick);

    // Pnumatics
    DoubleSolenoid beakSolenoid = new DoubleSolenoid(4, 5);
    beak = new Pnumatics(beakSolenoid);

    // NAV
    /*
    navXMicro = new AHRS(Port.kUSB);
    navXMicro.reset();
    */
    
    // Debouncer 
    beakButtonOpen = new ButtonDebouncer(input.operator,1, .5);
    beakButtonClose = new ButtonDebouncer(input.operator, 2, .5);
    shootButtton = new ButtonDebouncer(input.operator, 5, .3);
    intakeButton = new ButtonDebouncer(input.operator, 6, .3);
    // PDP
    /*
    PDP = new PowerDistributionPanel(0);
    PDP.clearStickyFaults();
    */
    // SmartDasboard 
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Logger 
    //logger = new Logger("dev/U/sda"); // Check this file path
  }


  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch(m_autoSelected){
      case kDefaultAuto:
        teleopPeriodic();
      case kCustomAuto:
        double current = Timer.getFPGATimestamp();
        double lastest = 0;

        if((current - lastest) >= .5){
          drive.m_Drive.arcadeDrive(.3, 0);
          lastest = current;
        }
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double driveY  = -input.driver.getRawAxis(1);
    double driveX = input.driver.getRawAxis(0);
    double zRotation = input.driver.getRawAxis(2);
    double rightDriveY = input.driver.getRawAxis(3);
    if(input.driveMode == DriveModes.SPEED){
      // Speed drivemode
      if (driveY < .1 && driveY > -.1) {
        driveY = 0;
      }
      if (driveX < .1 && driveX > -.1) {
        driveX = 0;
      }
      drive.m_Drive.arcadeDrive(driveX*.75, driveY*.75);
    }else if(input.driveMode == DriveModes.TANK){
      // Precision drivemode
      if (driveY < .1 && driveY > -.1) {
        driveY = 0;
      }
      if(rightDriveY < .1 && rightDriveY > -.1){
        rightDriveY = 0;
      }
      drive.m_Drive.tankDrive(driveY*.75, -rightDriveY*.75);
    }else if (input.driveMode == DriveModes.PRECISION){
        // Precision drivemode
      if (driveY > .4) {
        driveY = .5;
      } else if (driveY < -.4) {
        driveY = -.5;
      }
      if (rightDriveY > .4) {
        rightDriveY = .5;
      } else if (rightDriveY < -.4) {
        rightDriveY = .5;
      }
      drive.m_Drive.tankDrive(driveY*.75, -rightDriveY*.75);
    }else{
      // Defult drive mode;
      if (driveY < .1 && driveY > -.1) {
        driveY = 0;
      }
      if (driveX < .1 && driveX > -.1) {
        driveX = 0;
      }
      drivetrainPID.setActual(-driveY * .45);
      drive.m_Drive.curvatureDrive(drivetrainPID.getRate()*.85, zRotation, false);
    }

    // Buttons for the changing drive modes 
    if (input.driver.getRawButton(1)){
      input.setDriveMode(DriveModes.PRECISION);
    }
    if (input.driver.getRawButton(2)){
      input.setDriveMode(DriveModes.DEFAULT);
    }
    if (input.operator.getRawButton(1)){
      // Opens the beak
      if(beakButtonOpen.isReady()){
        //logger.info("Beak is open");
        beak.setFoward();
      }
    }
    // Beak control
    if (input.operator.getRawButton(2)){
      // Close the beak
      if(beakButtonClose.isReady()){
        //logger.info("Beak is closed");
        beak.setReversed();
      }
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }
}


