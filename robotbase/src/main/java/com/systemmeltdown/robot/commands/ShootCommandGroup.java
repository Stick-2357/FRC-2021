package com.systemmeltdown.robot.commands;

import com.systemmeltdown.robot.subsystems.FeederSubsystem;
import com.systemmeltdown.robot.subsystems.ShooterSubsystem;
import com.systemmeltdown.robot.subsystems.StorageSubsystem;
import com.systemmeltdown.robot.subsystems.TogglableLimelightSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class ShootCommandGroup extends ParallelRaceGroup {
    public ShootCommandGroup(
        final StorageSubsystem storageSub,
        final FeederSubsystem feederSub,
        final ShooterSubsystem shooterSub,
        final TogglableLimelightSubsystem visionSub,
        final int shooterSpeed
    ) {
        this(storageSub, feederSub, shooterSub, visionSub, shooterSpeed, -1);
    }

    public ShootCommandGroup(
        final StorageSubsystem storageSub,
        final FeederSubsystem feederSub,
        final ShooterSubsystem shooterSub,
        final TogglableLimelightSubsystem visionSub,
        final int shooterSpeed,
        final double shootSeconds
    ) {
        addCommands(
            new ShooterSpeedCommand(shooterSub, shooterSpeed),
            new SequentialCommandGroup(
                new WaitCommand(Constants.FEEDER_SHOOT_DELAY),
                new WaitForFeederToClearCommand(feederSub),
                new FeedToShooterCommand(feederSub)
            ),
            new SequentialCommandGroup(
                new WaitCommand(Constants.CAROUSEL_SHOOT_DELAY),
                new RotateStorageContinuous(storageSub, Constants.STORAGE_CAROUSEL_SHOOTER_ROTATION_SPEED, true)
            )
        );

        if (shootSeconds > 0) {
            addCommands(new WaitCommand(shootSeconds));
        }
    }
}
