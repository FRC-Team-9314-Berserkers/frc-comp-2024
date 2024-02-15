package frc.robot.actions;

import java.util.function.*;

public class AnalogAction {
    Predicate<Float> set;
    Predicate<Float> change;

    AnalogAction(Predicate<Float> _set) {
        set = _set;
    }
}
