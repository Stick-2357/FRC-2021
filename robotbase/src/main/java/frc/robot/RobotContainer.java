/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import com.systemmeltdown.robotlib.subsystems.drive.SingleSpeedTalonDriveSubsystem;
import com.systemmeltdown.robotlib.subsystems.drive.TalonGroup;
import com.systemmeltdown.robot.commands.InvertDriveCommand;
import com.systemmeltdown.robot.controls.GunnerControls;
import com.systemmeltdown.robot.controls.InvertDriveControls;
import com.systemmeltdown.robot.subsystems.TrajectorySubsystem;
import com.systemmeltdown.robotlib.commands.DriveProportionalCommand;
import com.systemmeltdown.robotlib.controllers.DriverControls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SingleSpeedTalonDriveSubsystem m_driveSub = new SingleSpeedTalonDriveSubsystem(
      new TalonGroup(Constants.DRIVE_MOTOR_RIGHT_1, Constants.DRIVE_MOTOR_RIGHT_SLAVES),
      new TalonGroup(Constants.DRIVE_MOTOR_LEFT_1, Constants.DRIVE_MOTOR_LEFT_SLAVES));
  
  private final TrajectorySubsystem m_trajectorySub = new TrajectorySubsystem(Constants.GYRO_ID);

  private final InvertDriveControls m_driverControls = new InvertDriveControls(new XboxController(0), .25);
  private final GunnerControls m_gunnerControls = new GunnerControls(new XboxController(1));
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureDriveSub();
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // TODO: create shoot command
    // m_gunnerControls.m_shootButton.whenPressed(command)
    m_driverControls.m_invertButton.whenPressed(new InvertDriveCommand(m_driveSub, m_driverControls));
  }

  private void configureDriveSub() {
    Map<String, Object> configMap = new HashMap<>();
    configMap.put(m_driveSub.CONFIG_IS_RIGHT_INVERTED, true);
    configMap.put(m_driveSub.CONFIG_IS_LEFT_INVERTED, false);
    m_driveSub.configure(configMap);
    m_driveSub
        .setDefaultCommand(new DriveProportionalCommand(m_driveSub, m_driverControls));
  }

  //Taken from docs.wpilib.org example code
  /**
  * Use this to pass the autonomous command to the main {@link Robot} class.
  *
  * @return the command to run in autonomous
  */
  public Command getAutonomousCommand() {

    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(Constants.S_VOLTS,
                                       Constants.V_VOLT_SECONDS_PER_METER,
                                       Constants.A_VOLT_SECONDS_SQUARED_PER_METER),
            Constants.DRIVE_KINEMATICS, 10);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(Constants.MAX_SPEED_METERS_PER_SECOND,
                             Constants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.DRIVE_KINEMATICS)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
    );

    RamseteCommand ramseteCommand = new RamseteCommand(
      exampleTrajectory,
      m_trajectorySub::getPose,
      new RamseteController(Constants.RAMSETE_B, Constants.RAMSETE_ZETA),
      new SimpleMotorFeedforward(Constants.S_VOLTS,
                                  Constants.V_VOLT_SECONDS_PER_METER,
                                  Constants.A_VOLT_SECONDS_SQUARED_PER_METER),
      Constants.DRIVE_KINEMATICS,
      m_trajectorySub::getWheelSpeeds,
      new PIDController(Constants.P_DRIVE_VEL, 0, 0),
      new PIDController(Constants.P_DRIVE_VEL, 0, 0),
      // RamseteCommand passes volts to the callback
      m_trajectorySub::tankDriveVolts,
      m_trajectorySub
    );

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> m_trajectorySub.tankDriveVolts(0, 0));
  }
}
