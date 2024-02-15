package frc.robot.actions;

import java.util.function.*;

public class ButtonAction extends Action{
    BooleanSupplier press;
    BooleanSupplier hold;
    BooleanSupplier release;

    ButtonAction(BooleanSupplier _press) {
        press = _press;
    }
}
