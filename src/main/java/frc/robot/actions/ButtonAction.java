package frc.robot.actions;

import java.util.function.*;

public class ButtonAction extends Action{
    public BooleanSupplier pressF; // Function to check if button is pressed
    public BooleanSupplier holdF; // Function to check if button is held
    public BooleanSupplier releaseF; // Function to check if button is released

    ButtonAction(BooleanSupplier _press) { // Constructor for when only press is needed
        pressF = _press;
    }

    ButtonAction(BooleanSupplier _press, BooleanSupplier _release) { // Constructor for when press and release are needed
        pressF = _press;
        releaseF = _release;
    }

    ButtonAction(BooleanSupplier _press, BooleanSupplier _release, BooleanSupplier _hold) { // Constructor for when press, release, and hold are needed
        pressF = _press;
        releaseF = _release;
        holdF = _hold;
    }

    
    public boolean press () { // Check if button is pressed
        if (pressF == null) return false;
        return pressF.getAsBoolean(); // Return boolean the value of the function
    }

    public boolean hold () { // Check if button is held
        if (holdF == null) return false;
        return holdF.getAsBoolean(); // Return the boolean value of the function
    }

    public boolean release () { // Check if button is released
        if (releaseF == null) return false;
        return releaseF.getAsBoolean(); // Return the boolean value of the function
    }


}
