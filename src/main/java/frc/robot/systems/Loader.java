package frc.robot.systems;

import java.util.Timer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Loader extends System {
    private final CANSparkMax intakeMotor;
    private final CANSparkMax angleMotor;

    //Settings
    float loadSpeed = 0.5f;
    float intakeSpeed = 0.6f;
    int t;
    public Loader() {
        super();
        //Neo Motors (Brushless)
        intakeMotor = new CANSparkMax(1, MotorType.kBrushless);
        angleMotor = new CANSparkMax(0, MotorType.kBrushless);
    }

    public void update() {

    }

    public void lift() {
    t = 100; 
    while (t != 0){
    angleMotor.set(loadSpeed);
    }

    if (t == 0){
    angleMotor.set(0);
    }

    }

    public void spin(){

    }

    public void drop(){
        t = 100; 
        while (t != 0){
        angleMotor.set(-loadSpeed);
        }
    
        if (t == 0){
        angleMotor.set(0);
        }
    }
}