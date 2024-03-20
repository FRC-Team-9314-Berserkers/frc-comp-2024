package frc.robot.systems;

import java.util.Timer;
import java.util.TimerTask;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Util;

public class Loader extends System {
    private final CANSparkMax bayMotor;
    private final CANSparkMax angleMotor;

    //Settings
    float loadSpeed = 0.5f;
    float intakeSpeed = 0.6f;
    float ejectSpeed = 0.3f;

    float angleSpeed = 0.04f;

    float maxAngleSpeed = 0.05f;

    float[][] raiseSpeeds = {
       {5.0f, 0.05f},
       {4.0f, 0.05f},
       {3.0f, 0.04f},
       {2.0f, 0.02f},
       {1.0f, 0.01f},
       {0.0f, 0.00f}
    };

    float[][] lowerSpeeds = {
        {5.0f, 0.05f},
        {4.0f, 0.05f},
        {3.0f, 0.04f},
        {2.0f, 0.02f},
        {1.0f, 0.01f},
        {0.0f, 0.00f}
     };

    int t;
    boolean up = false;
    boolean down = false;

    float maxAngle = 0;
    float minAngle = -5;

    //Timers
    Timer bayTimer;

    //
    long intakeTime = 1000;
    long ejectTime = 2000;

    public Loader() {
        super();
        //Neo Motors (Brushless)
        bayMotor = new CANSparkMax(10, MotorType.kBrushless);
        angleMotor = new CANSparkMax(11, MotorType.kBrushless);

        bayTimer = new Timer("intakeTimer");
    }

    public void update() {
        //intakeMotor.set(0);
        if (up == true || down == true){
            t --;
        }

        float position = (float) angleMotor.getEncoder().getPosition();
        
        //Apllied Voltage to robot
        float v = 0.0f;
        
        if (v > maxAngleSpeed) {
            v = maxAngleSpeed;
        }
        angleMotor.set(v);


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

    public void intake(){
        bayMotor.set(intakeSpeed);

        bayTimer.schedule(new TimerTask() {
            public void run() {
                bayMotor.stopMotor();
            }
        }, intakeTime);

        Util.log("Loader: Intake Note.");
    }

	public void ejectNote() {
        bayMotor.set(-ejectSpeed);

        bayTimer.schedule(new TimerTask() {
            public void run() {
                bayMotor.stopMotor();
            }
        }, ejectTime);

        Util.log("Loader: Ejecting Note.");
	}
}