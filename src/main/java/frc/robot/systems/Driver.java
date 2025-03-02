package frc.robot.systems; // Package declaration

import com.revrobotics.CANSparkMax; // Import CANSparkMax from revrobotics
import com.revrobotics.CANSparkLowLevel.MotorType; // Import MotorType from CANSparkLowLevel


import edu.wpi.first.wpilibj.GenericHID.RumbleType; // Import haptic feedback
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; // Import motor controller group
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // Import SmartDashboard
import frc.robot.Robot; // Import Robot
import frc.robot.Util; // Import Utility


public class Driver extends System{
    /** The input speed (which is inbetween 0 and 1) is multiplied by maxSpeed.
     * The result will not be greater than maxSpeed, for example
     * if the input is 0.5, and maxSpeed is 0.9, the resultant speed
     * is 0.45.
     */
    float maxSpeed;
    /** Motor velocities are multiplied by maxSpeed every tick. */
    float speedFalloff; // The speed falloff rate

    /** Velocities of motor groups */
    float vl, vr; // Left and right velocities

    boolean quickStopped; // Quick stop flag

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

        maxSpeed = 0.25f; // Max speed is 0.25
        speedFalloff = 0.89f; // Speed falloff is 0.89
        rightMotors.setInverted(true); // Invert right motors
        

        SmartDashboard.putNumber("Drive Speed: ", maxSpeed); // Put drive speed on SmartDashboard
        
        vl = 0; // Left velocity is 0
        vr = 0; // Right velocity is 0

        quickStopRelease(); // Release quick stop flag
        //quickStopped = false;
    }

    /** This update function is inherited from the System.  It is
     * called from Robot every periodic iteration.
     */
    public void update() {
        if (disabled) return; // If disabled don't pass this point

        float newMaxSpeed = (float) SmartDashboard.getNumber("Drive Speed: ", maxSpeed); // Get new max speed from SmartDashboard
        if (newMaxSpeed != maxSpeed) { // If new max speed is not equal to max speed
            if (newMaxSpeed > 0.4) { // If new max speed is greater than 0.4
                newMaxSpeed = 0.1f; // Set new max speed to 0.1
            }
            if (newMaxSpeed < 0.002) { // If new max speed is less than 0.002
                newMaxSpeed = 0.1f; // Set new max speed to 0.1
            }
            Util.log("Set drive speed to: " + maxSpeed); // Log new max speed
            maxSpeed = newMaxSpeed; // Set max speed to new max speed
            SmartDashboard.putNumber("Drive Speed: ", maxSpeed);// Put new max speed on SmartDashboard
        }

        //Util.log("0: " + vl);
        vl = Math.min(Math.max(vl, -1), 1); // Set left velocity to the minimum of the maximum of left velocity and -1
        vr = Math.min(Math.max(vr, -1), 1); // Set right velocity to the minimum of the maximum of right velocity and -1

        SmartDashboard.putNumber("Left Drive Velocity", vl); // Put left drive velocity on SmartDashboard
        SmartDashboard.putNumber("Right Drive Vel", vr); // Put right drive velocity on SmartDashboard

        float difference = Math.abs(vr-vl); // Difference is the absolute value of right velocity minus left velocity
        SmartDashboard.putNumber("Difference", difference); // Put difference on SmartDashboard

        //SNap Drive velocities
        if (difference < 0.015) { // Creates buffer to allow for strait driving
            float v = Math.max(vl,vr); // Set v to the maximum of left velocity and right velocity
            vl = v;
            vr = v;
        }

        if (difference > 0.15) { // If difference is greater than 0.15 then multiply by 0.85 to make the values smaller and closer together.
            vl *= 0.85; 
            vr *= 0.85;
        } else { // Otherwise multiply by speed falloff to decrease the values gradually by 11% or 0.89
            vl *= speedFalloff;
            vr *= speedFalloff; 
        }

        //Util.log("" + vl);
        

        
        leftMotors.set(vl); // Set left motors to left velocity
        rightMotors.set(vr); // Set right motors to right velocity

        //Robot.controller.xbox1.setRumble(RumbleType.kLeftRumble, Math.abs(vl));
        //Robot.controller.xbox1.setRumble(RumbleType.kRightRumble, Math.abs(vr));
    }

    /** Drive forward/backward at a percent of max speed*/
    public void straight(float speed) {
        //leftMotors.set(speed*maxSpeed);
        //rightMotors.set(speed*maxSpeed);
        //vl += speed*maxSpeed;
        //vr += speed*maxSpeed;
        setDrive(speed, speed); // call setDrive with desired speed
        //Util.log("Set Drive: " + speed + "  " + maxSpeed);
    }

    /** Turn left-negative or right-positive at a speed. */
    void turn(float speed) {
        
    }

    
    /** Set both motor controller groups*/
    public void setDrive (float left, float right) { // Set drive with left and right values
        setLeftDrive(left); // Set the left drive with left value
        setRightDrive(right);// Set the right drive with right value
    }

    public boolean setLeftDrive (float speed) {
        //if (quickStopped) return false;
        vl += speed*maxSpeed; // Set left velocity to speed times max speed

        /*if (Math.abs(speed) > Math.abs(vl)) {
            vl = speed;
        }*/
        return true;
    }

    public boolean setRightDrive (float speed) {
        //if (quickStopped) return false;
        vr += speed*maxSpeed; // Set right velocity to speed times max speed
        
        /*if (Math.abs(speed) > Math.abs(vr)) {
            vr = speed;
        }*/
        
        return true;
    }

    public boolean quickStop() {
        vr *= -0.9; // Reduce value by 90% and reverse it
        vl *= -0.9; // Reduce value by 90% and reverse it

        quickStopped = true; // Set quick stop flag to true
        return true;
    }

    public boolean quickStopRelease() { // Release quick stop
        quickStopped = false;
        return true;
    }
    
}