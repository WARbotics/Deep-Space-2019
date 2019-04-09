/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.Drivetrain;
import frc.robot.components.OI;
import frc.robot.components.Pnumatics;
import frc.robot.common.ButtonDebouncer;
import frc.robot.components.Shooter;
import frc.robot.components.OI.DriveModes;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.robot.common.TurnPID;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.common.MotorRamp;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
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
  TurnPID driveTurnPID;
  AHRS navXMicro; 
  MotorRamp fowardRamp;
  ButtonDebouncer beakButtonOpen; 
  ButtonDebouncer beakButtonClose;
  ButtonDebouncer shootButtton;
  ButtonDebouncer intakeButton; 
  Spark ballShooterLeft;
  Spark ballShooterRight;
  SpeedControllerGroup shooter;
  Spark basket;
  //NetworkTableInstance defaultTableInit; 
  //NetworkTableInstance visionTableInit;
  //NetworkTable visionTable;
  //NetworkTable defaultTable;
  PowerDistributionPanel PDP; 
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //private final int SHOOTER_UP = 5;
  //private final int SHOOTER_DOWN = 4;
  //Logger logger; 


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    // Drivetrain
    VictorSP driveMotorLeft = new VictorSP(1);
    VictorSP driveMotorLeft1 = new VictorSP(2);
    VictorSP driveMotorRight = new VictorSP(3);
    VictorSP driveMotorRight1 = new VictorSP(4);
    
    drive = new Drivetrain(driveMotorLeft, driveMotorLeft1, driveMotorRight, driveMotorRight1);
    drive.invertRightMotors();
    //Joysticks 
    Joystick driverStick = new Joystick(0);
    Joystick operatorStick = new Joystick(1);
    
    input = new OI(driverStick, operatorStick);
    // Pnumatics
  
    //    Shooter
    ballShooterLeft = new Spark(6);
    ballShooterRight = new Spark(7);
    shooter = new SpeedControllerGroup(ballShooterLeft, ballShooterRight);
    basket = new Spark(8);
    // Motion
    fowardRamp = new MotorRamp(0.001);

    // NAV
    navXMicro = new AHRS(Port.kUSB);
    navXMicro.reset();

    // DataTables 
    //  Java side will hold the datatable server becuase it is on the roborio
    /*
    defaultTableInit = NetworkTableInstance.getDefault();
    visionTableInit = NetworkTableInstance.create(); 
    defaultTable = defaultTableInit.getTable("datatables");
    visionTable = visionTableInit.getTable("vision");
    defaultTableInit.startClientTeam(6925);
    */
    // Debouncer 
    beakButtonOpen = new ButtonDebouncer(input.operator,1, .5);
    beakButtonClose = new ButtonDebouncer(input.operator, 2, .5);
    shootButtton = new ButtonDebouncer(input.operator, 5, .3);
    intakeButton = new ButtonDebouncer(input.operator, 6, .3);
    // PDP
    
    // SmartDasboard 
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Logger 
    //logger = new Logger("dev/U/sda"); // Check this file path
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
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
    teleopPeriodic();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double driveY  = -input.driver.getRawAxis(1);
    double driveX = input.driver.getRawAxis(0);
    double rightDriveY = input.driver.getRawAxis(3);
    if(input.driveMode == DriveModes.SPEED){
      // Speed
      if (driveY < .1 && driveY > -.1) {
        driveY = 0;
      }
      if (driveX < .1 && driveX > -.1) {
        driveX = 0;
      }
      drive.m_Drive.arcadeDrive(driveX*.85, driveY*.85);
    }else if(input.driveMode == DriveModes.PRECISION){
      // Precision
      if(driveY > .6){
        driveY = .6;
      }else if(driveY< -.6){
        driveY = -.6;
      }
      if(rightDriveY > .6){
        rightDriveY = .6;
      }else if(rightDriveY < -.6){
        rightDriveY = -.6;
      }
      drive.m_Drive.tankDrive(driveY, rightDriveY);
    }else{
      // Defult drive mode;
      if (driveY < .1 && driveY > -.1) {
        driveY = 0;
      }
      if (driveX < .1 && driveX > -.1) {
        driveX = 0;
      }
      drive.m_Drive.arcadeDrive(driveX*.75, driveY * .9);
    }
    // Thread this to 200 ms for the speed controller 

    if (input.driver.getRawButton(1)){
      input.setDriveMode(DriveModes.PRECISION);
    }
    if (input.driver.getRawButton(2)){
      input.setDriveMode(DriveModes.DEFAULT);
    }
    if (input.driver.getRawButton(3)){
      input.setDriveMode(DriveModes.SPEED);
    }
    if (input.driver.getRawButton(7)){
      basket.set(.7);
    }else{basket.set(0);}
    if (input.driver.getRawButton(6)){
      basket.set(-.7);
    }else{basket.set(0);}
    if(input.driver.getRawButton(5)){
      shooter.set(1);
    }else{shooter.set(0);}
    if(input.driver.getRawButton(4)){
      shooter.set(-1);
    }else{shooter.set(0);}
  
    /*
    if (input.operator.getRawButton(5)){
      // Shoot 
      ballShooter.shooter.set(-.75);
      
    }else{
      ballShooter.setSpeed(0);
    }
    if (input.operator.getRawButton(3)){
      ballShooter.shooter.set(.75);
      // intake
    }else{
      ballShooter.shooter.set(0);
    }
    */
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }
}


