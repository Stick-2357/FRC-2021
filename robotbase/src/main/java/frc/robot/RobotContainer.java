/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.systemmeltdown.robot.subsystems.IntakeSubsystem;
import com.systemmeltdown.robot.subsystems.ShooterSubsystem;
import com.systemmeltdown.robot.subsystems.StorageSubsystem;
import com.systemmeltdown.robotlib.subsystems.drive.FalconTrajectoryDriveSubsystem;
import com.systemmeltdown.robot.subsystems.ClimbSubsystem;
import com.systemmeltdown.robot.subsystems.FeederSubsystem;
import com.systemmeltdown.robot.controls.GunnerControls;
import com.systemmeltdown.robot.controls.InvertDriveControls;
import com.systemmeltdown.robot.subsystems.SubsystemFactory;
import com.systemmeltdown.robot.subsystems.TogglableLimelightSubsystem;
import com.systemmeltdown.robot.subsystems.TurretSubsystem;
import com.systemmeltdown.robotlib.commands.DriveProportionalCommand;
import com.systemmeltdown.robotlib.subsystems.ClosedLoopSubsystem;
import com.systemmeltdown.robot.shuffleboard.AutoModeCommandGenerator;
import com.systemmeltdown.robot.shuffleboard.AutonomousSelectorWidget;
import com.systemmeltdown.robot.shuffleboard.CellNumberWidget;
import com.systemmeltdown.robot.shuffleboard.DriveTab;
import com.systemmeltdown.robot.shuffleboard.FailsafeButtonWidget;
import com.systemmeltdown.robot.shuffleboard.TargetingWidget;
import com.systemmeltdown.robot.shuffleboard.LoggerTab;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.SerialPort.Port; <- Used for VL53LOX Sensor Output
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public static final String SHUFFLEBOARD_TAB_ROBOT = "Robot";

  // The robot's subsystems and commands are defined here...
  private FalconTrajectoryDriveSubsystem m_driveSub;
  private final ClimbSubsystem m_climbSub;
  private final FeederSubsystem m_feederSub;
  public final IntakeSubsystem m_intakeSub;
  private final ShooterSubsystem m_shootSub;
  private final StorageSubsystem m_storageSub;
  private final TurretSubsystem m_turretSub;
  private final TogglableLimelightSubsystem m_visionSub;
  private final Compressor m_compressor;

  private final InvertDriveControls m_driverControls;
  private final GunnerControls m_gunnerControls;
  // public final VL53LOXSensorOutput m_sensor = new
  // VL53LOXSensorOutput(Constants.BAUD_RATE, Port.kUSB);

  // Automode declarations
  private final AutoModeCommandGenerator m_autoModeCommandGenerator;
  private final AutonomousSelectorWidget m_autoNavPathSelector;
  // Flag to handle if the robot should run code specific to at-home or in-person
  // challenges
  private final boolean m_isInPerson = false;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    SubsystemFactory subsystemFactory = new SubsystemFactory();
    m_driveSub = subsystemFactory.CreateFalconTrajectoryDriveSubsystem();
    m_climbSub = subsystemFactory.CreateClimbSubsystem();
    m_feederSub = subsystemFactory.CreateFeederSubsystem();
    m_intakeSub = subsystemFactory.CreateIntakeSubsystem();
    m_shootSub = subsystemFactory.CreateShooterSubsystem();
    m_storageSub = subsystemFactory.CreateStorageSubsystem();
    m_turretSub = subsystemFactory.CreateTurretSubsystem();
    m_visionSub = subsystemFactory.CreateLimelightSubsystem();
    m_compressor = new Compressor();
    m_compressor.setClosedLoopControl(true);

    // Configure the button bindings
    m_driverControls = new InvertDriveControls.InvertDriveControlsBuilder(new XboxController(0), .1)
        .withDriveSub(m_driveSub).withVisionSub(m_visionSub).build();

    m_gunnerControls = new GunnerControls.GunnerControlsBuilder(new XboxController(1)).withIntakeSub(m_intakeSub)
        .withFeederSubsystem(m_feederSub).withShooterSubsystem(m_shootSub).withClimbSubsystem(m_climbSub)
        .withStorageSubsystem(m_storageSub).withTurretSub(m_turretSub).withVisionSub(m_visionSub).build();

    configureDriveSub();
    configureShuffleboard();
    if (m_isInPerson) {
      m_autoModeCommandGenerator = new AutoModeCommandGenerator("AUTO", m_intakeSub, m_driveSub, m_storageSub,
          m_turretSub, m_feederSub, m_shootSub, m_visionSub);
      m_autoNavPathSelector = null;
    } else {
      m_autoModeCommandGenerator = null;
      m_autoNavPathSelector = new AutonomousSelectorWidget("AUTO", m_driveSub);
    }
  }

  private void configureShuffleboard() {
    DriveTab driveTab = new DriveTab();

    driveTab.addWidget(new FailsafeButtonWidget(SHUFFLEBOARD_TAB_ROBOT,
        new ClosedLoopSubsystem[] { m_intakeSub, m_shootSub, m_climbSub, m_driveSub, m_visionSub }));

    driveTab.addWidget(new CellNumberWidget(SHUFFLEBOARD_TAB_ROBOT, m_storageSub));

    TargetingWidget targetingWidget = new TargetingWidget(SHUFFLEBOARD_TAB_ROBOT, m_turretSub);

    LoggerTab loggerTab = new LoggerTab();

    // driveTab.show();
  }

  private void configureDriveSub() {
    m_driveSub.setDefaultCommand(new DriveProportionalCommand(m_driveSub, m_driverControls));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // return m_autoModeCommandGenerator.generateCommand();
    if (m_isInPerson) {
      return m_autoModeCommandGenerator.generateCommand();
    } else {
      return m_autoNavPathSelector.generateCommand();
    }
  }
}
