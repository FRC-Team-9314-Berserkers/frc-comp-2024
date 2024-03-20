package frc.robot.systems;

import java.util.Timer;
import java.util.TimerTask;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Loader extends System {
    private final CANSparkMax intakeMotor;
    private final CANSparkMax angleMotor;

    //Settings
    float loadSpeed = 0.5f;
    float intakeSpeed = 0.6f;
    int t;
    boolean up = false;
    boolean down = false;

    //Timers
    Timer intakeTimer;

    public Loader() {
        super();
        //Neo Motors (Brushless)
        intakeMotor = new CANSparkMax(10, MotorType.kBrushless);
        angleMotor = new CANSparkMax(11, MotorType.kBrushless);

        intakeTimer = new Timer("intakeTimer");
    }

    public void update() {
        //intakeMotor.set(0);
        if (up == true || down == true){
            t --;
        }

        

    }

    public void raise() {
        t = 100; 
        up = true;
        while (t != 0){
            angleMotor.set(loadSpeed);
        }

        if (t == 0){
            angleMotor.set(0);
        }
        up = false;
    }

    public void intake(){
        intakeMotor.set(intakeSpeed);

        intakeTimer.schedule(new TimerTask() {
            public void run() {
                intakeMotor.stopMotor();
            }
        }, 1000);

        
    }

    public void lower(){
        down = true;
        t = 100; 
        while (t != 0){
            angleMotor.set(-loadSpeed);
        }

        if (t == 0){
            angleMotor.set(0);
        }
        down = false;
    }
}