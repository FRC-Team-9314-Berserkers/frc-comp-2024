package frc.robot.autonomous;

import java.util.function.BooleanSupplier;

import frc.robot.actions.Action;

public class AutoAction extends Action{
    float time;
    String name;
    public BooleanSupplier startFunction;
    public BooleanSupplier loopFunction;
    public BooleanSupplier endFunction;

    public AutoAction() {
        super();
        time = 4;
        name = "action";
        startFunction = () -> {return false;};
        endFunction = () -> {return false;};
        loopFunction = () -> {return false;};
    }

    public AutoAction(BooleanSupplier _startFunction) {
        this();
        startFunction = _startFunction;
    }

    public AutoAction(float _time, BooleanSupplier _startFunction) {
        this();
        time = _time;
        startFunction = _startFunction;
    }
        
    public AutoAction(float _time, BooleanSupplier _startFunction, BooleanSupplier _loopFunction) {
        this();
        time = _time;
        startFunction = _startFunction;
        loopFunction = _loopFunction;
    }

    public AutoAction(float _time, BooleanSupplier _startFunction, BooleanSupplier _loopFunction, BooleanSupplier _endFunction) {
        this();
        time = _time;
        startFunction = _startFunction;
        loopFunction = _loopFunction;
    }
    
}