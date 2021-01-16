package com.systemmeltdown.robot.commands;

import com.systemmeltdown.robotlib.subsystems.drive.SkidSteerDriveSubsystem;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoSlalomPathCommand extends ParallelRaceGroup{
    private SkidSteerDriveSubsystem m_driveSub;
    private double m_speed;
    private double m_turn;
    private double m_driveSeconds;

    public AutoSlalomPathCommand(SkidSteerDriveSubsystem driveSubsystem) {
    m_driveSub = driveSubsystem;
    //One revolution of six-inch wheel = 18.84 inches per revolution
    addCommands(
    //Forward 60” (center on edge)
    //Right 26.331”, left 4.514”
    new AutoEncoderDriveCommand(m_driveSub, 26.331, 4.514),
    //Forward 43.806”
    //Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.514, 25.331),
    //Forward 120
    //Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.524, 26.331),
    //Forward 43.806”
    //Right 162.618”, left 27.877”
    new AutoEncoderDriveCommand(m_driveSub, 162.618, 27.877),
    //Forward 43.806”
    //Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.514, 26.331),
    //Forward 120”
    //Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.514, 26.331),
    //Forward 43.806”
    //Right 26.331”, left 4.514”
    new AutoEncoderDriveCommand(m_driveSub, 26.331, 4.514);
    //Forward to finish
    );
    }
}
