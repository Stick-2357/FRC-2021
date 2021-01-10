package com.systemmeltdown.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * A Shuffleboard widget to auto-select autonomous commands.
 * 
 * @category Shuffleboard
 */
public class AutonomousSelectorWidget extends ShuffleboardWidget {
    private static ComplexWidget m_autoSelectWidget;
    private static SendableChooser m_chooser;

    /**
     * @param tabTitle The title of the tab the widget will be added to. If it does
     *                 not exist, it will be created automatically.
     */
    public AutonomousSelectorWidget(String tabTitle) {
        
        super(tabTitle);
        ComplexWidget autoSelectWidget = Shuffleboard.getTab(tabTitle)
            .add("AutoNav Selector", m_chooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser);
        m_autoSelectWidget = autoSelectWidget;
    }

    public void periodic() {
        // TODO:
    }
}