package frc.robot.autonomous;

import java.util.function.BooleanSupplier;

import frc.robot.actions.Action;

public class AutoAction extends Action{
    float time;
    public BooleanSupplier startFunction;
    public BooleanSupplier loopFunction;
    public BooleanSupplier endFunction;

        
    
}