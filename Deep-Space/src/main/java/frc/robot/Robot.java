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
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.components.Pnumatics;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.components.LinearSlider;

/**
 * TODO:
 * - Pathfinder placed into the auto command
 * - Check to see if the all the ports are correct 
 * - Camera Server 
 * - Vision Processing 
 * - Display data onto shuffle board 
 * - learn about network tables
 * - Create safety flags 
 * - Multithreading 
 * - The shooting pnumatics is just going to be on for ready to fire at an angle or off to collect 
 * - 
 */
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  Drivetrain drive;
  OI input;
  Pnumatics pnumatics;
  LinearSlider slider;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  VictorSP frontLeftMotor;
  VictorSP frontRightMotor;
  VictorSP backLeftMotor;
  VictorSP backRightMotor;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    drive = new Drivetrain(new VictorSP(1), new VictorSP(2), new VictorSP(3), new VictorSP(4));
    input = new OI(new Joystick(1));
    pnumatics = new Pnumatics(new DoubleSolenoid(0, 1));
    slider = new LinearSlider(new VictorSP(5));
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
    double driverX = input.driver.getX();
    double driveY = input.driver.getY();
    drive.setSpeed(driverX, driveY);
    drive.move();
    // If linearslider is manual 
    if (input.linearSliderManual) {
      // Set the linearSlider back to preset state
      if (input.driver.getRawButton(1)) {
        input.setLinearSliderManualState(false);
      }
      // Manual raise the linear slider up 
      if (input.driver.getRawButton(3)){
        // TODO: find out what the speed should be
        slider.setSpeed(.3);
        slider.move();
      }
      if (input.driver.getRawButton(4)){
        slider.setSpeed(-.3);
        slider.move(); 
      }
    } else if (!input.linearSliderManual) {
      // The linear slider is in a preset state 
      // TODO: write the presets and the math to do the preset based on the camera
    }
    if (input.driver.getRawButton(2)){
      pnumatics.setFoward();
    }
    if (input.driver.getRawButton(5)){
      pnumatics.setReversed();
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
