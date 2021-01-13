/*package com.systemmeltdown.robot.shuffleboard;

import com.systemmeltdown.robot.commands.TemporaryCommand;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A Shuffleboard widget to auto-select autonomous commands.
 * 
 * @category Shuffleboard
 *//*
public class AutonomousSelectorWidget extends ShuffleboardWidget {
    private static ComplexWidget m_autoSelectWidget;
    private static SendableChooser<Integer> m_chooser;

    /**
     * @param tabTitle The title of the tab the widget will be added to. If it does
     *                 not exist, it will be created automatically.c
     *//*
    public AutonomousSelectorWidget(String tabTitle) {
        super(tabTitle);

        m_chooser = new SendableChooser<>();
        m_chooser.setDefaultOption("NONE", new TemporaryCommand());
        m_chooser.addOption("ONE", new TemporaryCommand());
        m_chooser.addOption("TWO", new TemporaryCommand());

        ComplexWidget autoSelectWidget = Shuffleboard.getTab(tabTitle)
            .add("AutoNav Selector", m_chooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser);

        m_autoSelectWidget = autoSelectWidget;
       
    }

    private void testOutput() {
        switch (m_chooser.getSelected()) {
            case 0:
                System.out.println("NONE TRIGGERED");
                break;
            case 1:
                System.out.println("ONE TRIGGERED");
                break;
            case 2:
                System.out.println("TWO TRIGGERED");
                break;
            default:
                System.out.println("DID NOT WORK");
        }
    }

    public void periodic() {
        // TODO:
        testOutput();
    }
} */

package com.systemmeltdown.robot.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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

        protected AutoNavPathChooser() {
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
    public AutonomousSelectorWidget(String tabTitle) {
        m_tabTitle = tabTitle;

        chooser = new AutoNavPathChooser();
    }

    public static void show() {
        Shuffleboard.selectTab(m_tabTitle);
    }

    public Command generateCommand() {
        return new SequentialCommandGroup(
            chooser.getActionCommand()
        );
    }
}
