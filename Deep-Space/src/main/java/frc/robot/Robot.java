/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.Drivetrain;
import frc.robot.components.OI;
import frc.robot.components.Pnumatics;
import frc.robot.common.ButtonDebouncer;
import frc.robot.components.Shooter;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.robot.common.TurnPID;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.common.MotorRamp;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.cameraserver.CameraServer;
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
  Pnumatics shooterPosition;
  Shooter ballShooter; 
  TurnPID driveTurnPID;
  AHRS navXMicro; 
  MotorRamp fowardRamp;
  ButtonDebouncer beakButtonOpen; 
  ButtonDebouncer beakButtonClose;
  ButtonDebouncer shootButtton;
  ButtonDebouncer intakeButton;
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
    // Camera 
    CameraServer.getInstance().startAutomaticCapture();
    // Drivetrain
    PWMVictorSPX driveMotorLeft = new PWMVictorSPX(4);
    PWMVictorSPX driveMotorLeft1 = new PWMVictorSPX(3);
    PWMVictorSPX driveMotorRight = new PWMVictorSPX(7);
    PWMVictorSPX driveMotorRight1 = new PWMVictorSPX(6);

    drive = new Drivetrain(driveMotorLeft, driveMotorLeft1, driveMotorRight, driveMotorRight1);
    drive.invertRightMotors();
    //Joysticks 
    Joystick driverStick = new Joystick(0);
    Joystick operatorStick = new Joystick(1);
    
    input = new OI(driverStick, operatorStick);
    // Pnumatics
    DoubleSolenoid beakSolenoid = new DoubleSolenoid(4, 5);
    DoubleSolenoid shooterSolenoid = new DoubleSolenoid(0,1);
    beak = new Pnumatics(beakSolenoid);
    shooterPosition = new Pnumatics(shooterSolenoid);
    //    Shooter
    PWMVictorSPX leftShooter = new PWMVictorSPX(1);
    PWMVictorSPX rightShooter = new PWMVictorSPX(5);
    PWMVictorSPX shooterWinch = new PWMVictorSPX(0);

    ballShooter = new Shooter(leftShooter,rightShooter, .8, shooterWinch);
    

    
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
    
    PDP = new PowerDistributionPanel(0);
    PDP.clearStickyFaults();
    
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
    if(driveY < .1 && driveY >-.1){
      driveY = 0;
    }
    if(driveX < .1 && driveX > -.1 ){
      driveX = 0;
    }
    drive.m_Drive.arcadeDrive(driveX*.7, driveY*.9);

    // Thread this to 200 ms for the speed controller 

    if (input.operator.getRawButton(1)){
      // Opens the beak
      if(beakButtonOpen.isReady()){
        //logger.info("Beak is open");
        beak.setFoward();
      }
    }
    if (input.operator.getRawButton(2)){
      // Close the beak
      if(beakButtonClose.isReady()){
        //logger.info("Beak is closed");
        beak.setReversed();
      }
    } 
    if (input.operator.getRawButton(5)){
      // Shoot 
      ballShooter.shooter.set(.25);
    }else{
      ballShooter.shooter.set(0);
    }
    if (input.operator.getRawButton(3)){
      ballShooter.shooter.set(-.23);
      // intake
    }else{
      ballShooter.shooter.set(0);
    }
    if (input.operator.getRawButton(6)){
      shooterPosition.m_solenoid.set(DoubleSolenoid.Value.kReverse);
      // pnuamtics to shoot
    }
    if (input.operator.getRawButton(4)){
      // pnumatics to intakes
      shooterPosition.m_solenoid.set(DoubleSolenoid.Value.kForward);
    }

    if (input.operator.getRawButton(6)) {
      ballShooter.setWinchSpeed(-1.50);
    } else if (input.operator.getRawButton(4)) {
      ballShooter.setWinchSpeed(1.50);
    } else {
      ballShooter.setWinchSpeed(0);
    }
    // Automatic way that has not been tested 
    /*
    if (input.operator.getRawButton(3)){
      // Check to see if the shooter is up and if so then it allow the user to shoot
      if(shootButtton.isReady()){
        if(shooterPosition.isState(DoubleSolenoid.Value.kForward) || shooterPosition.isState(DoubleSolenoid.Value.kOff)){
          //logger.info("Shoot the ball");
          ballShooter.shoot();
        }else{
          shooterPosition.setReversed();
        }
      }
    }
    if (input.operator.getRawButton(4)) {
      // Check to see if the shooter is down and if so then will allow the user to intake
      if (intakeButton.isReady()) {
        if(shooterPosition.isState(DoubleSolenoid.Value.kReverse) || shooterPosition.isState(DoubleSolenoid.Value.kOff)){
          //logger.info("Intaked a book");
          ballShooter.intake();
        }else{ shooterPosition.setFoward();}
      }
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


