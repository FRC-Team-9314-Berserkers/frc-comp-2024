package frc.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Util;


public class Driver extends System{
    /** The input speed (which is inbetween 0 and 1) is multiplied by maxSpeed.
     * The result will not be greater than maxSpeed, for example
     * if the input is 0.5, and maxSpeed is 0.9, the resultant speed
     * is 0.45.
     */
    float maxSpeed;
    /** Motor velocities are multiplied by maxSpeed every tick. */
    float speedFalloff;

    /** Velocities of motor groups */
    float vl, vr;

    boolean quickStopped;

    /** All Drive Motors: */
    private final CANSparkMax motorLeft1 = new CANSparkMax(4, MotorType.kBrushed);
    private final CANSparkMax motorRight1 = new CANSparkMax(6, MotorType.kBrushed);
    private final CANSparkMax motorLeft2 = new CANSparkMax(5, MotorType.kBrushed);
    private final CANSparkMax motorRight2 = new CANSparkMax(7, MotorType.kBrushed);

    /** Drive Motor Groups */
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(motorLeft1, motorLeft2);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(motorRight1, motorRight2);


    
  
    public Driver() {
        /** Driving is disabled if true. */
        disabled = false;

        maxSpeed = 0.25f;
        speedFalloff = 0.89f;
        rightMotors.setInverted(true);
        

        SmartDashboard.putNumber("Drive Speed: ", maxSpeed);
        
        vl = 0;
        vr = 0;

        quickStopRelease();
        //quickStopped = false;
    }

    /** This update function is inherited from the System.  It is
     * called from Robot every periodic iteration.
     */
    public void update() {
        if (disabled) return;

        float newMaxSpeed = (float) SmartDashboard.getNumber("Drive Speed: ", maxSpeed);
        if (newMaxSpeed != maxSpeed) {
            if (newMaxSpeed > 0.4) {
                newMaxSpeed = 0.1f;
            }
            if (newMaxSpeed < 0.002) {
                newMaxSpeed = 0.1f;
            }
            Util.log("Set drive speed to: " + maxSpeed);
            maxSpeed = newMaxSpeed;
            SmartDashboard.putNumber("Drive Speed: ", maxSpeed);
        }

        //Util.log("0: " + vl);
        vl = Math.min(Math.max(vl, -1), 1);
        vr = Math.min(Math.max(vr, -1), 1);

        SmartDashboard.putNumber("Left Drive Velocity", vl);
        SmartDashboard.putNumber("Right Drive Vel", vr);

        float difference = Math.abs(vr-vl);
        SmartDashboard.putNumber("Difference", difference);

        //SNap Drive velocities
        if (difference < 0.015) {
            float v = Math.max(vl,vr);
            vl = v;
            vr = v;
        }

        if (difference > 0.15) {
            vl *= 0.85; 
            vr *= 0.85;
        } else {
            vl *= speedFalloff;
            vr *= speedFalloff; 
        }

        //Util.log("" + vl);
        

        
        leftMotors.set(vl);
        rightMotors.set(vr);

        //Robot.controller.xbox1.setRumble(RumbleType.kLeftRumble, Math.abs(vl));
        //Robot.controller.xbox1.setRumble(RumbleType.kRightRumble, Math.abs(vr));
    }

    /** Drive forward/backward at a percent of max speed*/
    public void straight(float speed) {
        //leftMotors.set(speed*maxSpeed);
        //rightMotors.set(speed*maxSpeed);
        //vl += speed*maxSpeed;
        //vr += speed*maxSpeed;
        setDrive(speed, speed);
        //Util.log("Set Drive: " + speed + "  " + maxSpeed);
    }

    /** Turn left-negative or right-positive at a speed. */
    void turn(float speed) {
        
    }

    
    /** Set both motor controller groups*/
    public void setDrive (float left, float right) {
        setLeftDrive(left);
        setRightDrive(right);
    }

    public boolean setLeftDrive (float speed) {
        //if (quickStopped) return false;
        vl += speed*maxSpeed;

        /*if (Math.abs(speed) > Math.abs(vl)) {
            vl = speed;
        }*/
        return true;
    }

    public boolean setRightDrive (float speed) {
        //if (quickStopped) return false;
        vr += speed*maxSpeed;
        
        /*if (Math.abs(speed) > Math.abs(vr)) {
            vr = speed;
        }*/
        
        return true;
    }

    public boolean quickStop() {
        vr *= -0.9;
        vl *= -0.9;

        quickStopped = true;
        return true;
    }

    public boolean quickStopRelease() {
        quickStopped = false;
        return true;
    }
    
}