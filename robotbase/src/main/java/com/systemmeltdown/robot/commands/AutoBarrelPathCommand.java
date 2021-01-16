package com.systemmeltdown.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.systemmeltdown.robotlib.subsystems.drive.FalconTrajectoryDriveSubsystem;

public class AutoBarrelPathCommand extends SequentialCommandGroup {
    // Forward 150”
    // Left 212.938”, right 36.504”
    // Forward 100.095”
    // Right 185.449”, left 31.791”
    // Forward 84.853”
    // Right 137.445”, left 23.562”
    // Forward 300”
    public AutoBarrelPathCommand(FalconTrajectoryDriveSubsystem driveSub) {
        addCommands(new AutoEncoderDriveCommand(driveSub, 150, 150),
                new AutoEncoderDriveCommand(driveSub, 212.938, 36.504),
                new AutoEncoderDriveCommand(driveSub, 100.095, 100.095),
                new AutoEncoderDriveCommand(driveSub, 31.791, 186.449),
                new AutoEncoderDriveCommand(driveSub, 84.853, 84.853),
                new AutoEncoderDriveCommand(driveSub, 23.562, 137.445),
                new AutoEncoderDriveCommand(driveSub, 300, 300));
    }

}