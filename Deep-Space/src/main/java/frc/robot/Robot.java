/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.Drivetrain;
import frc.robot.components.OI;
import frc.robot.components.Pnumatics;
import frc.robot.common.ButtonDebouncer;
import frc.robot.components.OI.DriveModes;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.common.MotorRamp;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.common.PID;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Spark;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 **/
public class Robot extends TimedRobot {
  //Drivetrain drive;
  OI input;
  Pnumatics beak;  
  AHRS navXMicro; 
  MotorRamp fowardRamp;
  PID drivetrainPID;
  double lastestAutoTime;
  VictorSP shooter; 
  Spark shooter1;
  Spark intake;
  Drivetrain drive;
  /*
  1 VictorSP for intake 
  1 VictorSP for shooter


  */
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
    // Drivetrain 
  
    Spark leftFront = new Spark(1);
    VictorSP rightFront = new VictorSP(2);
    VictorSP leftRear = new VictorSP(3);
    VictorSP rightRear = new VictorSP(4);
    drive = new Drivetrain(leftFront, leftRear, rightFront, rightRear);
  
    //shooter 
    shooter = new VictorSP(3);
    shooter1 = new Spark(2);
    //intake 
    intake = new Spark(1);
    //Joysticks 
    Joystick driverStick = new Joystick(0);
    Joystick operatorStick = new Joystick(1);
    input = new OI(driverStick, operatorStick);

    /*
    navXMicro = new AHRS(Port.kUSB);
    navXMicro.reset();
    */

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
    
    if(input.operator.getRawButton(1)){
      shooter.set(-1);
    }else{
      shooter.set(0);
    }
    if(input.operator.getRawButton(3)){
      shooter1.set(1);
    }else{
      shooter1.set(0);
    }
    if(input.operator.getRawButton(2)){
      intake.set(-1);
    }else{
      intake.set(0);
    }

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }
}


