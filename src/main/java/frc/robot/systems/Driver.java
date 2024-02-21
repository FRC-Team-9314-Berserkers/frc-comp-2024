package frc.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;


public class Driver extends System{
    float maxSpeed;
    float speedFalloff;

    //Drive Motors
    private final CANSparkMax motorLeft1 = new CANSparkMax(4, MotorType.kBrushed);
    private final CANSparkMax motorRight1 = new CANSparkMax(6, MotorType.kBrushed);
    private final CANSparkMax motorLeft2 = new CANSparkMax(5, MotorType.kBrushed);
    private final CANSparkMax motorRight2 = new CANSparkMax(7, MotorType.kBrushed);

    //Drive Motor Groups
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(motorLeft1, motorLeft2);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(motorRight1, motorRight2);

  
    public Driver() {
        maxSpeed = 0.4f;
        speedFalloff = 1;
        rightMotors.setInverted(true);
        
    }

    /** Drive forward/backward at a percent of max speed*/
    void straight(float speed) {
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
        leftMotors.set(speed*maxSpeed);
        return true;
    }

    public boolean setRightDrive (float speed) {
        rightMotors.set(speed*maxSpeed);
        return true;
    }

    
}