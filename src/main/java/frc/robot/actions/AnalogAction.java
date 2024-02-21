package frc.robot.actions;

import java.util.function.*;

public class AnalogAction {
    Function<Float, Boolean> setF;
    Function<Float, Boolean> changeF;

    AnalogAction(Function<Float, Boolean> _set) {
        setF = _set;
    }

    public boolean set(float f) {
        return setF.apply(f);
    }

    public boolean change(float f) {
        return changeF.apply(f);
    }
}
