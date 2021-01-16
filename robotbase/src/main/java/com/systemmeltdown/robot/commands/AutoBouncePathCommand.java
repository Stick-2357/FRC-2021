package com.systemmeltdown.robot.commands;

import com.systemmeltdown.robotlib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoBouncePathCommand extends SequentialCommandGroup {

    public AutoBouncePathCommand(FalconTrajectoryDriveSubsystem driveSub) {
        addCommands(new AutoEncoderDriveCommand(driveSub, 60, 60), // Forward 60”
                new AutoEncoderDriveCommand(driveSub, 7.738, 45.139), // Left 7.738”, right 45.139”
                new AutoEncoderDriveCommand(driveSub, 22.645, 22.645), // Forward 22.645”
                new AutoEncoderDriveCommand(driveSub, -11.209, 11.209), // Left -11.209”, right 11.209”
                new AutoEncoderDriveCommand(driveSub, -97.289, -97.289), // Reverse 97.289”
                new AutoEncoderDriveCommand(driveSub, -89.1, -15.274), // Right -15.274”, left -89.1”
                new AutoEncoderDriveCommand(driveSub, -78.127, -78.127), // Reverse 78.127”
                new AutoEncoderDriveCommand(driveSub, -3.565, 3.565), // Right 3.565”, Left -3.565”
                new AutoEncoderDriveCommand(driveSub, 79.274, 79.274), // Forward 79.274”
                new AutoEncoderDriveCommand(driveSub, 8.573, 50.012), // Right 50.012”, left 8.573”
                new AutoEncoderDriveCommand(driveSub, 30, 30), // Forward 30”
                new AutoEncoderDriveCommand(driveSub, 8.801, 51.34), // Left 8.801”, right 51.34”
                new AutoEncoderDriveCommand(driveSub, 78.127, 78.127), // Forward 78.127”
                new AutoEncoderDriveCommand(driveSub, -8.595, 8.595), // Left -8.595”, right 8.595”
                new AutoEncoderDriveCommand(driveSub, -28.548, -28.548), // Reverse 28.548”
                new AutoEncoderDriveCommand(driveSub, -37.87, -6.492), // Left -37.87, -6.492”
                new AutoEncoderDriveCommand(driveSub, -60, -60) // Reverse 60”
        );
    }
}
