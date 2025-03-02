package frc.robot.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer; // Timer class for timing
import frc.robot.Util;

public class AutoMode {
    protected ArrayList<AutoAction> commands; // List of AutoActions

    AutoAction current; // Current AutoAction
    int step; // Current step in the AutoMode

    String name; // Name of the AutoMode

    Timer actionTimer;// Timer for timing the AutoMode

    boolean active; // Whether the AutoMode is active
    
    public AutoMode(String _name) { // Constructor
        commands = new ArrayList<AutoAction>(); // Initialize the list of AutoActions
        name = _name; // Set the name of the AutoMode

        step = 0; // Set the step to 0

        actionTimer = new Timer(); // Initialize the Timer
    }

    public void start() { // Start the AutoMode
        Util.log("Starting Auto Mode: " + name); // Log that the AutoMode is starting
        
        active = true; // Set the AutoMode to active

        Util.log("0"); // Log that the AutoMode is starting
        setStep(0); // Set the step to 0
        Util.log("1"); // Log that the AutoMode is starting
        actionTimer.start(); // Start the Timer
        Util.log(step + ". Action: name-" + current.name + " time-" + current.time); // Log data about the current action.
        current.startFunction.getAsBoolean(); // Run the start function of the current action
        

    }

    public void stop() { // Stop the AutoMode
        Util.log("Auto Mode is done.");
        active = false; // Set the AutoMode to inactive
    }

    public void update() { // Update the AutoMode
        if (! active) {return;} // If the AutoMode is not active, return
        if (current == null) {Util.log("Null Auto Action.");return;} // If the current action is null, return
        
        float time = (float) actionTimer.get(); // Get the time from the Timer

        if (time >= current.time) { // If the time is greater than or equal to the time of the current action
            nextAction();
            return;
        }

        current.loopFunction.getAsBoolean(); // Run the loop function of the current action

        //if() {}
    }

    public void add(AutoAction e) { // Add an AutoAction to the AutoMode
        commands.add(e); // Add the AutoAction to the list of AutoActions
    }

    void nextAction() { // Move to the next action
        current.endFunction.getAsBoolean(); // Run the end function of the current action

        setStep(step + 1); // Set the step to the next step
        actionTimer.restart(); // Restart the Timer
        if (current == null) { // If the current action is null then return
            return;
        }
        Util.log(step + ". Action: name-" + current.name + " time-" + current.time); // Log data about the current action.

        current.startFunction.getAsBoolean(); // Run the start function of the current action
    }

    protected void setStep(int i) { // Set the step
        step = i; // Set the step to i
        if (step < commands.size()) { // If the step is less than the size of the list of AutoActions
            current = commands.get(step); // Set the current action to the action at the step
        } else { // If the step is greater than or equal to the size of the list of AutoActions
            current = null; // Set the current action to null
            stop();// Stop the AutoMode
        }
    }

}