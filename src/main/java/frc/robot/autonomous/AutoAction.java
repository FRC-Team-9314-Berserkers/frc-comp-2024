package frc.robot.autonomous;

import java.util.function.BooleanSupplier;

import frc.robot.actions.Action;

public class AutoAction extends Action{
    float time;
    public BooleanSupplier startFunction;
    public BooleanSupplier loopFunction;
    public BooleanSupplier endFunction;

    public AutoAction() {
        super();
        this.time = 0;
        startFunction = () -> {return false;};
        endFunction = () -> {return false;};
    }

    public AutoAction(BooleanSupplier _startFunction) {
        this();
        startFunction = _startFunction;
    }
        
    
}