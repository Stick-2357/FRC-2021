package com.systemmeltdown.robot.shuffleboard;

import com.systemmeltdown.robotlib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;


/**
 * A Shuffleboard widget that allows editing of wait time and Automode actions.
 * 
 * @category Shuffleboard
 */
public class AutonomousSelectorWidget {
    public enum AutomodePaths {
        NONE,
        SLALOM,
        BARRELROLL,
        BOUNCE,
    }

    private class AutoNavPathChooser {
        protected SendableChooser<AutomodePaths> m_chooser;
        private FalconTrajectoryDriveSubsystem m_driveSub;

        protected AutoNavPathChooser(FalconTrajectoryDriveSubsystem driveSub) {

            m_driveSub = driveSub;

            // Adds Chooser Widget
            ShuffleboardTab tab = Shuffleboard.getTab(m_tabTitle);
            m_chooser = new SendableChooser<>();

            //Sets options for chooser widget
            m_chooser.setDefaultOption("None", AutomodePaths.NONE);
            m_chooser.addOption("Slalom", AutomodePaths.SLALOM);
            m_chooser.addOption("Barrel Roll", AutomodePaths.BARRELROLL);
            m_chooser.addOption("Bounce", AutomodePaths.BOUNCE);
            tab.add("AutoNav Paths ", m_chooser);
        }
        
        //Switch command to choose what auto command to run.
        public Command getActionCommand() {
            switch (m_chooser.getSelected()) {
                case SLALOM:
                    System.out.println("RUN SLALOM");
                    return new WaitCommand(0);
                case BARRELROLL:
                    System.out.println("RUN BARRELROLL");
                    return new WaitCommand(0);
                case BOUNCE:
                    System.out.println("RUN BOUNCE");
                    return new WaitCommand(0);
                case NONE:
                default:
                    System.out.println("ACTION: NONE");
                    return new WaitCommand(0);
            }
        }
    }

    private static String m_tabTitle;
    private AutoNavPathChooser chooser;

    /**
     * @param tabTitle The title of the tab the widget should be added to.
     * @param index The index of the Wait Time widget, since there are more than one being created.
     */
    public AutonomousSelectorWidget(String tabTitle, FalconTrajectoryDriveSubsystem driveSub) {
        m_tabTitle = tabTitle;
        chooser = new AutoNavPathChooser(driveSub); //Creates the chooser widget
    }

    public static void show() {
        Shuffleboard.selectTab(m_tabTitle);
    }

    public Command generateCommand() {
            return chooser.getActionCommand();
    }
}
