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
import frc.robot.common.Test;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.common.ButtonDebouncer;
import frc.robot.components.Shooter;
import frc.robot.components.LinearSlider;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * MOTOPR CONTROLLERS
 * [] Accelamentor (speed)
 * 4 drive train Victor SP
 * 2 Victor SP for shooter 
 * 1 Victor SP for linear slider 
 * TODO
 * [] Pathfinder placed into the auto command
 * [] Camera Server 
 * [] Vision Processing 
 * [] Display data onto shuffle board 
 * [] learn about network tables
 * [] Create safety flags 
 * [] Multithreading 
 * [] The shooting pnumatics is just going to be on for ready to fire at an angle or off to collect 
 * [] Logger 
 * [] Test cases 
 * [] Maybe change the shuffle board color based on the alliance side
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
  DoubleSolenoid beakSolenoid;
  DoubleSolenoid shooterSolenoid; 
  Test unitTest = new Test();
  LinearSlider slider; 
  Shooter ballShooter; 
  ButtonDebouncer beakButtonOpen; 
  ButtonDebouncer beakButtonClose;
  ButtonDebouncer sliderButtonRaise;
  ButtonDebouncer sliderButtonLower;
  ButtonDebouncer shoot;
  ButtonDebouncer intake;
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


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    drive = new Drivetrain();
    drive.setMotorsInverted();
    input = new OI();
    beakSolenoid = new DoubleSolenoid(0, 1);
    shooterSolenoid = new DoubleSolenoid(2,3);
    beak = new Pnumatics(beakSolenoid);
    shooterPosition = new Pnumatics(shooterSolenoid);
    slider = new LinearSlider();
    ballShooter = new Shooter();

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
    shoot = new ButtonDebouncer(input.driver, 5, .5);
    intake = new ButtonDebouncer(input.driver, 6, .5);

    // PDP
    PDP = new PowerDistributionPanel();
    PDP.clearStickyFaults();

    // SmartDasboard 
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
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
    SmartDashboard.putNumber("Linear Slider Height", slider.getHeight());
    SmartDashboard.putBoolean("Linear Slider Raising", slider.isRaising());
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
    // Double check to see if the x and y are corret with the joystick
    // Get the correct button values
    double driveY  = input.driver.getRawAxis(1);
    double driveX = input.driver.getRawAxis(0);
    drive.m_Drive.arcadeDrive(driveY*.80, driveX*.80);
    
    if (input.driver.getRawButton(1)){
      if(beakButtonOpen.isReady()){
        beak.setFoward();
      }
    }
    if (input.driver.getRawButton(2)){
      if(beakButtonClose.isReady()){
        beak.setReversed();
      }
    }
    if (input.driver.getRawButton(3)){
      if(sliderButtonRaise.isReady()){
        slider.raise(); 
      }
    }
    if (input.driver.getRawButton(4)){
      if(sliderButtonLower.isReady()){
        slider.lower();
      }
    }
    if (input.driver.getRawButton(5)){
      if(shoot.isReady()){
        if(shooterPosition.m_solenoid.get() == DoubleSolenoid.Value.kForward){
          shooterPosition.setReversed();
        }
        if(shooterPosition.isReady()){
          ballShooter.shoot();
        }
      }
    }
    if (input.driver.getRawButton(6)) {
      if (intake.isReady()) {
        shooterPosition.setFoward();
        if (shooterPosition.m_solenoid.get() == DoubleSolenoid.Value.kReverse) {
          shooterPosition.setFoward();
        }
        if (shooterPosition.isReady()) {
          ballShooter.intake();
        }
      }
    }

  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    if(!unitTest.isTestFinished){
      // Runs the test cases 
      unitTest.testCompressor();
    }
  }
}
