package frc.robot.systems;

import frc.robot.Robot;
import frc.robot.Util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;



public class Shooter extends System {
    public final double shooterSpeed = 0.6;
    public boolean active;
    
    //Motors for shooter
    private final CANSparkMax shootMotor1 = new CANSparkMax(8, MotorType.kBrushless);
    private final CANSparkMax shootMotor2 = new CANSparkMax(12, MotorType.kBrushless);
    
    private final MotorControllerGroup shootMotors = new MotorControllerGroup(shootMotor1, shootMotor2);
    public Shooter() {
        super();
        active = false;

        shootMotor1.setIdleMode(IdleMode.kCoast);
        shootMotor2.setIdleMode(IdleMode.kCoast);
        shootMotor2.setInverted(true);

        disabled = false;
    }

    public void update() {
        if (active && ! disabled) {
            shootMotors.set(shooterSpeed);
        }
        
    }
    
    public void start() {
        active = true;
    }

    public boolean shoot() {
        //Shoot code
        Util.log("Shooter: shooting note");
        //
        //Robot.loader.ejectNote();
        return true;
    }

    public boolean checkValue() {
        Util.log("Shooter Velocity: " + shootMotor1.getEncoder().getVelocity());
        Util.log("Shooter Position: " + shootMotor1.getEncoder().getPosition());
        return true;
    }


}