package frc.robot.actions;

import java.util.function.*;

public class ButtonAction extends Action{
    public BooleanSupplier pressF;
    public BooleanSupplier holdF;
    public BooleanSupplier releaseF;

    ButtonAction(BooleanSupplier _press) {
        pressF = _press;
    }

    ButtonAction(BooleanSupplier _press, BooleanSupplier _release) {
        pressF = _press;
        releaseF = _release;
    }

    ButtonAction(BooleanSupplier _press, BooleanSupplier _release, BooleanSupplier _hold) {
        pressF = _press;
        releaseF = _release;
        holdF = _hold;
    }

    
    public boolean press () {
        if (pressF == null) return false;
        return pressF.getAsBoolean();
    }

    public boolean hold () {
        if (holdF == null) return false;
        return holdF.getAsBoolean();
    }

    public boolean release () {
        if (releaseF == null) return false;
        return releaseF.getAsBoolean();
    }


}
