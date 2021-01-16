package com.systemmeltdown.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.systemmeltdown.robotlib.subsystems.drive.FalconTrajectoryDriveSubsystem;

public class AutoSlalomPathCommand extends SequentialCommandGroup {

    public AutoSlalomPathCommand(FalconTrajectoryDriveSubsystem driveSubsystem) {

        addCommands(
                // Forward 60” (center on edge)
                new AutoEncoderDriveCommand(driveSubsystem, 60, 60),

                // Right 26.331”, left 4.514”
                new AutoEncoderDriveCommand(driveSubsystem, 4.514, 26.331),

                // Forward 43.806”
                new AutoEncoderDriveCommand(driveSubsystem, 43.806, 43.806),

                // Right 4.514”, left 26.331”
                new AutoEncoderDriveCommand(driveSubsystem, 26.331, 4.514),

                // Forward 120
                new AutoEncoderDriveCommand(driveSubsystem, 120, 120),

                // Right 4.514”, left 26.331”
                new AutoEncoderDriveCommand(driveSubsystem, 26.331, 4.514),

                // Forward 43.806”
                new AutoEncoderDriveCommand(driveSubsystem, 43.806, 43.806),

                // Right 162.618”, left 27.877”
                new AutoEncoderDriveCommand(driveSubsystem, 27.877, 162.618),

                // Forward 43.806”
                new AutoEncoderDriveCommand(driveSubsystem, 43.806, 43.806),

                // Right 4.514”, left 26.331”
                new AutoEncoderDriveCommand(driveSubsystem, 26.331, 4.514),

                // Forward 120”
                new AutoEncoderDriveCommand(driveSubsystem, 120, 120),

                // Right 4.514”, left 26.331”
                new AutoEncoderDriveCommand(driveSubsystem, 26.331, 4.514),

                // Forward 43.806”
                new AutoEncoderDriveCommand(driveSubsystem, 43.806, 43.806),

                // Right 26.331”, left 4.514”
                new AutoEncoderDriveCommand(driveSubsystem, 4.514, 26.331),

                // Forward to finish
                new AutoEncoderDriveCommand(driveSubsystem, 20, 20));
    }
}
