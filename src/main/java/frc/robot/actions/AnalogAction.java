package frc.robot.actions;

import java.util.function.*;

public class AnalogAction extends Action{ // AnalogAction is a subclass of Action
    Function<Float, Boolean> setF; // setF is a function that takes a float and returns a boolean
    Function<Float, Boolean> changeF; // changeF is a function that takes a float and returns a boolean

    AnalogAction(Function<Float, Boolean> _set) { // Constructor for AnalogAction
        setF = _set;
    }

    public boolean set(float f) { // set is a function that takes a float and returns a boolean
        return setF.apply(f); // apply is a function that takes a float and returns a boolean
    }

    public boolean change(float f) { // change is a function that takes a float and returns a boolean
        return changeF.apply(f); // apply is a function that takes a float and returns a boolean
    }
}
