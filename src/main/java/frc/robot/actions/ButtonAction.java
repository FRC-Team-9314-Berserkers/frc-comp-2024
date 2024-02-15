package frc.robot.actions;

import java.util.function.*;

public class ButtonAction extends Action{
    public BooleanSupplier pressF;
    public BooleanSupplier holdF;
    public BooleanSupplier releaseF;

    ButtonAction(BooleanSupplier _press) {
        pressF = _press;
    }

    public boolean press () {
        return pressF.getAsBoolean();
    }

    public boolean hold () {
        return holdF.getAsBoolean();
    }

    public boolean release () {
        return releaseF.getAsBoolean();
    }


}
