package com.systemmeltdown.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.systemmeltdown.robotlib.subsystems.drive.FalconTrajectoryDriveSubsystem;

public class AutoSlalomPathCommand extends SequentialCommandGroup{
    private FalconTrajectoryDriveSubsystem m_driveSub;

    public AutoSlalomPathCommand(FalconTrajectoryDriveSubsystem driveSubsystem) {
    m_driveSub = driveSubsystem;
    //One revolution of six-inch wheel = 18.84 inches per revolution
    addCommands(
    //Forward 60” (center on edge), Right 26.331”, left 4.514”
    new AutoEncoderDriveCommand(m_driveSub, 26.331, 4.514),
    //Forward 43.806”, Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.514, 25.331),
    //Forward 120, Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.524, 26.331),
    //Forward 43.806”, Right 162.618”, left 27.877”
    new AutoEncoderDriveCommand(m_driveSub, 162.618, 27.877),
    //Forward 43.806”, Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.514, 26.331),
    //Forward 120”, Right 4.514”, left 26.331”
    new AutoEncoderDriveCommand(m_driveSub, 4.514, 26.331),
    //Forward 43.806”, Right 26.331”, left 4.514”
    new AutoEncoderDriveCommand(m_driveSub, 26.331, 4.514),
    //Forward to finish
    new AutoEncoderDriveCommand(m_driveSub, 20, 20)
    );
    }
}
