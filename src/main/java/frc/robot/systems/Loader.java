package frc.robot.systems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Util;

public class Loader extends System {
    private final CANSparkMax bayMotor; // Intake Motor
    private final CANSparkMax angleMotor; // Loader Position Motor
    //Settings
    float loadSpeed = 0.35f; // Speed of the loader
    float intakeSpeed = 0.25f; // Speed of the intake
    float ejectSpeed = 0.3f; // Speed of the ejector

    float angleSpeed = 0.04f; // Speed of the angle motor

    float maxAngleSpeed = 0.2f; // Max speed of the angle motor

    
    float[][] raiseSpeeds1 = { // Array of values to control the angle motors speed based on the angle of the loader. Is used to raise the loader to the shooter
        //First is position, second is velocity
        //Calibrated
        {-16.0f, 0.0f},
        {-15.0f, 0.12f},
        {-12.0f, 0.14f},
        {-6.0f,  0.16f},    //Start
        {-5.0f,  0.16f},
        {-4.0f,  0.12f},
        {-3.0f,  0.08f},
        {-2.0f, -0.01f},
        {-1.0f, -0.02f},
        {-0.5f, -0.00f},
        {0.1f, -0.02f},     //End
        {1.0f,  -0.04f},
        {4.0f,  -0.08f},
        {14.0f,  -0.08f},
        {15.0f,  -0.00f}
    };
    ArrayList<float[]> raiseSpeeds = new ArrayList<float[]>();
    

    float[][] lowerSpeeds1 = { // Array of values to control the angle motors speed based on the angle of the loader. Is used to lower the loader over the bumper
        //Calibrated somewhat
        {0.5f, -0.14f},
        {-0.0f, -0.15f},    //Start
        {-1.0f, -0.13f},
        {-2.4f, -0.14f},
        {-3.0f, -0.06f},
        {-4.0f,  0.025f},
        {-5.0f,  0.03f},
        {-6.0f,  0.01f},    //End
        {-7.0f,  0.01f},
        {-8.0f,  0.02f},
        {-10.0f,  0.02f}
    };
    ArrayList<float[]> lowerSpeeds = new ArrayList<float[]>();

    int t;
    public boolean up = false; // Boolean to store if the loader is up
    boolean down = false; // Boolean to store if the loader is down

    float maxAngle = 0; // Max angle of the loader
    float minAngle = -5; // Min angle of the loader

    //Timers
    Timer bayTimer; // Timer for the intake

    long intakeTime = 4000; // Time to intake a note
    long ejectTime = 3000; // Time to eject a note

    enum AngleActions { // Possible states for the angle motor
        raising,
        raised,
        lowering,
        lowered,
        stopped
    }
    
    AngleActions angleAction; // Define a variable to store the current state of the angle motor

    int i = 0;
    boolean loud = false;

    public Loader() {
        super();
        //Neo Motors (Brushless)
        bayMotor = new CANSparkMax(10, MotorType.kBrushless); // Initalize intake motor
        angleMotor = new CANSparkMax(11, MotorType.kBrushless);// Initalize angle motor

        bayTimer = new Timer("intakeTimer"); // Initalize timer for intake

        raiseSpeeds.addAll(Arrays.asList(raiseSpeeds1));// Convert the array to a list
        lowerSpeeds.addAll(Arrays.asList(lowerSpeeds1));// Convert the array to a list

        angleAction = AngleActions.stopped; // Set the angle motor state to stoped.

        disabled = false;// Set the system to enabled
    }

    public void update() {
        i++;// Increment the counter
        loud = false;// Don't log every update
        if (i > 100) {loud = true; i = 0;}// Do Loging this tick

        //Get Position
        float position = (float) angleMotor.getEncoder().getPosition(); // Get the current position of the angle motor
        
        louge(""); // Log a blank line
        louge("Position: " + position); // Log the position of the angle motor
    
        //Apllied Voltage to angle motor
        float v = 0.0f;

        //Upper and lower voltages
        float lowerV = 0.0f;
        float upperV = 0.0f;

        if (angleAction == AngleActions.stopped) { // If the angleAction is stopped stop the angleMotor
            angleMotor.stopMotor();
        } else {
            //Closest array values
            float[] closestAbove = {0, 0};
            float[] closestBelow = {0, 0};
            //Difference from actual position
            float diff = 0.5f;

            if (angleAction == AngleActions.raising) { // If the angleAction is raising
                closestAbove = Util.getClosestAbove(raiseSpeeds, position);// Get the closest value above the current position
                closestBelow = Util.getClosestBelow(raiseSpeeds, position);// Get the closest value below the current position

                diff = position - closestBelow[0]; // Get the difference between the current position and the closest below value
            } else if(angleAction == AngleActions.lowering) { // If the angleAction is lowering
                closestAbove = Util.getClosestAbove(lowerSpeeds, position); // Get the closest value above the current position
                closestBelow = Util.getClosestBelow(lowerSpeeds, position); // Get the closest value below the current position

                diff = position - closestBelow[0]; // Get the difference between the current position and the closest below value
            }

            louge("+[" + closestAbove[0] + ", " + closestAbove[1] + "]" + "   -" + "[" + closestBelow[0] + ", " + closestBelow[1] + "]"); // Log the closest values
            lowerV = closestBelow[1]; // Set the lower voltage to the closest below value
            upperV = closestAbove[1]; // Set the upper voltage to the closest above value

            louge("Upper / Lower Veocities: " + lowerV + " - " + upperV + ";  Difference" + diff); // Log the upper and lower velocities and the difference
            v = Util.lerp(lowerV, upperV, diff); // Set the voltage to the linear interpolation of the lower and upper voltages based on the difference

            if (v > maxAngleSpeed || v < -maxAngleSpeed) { // If the voltage is greater than the max angle speed
                v = 0; // Set the voltage to 0
            }

            louge("Loader Angle Velocity: " + v); // Log the velocity of the angle motor
            if (! disabled) angleMotor.set(v);// Set the angle motor to the velocity or voltage
        }
        

    }

    public void raise() { // Raise the loader
        Util.log("Raising Loader.");
        angleAction = AngleActions.raising;// Set the angleAction to raising
        bayMotor.stopMotor(); // Stop the bay motor

    }

    public void lower(){ // Lower the loader
        Util.log("Lowering Loader.");
        angleAction = AngleActions.lowering;// Set the angleAction to lowering
    }

    public void intake(){ // Intake a note
        if (angleAction == AngleActions.raising) {return;}// If the angleAction is raising return

        if (! disabled) {
            bayMotor.set(-intakeSpeed); // Set the bay motor to the intake speed

            /* bayTimer.schedule(new TimerTask() {
                public void run() {
                    bayMotor.stopMotor();
                }
            }, intakeTime);
            */

            Util.log("Loader: Intake Note.");
        }
    }

	public void ejectNote() { // Eject a note
        if (! disabled) bayMotor.set(ejectSpeed); // Set the bay motor to the eject speed

        bayTimer.schedule(new TimerTask() { // Schedule a timer to stop the bay motor
            public void run() {// Run the timer
                bayMotor.stopMotor();// Stop the bay motor
            }
        }, ejectTime);// Set the time to eject a note

        Util.log("Loader: Ejecting Note.");
	}

    public void stopBay() {// Stop the bay motor
        bayMotor.stopMotor();
         Util.log("Loader: Stopped Loader Bay.");
    }

    void louge(String message) {// Log a message if loud is true
        if (loud) {
            Util.log(message);
        }
    }
}