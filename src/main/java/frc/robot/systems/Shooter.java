package frc.robot.systems;

import frc.robot.Robot;
import frc.robot.Util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;



public class Shooter extends System {
    private enum actions { // Enum for the different states of the shooter
        active,
        stop,
        reverse
    }
    public final double shooterSpeed = 0.9; // Speed of the shooter
    public final double reverseSpeed = -0.24; // Speed of the shooter when in reverse
    public actions active; // Set the current state of the shooter
    
    //Motors for shooter
    private final CANSparkMax shootMotor1 = new CANSparkMax(8, MotorType.kBrushless);// Intialize the first shooter motor
    private final CANSparkMax shootMotor2 = new CANSparkMax(12, MotorType.kBrushless);// Intialize the second shooter motor
    
    private final MotorControllerGroup shootMotors = new MotorControllerGroup(shootMotor1, shootMotor2); // Group the two motors together
    public Shooter() {
        super();
        active = actions.stop; // Set the initial state of the shooter to stop

        shootMotor1.setIdleMode(IdleMode.kCoast);// Set the idle mode of the first shooter motor to coast
        shootMotor2.setIdleMode(IdleMode.kCoast);// Set the idle mode of the second shooter
        shootMotor2.setInverted(true);// Invert the second shooter motor

        disabled = false;
    }

    public void update() {
        if (active != actions.stop && ! disabled) {
            if (active == actions.active) {
                shootMotors.set(shooterSpeed);
            }
            if (active == actions.reverse) {
                shootMotors.set(reverseSpeed);
            }
            
        } else {
            shootMotors.stopMotor();
        }
        
    }
    
    public void start() {
        active = actions.active;
    }

    public void stop() {
        active = actions.stop;
    }

    public void reverse() {
        active = actions.reverse;
    }

    public void toggle() {
        if (active != actions.active) {
            active = actions.active;
        } else {
            active = actions.stop;
        }
    }

    public boolean shoot() {
        //Shoot code
        Util.log("Shooter: shooting note");
        Robot.loader.ejectNote();// Eject the note into the shooter
        active = actions.active; // Enable the shooter
        
        return true;
    }

    public boolean checkValue() {
        Util.log("Shooter Velocity: " + shootMotor1.getEncoder().getVelocity());
        Util.log("Shooter Position: " + shootMotor1.getEncoder().getPosition());
        return true;
    }
}