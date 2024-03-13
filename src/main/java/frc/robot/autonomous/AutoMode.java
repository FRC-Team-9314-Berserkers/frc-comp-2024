package frc.robot.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class AutoMode {
    protected ArrayList<AutoAction> commands;

    AutoAction current;
    int step;

    String name;

    Timer actionTimer;
    
    public AutoMode(String _name) {
        commands = new ArrayList<AutoAction>();
        name = _name;

        setStep(0);

        actionTimer = new Timer();
    }

    void start() {
        setStep(0);
        
    }

    public void update() {
        if (current == null) return;
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

        current.startFunction.getAsBoolean();
    }

    protected void setStep(int i) {
        step = i;
        if (step < commands.size())
            current = commands.get(step);
    }

}