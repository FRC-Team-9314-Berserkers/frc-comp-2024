package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;


public class Driver {
    float maxSpeed;

    //Drive Motors
    private final CANSparkMax motorLeft1 = new CANSparkMax(6, MotorType.kBrushed);
    private final CANSparkMax motorRight1 = new CANSparkMax(4, MotorType.kBrushed);
    private final CANSparkMax motorLeft2 = new CANSparkMax(7, MotorType.kBrushed);
    private final CANSparkMax motorRight2 = new CANSparkMax(5, MotorType.kBrushed);

    //Drive Motor Groups
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(motorLeft1, motorLeft2);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(motorRight1, motorRight2);

  
    Driver() {
        maxSpeed = 1;
        rightMotors.setInverted(true);
        
    }

    /** Drive f rward/backward at a percent of max speed*/
    void straight(float speed) {
        float spd = speed*maxSpeed;
        leftMotors.set(spd);
        rightMotors.set(spd);
        
    }

    /** Turn left-negative or right-positive at a speed. */
    void turn(float speed) {
        
    }

    /** Set both motor controller groups*/
    void setDrive (float left, float right) {
        leftMotors.set(left*maxSpeed);
        rightMotors.set(right*maxSpeed);
    }


    
}