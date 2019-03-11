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
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.common.ButtonDebouncer;
import frc.robot.components.Shooter;
import frc.robot.components.LinearSlider;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.robot.common.TurnPID;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.common.MotorRamp;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.common.Logger;

/**
 * TODO:
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
  PWMVictorSPX leftShooter; 
  PWMVictorSPX rightShooter;
  PWMVictorSPX driveMotorLeft; 
  PWMVictorSPX driveMotorLeft1; 
  PWMVictorSPX driveMotorRight;
  PWMVictorSPX driveMotorRight1; 
  Drivetrain drive;
  Joystick driverStick;
  Joystick operatorStick; 
  OI input;
  Pnumatics beak;  
  Pnumatics shooterPosition;
  DoubleSolenoid beakSolenoid;
  DoubleSolenoid shooterSolenoid; 
  PWMVictorSPX sliderMotor;
  LinearSlider slider; 
  Shooter ballShooter; 
  TurnPID driveTurnPID;
  AHRS navXMicro; 
  MotorRamp fowardRamp;
  ButtonDebouncer beakButtonOpen; 
  ButtonDebouncer beakButtonClose;
  ButtonDebouncer sliderButtonRaise;
  ButtonDebouncer sliderButtonLower;
  ButtonDebouncer shootButtton;
  ButtonDebouncer intakeButton;
  NetworkTableInstance defaultTableInit; 
  NetworkTableInstance visionTableInit;
  NetworkTable visionTable;
  NetworkTable defaultTable;
  NetworkTableEntry testEntry;
  PowerDistributionPanel PDP; 
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  Logger logger; 


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Drivetrain
    driveMotorLeft = new PWMVictorSPX(0);
    driveMotorLeft1 = new PWMVictorSPX(1);
    driveMotorRight = new PWMVictorSPX(2);
    driveMotorRight1 = new PWMVictorSPX(3);
    drive = new Drivetrain(driveMotorLeft, driveMotorLeft1, driveMotorRight, driveMotorRight1);
    drive.invertLeftMotors();
    //Joysticks 
    driverStick = new Joystick(0);
    operatorStick = new Joystick(1);
    input = new OI(driverStick, operatorStick);
    // Pnumatics
    beakSolenoid = new DoubleSolenoid(0, 1);
    shooterSolenoid = new DoubleSolenoid(2,3);
    beak = new Pnumatics(beakSolenoid);
    shooterPosition = new Pnumatics(shooterSolenoid);
    // LinearSlider
    sliderMotor = new PWMVictorSPX(5);
    slider = new LinearSlider(sliderMotor);
    //    Shooter
    leftShooter = new PWMVictorSPX(6);
    rightShooter = new PWMVictorSPX(7);
    ballShooter = new Shooter(leftShooter,rightShooter, .8);
    // Motion
    fowardRamp = new MotorRamp(0.03);

    // NAV
    navXMicro = new AHRS(Port.kUSB);
    navXMicro.reset();

    // DataTables 
    //  Java side will hold the datatable server becuase it is on the roborio
    defaultTableInit = NetworkTableInstance.getDefault();
    visionTableInit = NetworkTableInstance.create(); 
    defaultTable = defaultTableInit.getTable("datatables");
    visionTable = visionTableInit.getTable("vision");
    defaultTableInit.startClientTeam(6925);
    visionTableInit.startClientTeam(6925);
    
    // Debouncer 
    beakButtonOpen = new ButtonDebouncer(input.driver,1, .5);
    beakButtonClose = new ButtonDebouncer(input.driver, 2, .5);
    sliderButtonRaise = new ButtonDebouncer(input.driver, 3, .5);
    sliderButtonLower = new ButtonDebouncer(input.driver, 4, .5);
    shootButtton = new ButtonDebouncer(input.driver, 5, .3);
    intakeButton = new ButtonDebouncer(input.driver, 6, .3);

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
    logger = new Logger("dev/U/sda"); // Check this file path
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
    SmartDashboard.putBoolean("beak Open", beak.isOpen());
    SmartDashboard.putBoolean("Shooter Position down", shooterPosition.isOpen());
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
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double driveY  = -input.driver.getRawAxis(1);
    double driveX = -input.driver.getRawAxis(0);
    // Thread this to 200 ms for the speed controller 
    drive.m_Drive.arcadeDrive(fowardRamp.getSpeed(driveX), driveY * .80);
    
    if (input.operator.getRawButton(1)){
      logger.info("Linear slider is moving up");
      slider.motor.set(.6);
    }else{
      slider.motor.set(0);
    }
    
    if (input.operator.getRawButton(2)){
      logger.info("Linear slider is moving down");
      slider.motor.set(-.4);
    }else{
      slider.motor.set(0);
    }

    if (input.driver.getRawButton(1)){
      // Opens the beak
      if(beakButtonOpen.isReady()){
        logger.info("Beak is open");
        beak.setFoward();
      }
    }
    if (input.driver.getRawButton(2)){
      // Close the beak
      if(beakButtonClose.isReady()){
        logger.info("Beak is closed");
        beak.setReversed();
      }
    }

    if (input.driver.getRawButton(5)){
      // Check to see if the shooter is up and if so then it allow the user to shoot
      if(shootButtton.isReady()){
        if(shooterPosition.isState(DoubleSolenoid.Value.kForward) || shooterPosition.isState(DoubleSolenoid.Value.kOff)){
          logger.info("Shoot the ball");
          ballShooter.shoot();
        }else{
          shooterPosition.setReversed();
        }
      }
    }

    if (input.driver.getRawButton(6)) {
      // Check to see if the shooter is down and if so then will allow the user to intake
      if (intakeButton.isReady()) {
        if(shooterPosition.isState(DoubleSolenoid.Value.kReverse) || shooterPosition.isState(DoubleSolenoid.Value.kOff)){
          logger.info("Intaked a book");
          ballShooter.intake();
        }else{ shooterPosition.setFoward();}
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


