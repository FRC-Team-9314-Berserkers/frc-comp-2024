package frc.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    
    /** Driving is disabled if true. */
    boolean disabled = false;

    /** All Drive Motors: */
    private final CANSparkMax motorLeft1 = new CANSparkMax(4, MotorType.kBrushed);
    private final CANSparkMax motorRight1 = new CANSparkMax(6, MotorType.kBrushed);
    private final CANSparkMax motorLeft2 = new CANSparkMax(5, MotorType.kBrushed);
    private final CANSparkMax motorRight2 = new CANSparkMax(7, MotorType.kBrushed);

    /** Drive Motor Groups */
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(motorLeft1, motorLeft2);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(motorRight1, motorRight2);

    
  
    public Driver() {
        maxSpeed = 0.037f;
        speedFalloff = 0.93f;
        rightMotors.setInverted(true);
        
        vl = 0;
        vr = 0;
    }

    /** This update function is inherited from the System.  It is
     * called from Robot every periodic iteration.
     */
    public void update() {
        if (disabled) return;

        vl = Math.min(Math.max(vl, -1), 1);
        vr = Math.min(Math.max(vr, -1), 1);

        leftMotors.set(vl);
        rightMotors.set(vr);

        SmartDashboard.putNumber("Left Drive Velocity", vl);
        SmartDashboard.putNumber("Right Drive Vel", vr);

        SmartDashboard.putNumber("Difference", Math.abs(vr-vl));

        if (Math.abs(vl-vr) < 0.034) {
            float v = (vl+vr)/2;
            vl = v;
            vr = v;
        }

        if (Math.abs(vl-vr) > 0.1) {
            //float v = (vl+vr)/2;
            vl *= 0.9;
            vr *= 0.9;
        } else {
            vl *= speedFalloff;
            vr *= speedFalloff; 
        }

       
    }

    /** Drive forward/backward at a percent of max speed*/
    public void straight(float speed) {
        float spd = speed*maxSpeed;
        leftMotors.set(spd);
        rightMotors.set(spd);
        
    }

    /** Turn left-negative or right-positive at a speed. */
    void turn(float speed) {
        
    }

    
    /** Set both motor controller groups*/
    public void setDrive (float left, float right) {
        setLeftDrive(left);
        setRightDrive(left);
    }

    public boolean setLeftDrive (float speed) {
        //speed = speed*maxSpeed;

        vl += speed*maxSpeed;

        /*if (Math.abs(speed) > Math.abs(vl)) {
            vl = speed;
        }*/
        return true;
    }

    public boolean setRightDrive (float speed) {
        //speed = ;

        vr += speed*maxSpeed;
        
        /*if (Math.abs(speed) > Math.abs(vr)) {
            vr = speed;
        }*/
        
        return true;
    }

    public boolean quickStop() {
        vr *= -0.9;
        vl *= -0.9;
        return true;
    }
    //protected void 
    
}