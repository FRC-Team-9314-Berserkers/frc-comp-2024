package frc.robot.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Util;

public class AutoMode {
    protected ArrayList<AutoAction> commands;

    AutoAction current;
    int step;

    String name;

    Timer actionTimer;

    boolean active;
    
    public AutoMode(String _name) {
        commands = new ArrayList<AutoAction>();
        name = _name;

        step = 0;

        actionTimer = new Timer();
    }

    public void start() {
        Util.log("Starting Auto Mode: " + name);
        
        active = true;

        Util.log("0");
        setStep(0);
        Util.log("1");
        actionTimer.start();
        Util.log(step + ". Action: name-" + current.name + " time-" + current.time);
        current.startFunction.getAsBoolean();
        

    }

    public void stop() {
        Util.log("Auto Mode is done.");
        active = false;
    }

    public void update() {
        if (! active) {return;}
        if (current == null) {Util.log("Null Auto Action.");return;}
        
        float time = (float) actionTimer.get();

        if (time >= current.time) {
            nextAction();
            return;
        }

        current.loopFunction.getAsBoolean();

        //if() {}
    }

    public void add(AutoAction e) {
        commands.add(e);
    }

    void nextAction() {
        current.endFunction.getAsBoolean();

        setStep(step + 1);
        actionTimer.restart();
        if (current == null) {
            return;
        }
        Util.log(step + ". Action: name-" + current.name + " time-" + current.time);

        current.startFunction.getAsBoolean();
    }

    protected void setStep(int i) {
        step = i;
        if (step < commands.size()) {
            current = commands.get(step);
        } else {
            current = null;
            stop();
        }
    }

}