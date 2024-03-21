package frc.robot.systems;

import java.util.ArrayList;
import java.util.Arrays;
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

    
    float[][] raiseSpeeds1 = {
        //First is position, second is velocity
        {-6.0f, 0.05f},
        {-5.0f, 0.05f},
        {-4.0f, 0.05f},
        {-3.0f, 0.04f},
        {-2.0f, 0.02f},
        {-1.0f, 0.01f},
        {0.0f, 0.00f},
        {1.0f, -0.01f}
    };
    ArrayList<float[]> raiseSpeeds = new ArrayList<float[]>();
    

    float[][] lowerSpeeds1 = {
        //Incorrect at the moment (WIP)
        {-0.5f, -0.05f},
        {-0.0f, -0.05f},
        {-1.0f, -0.05f},
        {-2.0f, -0.04f},
        {-3.0f, -0.02f},
        {-4.0f, -0.01f},
        {-5.0f, -0.00f},
        {-6.0f, 0.01f}
    };
    ArrayList<float[]> lowerSpeeds = new ArrayList<float[]>();

    int t;
    public boolean up = false;
    boolean down = false;

    float maxAngle = 0;
    float minAngle = -5;

    //Timers
    Timer bayTimer;

    //
    long intakeTime = 1000;
    long ejectTime = 2000;

    enum AngleActions {
        raising,
        lowering,
        stopped
    }
    
    AngleActions angleAction;

    int i = 0;
    boolean loud = false;

    public Loader() {
        super();
        //Neo Motors (Brushless)
        bayMotor = new CANSparkMax(10, MotorType.kBrushless);
        angleMotor = new CANSparkMax(11, MotorType.kBrushless);

        bayTimer = new Timer("intakeTimer");

        raiseSpeeds.addAll(Arrays.asList(raiseSpeeds1));
        lowerSpeeds.addAll(Arrays.asList(lowerSpeeds1));

        angleAction = AngleActions.stopped;
    }

    public void update() {
        i++;
        if (i > 100) {loud = true; i = 0;}

        //Get Position
        float position = (float) angleMotor.getEncoder().getPosition();
        
        louge("");
        louge("Position: " + position);
    
        //Apllied Voltage to angle motor
        float v = 0.0f;

        //Upper and lower voltages
        float lowerV = 0.0f;
        float upperV = 0.0f;

        if (angleAction == AngleActions.stopped) {
            angleMotor.stopMotor();
        } else {
            //Closest array values
            float[] closestAbove = {0, 0};
            float[] closestBelow = {0, 0};
            //Difference from actual position
            float diff = 0.5f;

            if (angleAction == AngleActions.raising) {
                closestAbove = Util.getClosestAbove(raiseSpeeds, position);
                closestBelow = Util.getClosestBelow(raiseSpeeds, position);

                diff = position - closestBelow[0];
            } else if(angleAction == AngleActions.lowering) {
                closestAbove = Util.getClosestAbove(lowerSpeeds, position);
                closestBelow = Util.getClosestBelow(lowerSpeeds, position);

                diff = position - closestBelow[0];
            }

            louge("+[" + closestAbove[0] + ", " + closestAbove[1] + "]" + "   -" + "[" + closestBelow[0] + ", " + closestBelow[1] + "]");
            lowerV = closestBelow[1];
            upperV = closestAbove[1];

            louge("Upper / Lower Veocities: " + lowerV + " - " + upperV + ";  Difference" + diff);
            v = Util.lerp(lowerV, upperV, diff);

            if (v > maxAngleSpeed || v < -maxAngleSpeed) {
                v = 0;
            }

            louge("Loader Angle Velocity: " + v);
            angleMotor.set(v);
        }
        

    }

    public void raise() {
        Util.log("Raising Loader.");
        angleAction = AngleActions.raising;
    }

    public void lower(){
        angleAction = AngleActions.lowering;
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

    void louge(String message) {
        if (loud) {
            Util.log(message);
        }
    }
}