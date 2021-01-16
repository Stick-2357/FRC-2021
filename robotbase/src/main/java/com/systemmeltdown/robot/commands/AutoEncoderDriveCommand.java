package com.systemmeltdown.robot.commands;

import com.systemmeltdown.robotlib.subsystems.drive.FalconTrajectoryDriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoEncoderDriveCommand extends CommandBase {
    private FalconTrajectoryDriveSubsystem m_driveSub;
    private double m_left, m_right;

    /**
     * Command that drives for a certian distance
     * @param driveSub 
     * @param left - Distance the left wheels with travel (inches)
     * @param right - Distance the right wheels with travel (inches)
     */
    public AutoEncoderDriveCommand(FalconTrajectoryDriveSubsystem driveSub, double left, double right) {
        m_driveSub = driveSub;
        m_left = left;
        m_right = right;
    }

    @Override
    public boolean isFinished() {
        return m_driveSub.getLeftFalconMaster().getSelectedSensorPosition() * m_driveSub.m_distancePerPulse == m_left
                && m_driveSub.getRightFalconMaster().getSelectedSensorPosition()
                        * m_driveSub.m_distancePerPulse == m_right;
    }

    @Override
    public void initialize() {
        double scaledLeft = (m_left >= m_right ? 1.0 : m_left / m_right);
        double scaledRight = (m_right > m_left ? 1.0 : m_right / m_left);
        m_driveSub.setProportional(scaledLeft, scaledRight);
        super.initialize();
    }

    @Override
    public void end(boolean interrupted) {
        m_driveSub.setProportional(0, 0);
        super.end(interrupted);
    }
}