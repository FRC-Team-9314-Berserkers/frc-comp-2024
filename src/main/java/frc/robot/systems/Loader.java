package frc.robot.systems;

import java.util.Timer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Loader extends System {
    private final CANSparkMax intakeMotor;
    private final CANSparkMax angleMotor;

    //Settings
    float loadSpeed = 0.6f;

    Loader() {
        super();
        //Neo Motors (Brushless)
        intakeMotor = new CANSparkMax(-1, MotorType.kBrushless);
        angleMotor = new CANSparkMax(-1, MotorType.kBrushless);
    }

    public void update() {
        Timer t = new Timer();
        //t.schedule(new, 0);
    }

    public void load() {
        intakeMotor.set(loadSpeed);
    }
}