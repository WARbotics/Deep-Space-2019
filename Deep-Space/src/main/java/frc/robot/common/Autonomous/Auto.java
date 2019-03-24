package frc.robot.common.Autonomous;

import frc.robot.common.Autonomous.Turn;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.components.Drivetrain;
import frc.robot.common.Autonomous.Forward;
//import edu.wpi.first.wpilibj.Timer;

public class Auto{

    AHRS navBoard;
    Drivetrain drive;
    Turn turn = new Turn(navBoard);
    Forward forward = new Forward(navBoard);

    public Auto(AHRS navBoard, Drivetrain drive){
        this.navBoard = navBoard;
        this.drive = drive;
    }
}