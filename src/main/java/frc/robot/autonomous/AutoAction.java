package frc.robot.autonomous;

import java.util.function.BooleanSupplier;

import frc.robot.actions.Action;

public class AutoAction extends Action{
    float time; // time in seconds
    String name; // name of the action
    public BooleanSupplier startFunction;
    public BooleanSupplier loopFunction;
    public BooleanSupplier endFunction;

    public AutoAction() {
        super();
        time = 4; // default time is 4 seconds
        name = "action"; // default name is action
        startFunction = () -> {return false;}; // default start function is false
        endFunction = () -> {return false;}; // default end function is false
        loopFunction = () -> {return false;}; // default loop function is false
    }

    public AutoAction(BooleanSupplier _startFunction) {
        this(); // call default constructor
        startFunction = _startFunction; // set start function
    }

    public AutoAction(float _time, BooleanSupplier _startFunction) {
        this(); // call default constructor
        time = _time; // set time
        startFunction = _startFunction;// set start function
    }
        
    public AutoAction(float _time, BooleanSupplier _startFunction, BooleanSupplier _loopFunction) {
        this(); // call default constructor
        time = _time; // set time
        startFunction = _startFunction; // set start function
        loopFunction = _loopFunction; // set loop function
    }

    public AutoAction(float _time, BooleanSupplier _startFunction, BooleanSupplier _loopFunction, BooleanSupplier _endFunction) {
        this(); // call default constructor
        time = _time; // set time
        startFunction = _startFunction; // set start function
        loopFunction = _loopFunction; // set loop function
    }
    
}