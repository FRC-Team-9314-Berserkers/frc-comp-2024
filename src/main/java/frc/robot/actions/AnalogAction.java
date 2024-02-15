package frc.robot.actions;

import java.util.function.*;

public class AnalogAction {
    Function<Float, Boolean> set;
    Function<Float, Boolean> change;

    AnalogAction(Function<Float, Boolean> _set) {
        set = _set;
    }
}
