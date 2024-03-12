package frc.robot.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class AutoMode {
    ArrayList<AutoAction> commands;

    AutoAction current;

    Timer actionTimer;
    
    AutoMode() {

    }

    void update() {
        float time = (float) actionTimer.get();

        //if() {}
    }

    void add() {

    }

    void nextAction() {
        current.endFunction.getAsBoolean();
    }

}