package frc.robot.systems;

import frc.robot.Util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;



public class Shooter extends System {
    public double shooterSpeed = 0.2;
    //Motors for shooter
    private final CANSparkMax shootMotor1 = new CANSparkMax(10, MotorType.kBrushless);
    private final CANSparkMax shootMotor2 = new CANSparkMax(12, MotorType.kBrushless);
    //private final CANSparkMax motor4 = new CANSparkMax(13, MotorType.kBrushless);
    //private final CANSparkMax motor5 = new CANSparkMax(9, MotorType.kBrushless);
    //private final CANSparkMax motor6 = new CANSparkMax(8, MotorType.kBrushless);

    

    public void update() {

    }
    
    public boolean shoot() {
        //Shoot code
        Util.log("Shooter: shooting note");
        shootMotor1.set(shooterSpeed);
        shootMotor2.set(shooterSpeed);
        return true;
    }

    public boolean checkValue() {
        Util.log(shootMotor1.getAbsoluteEncoder().getPosition() + "");
        Util.log(shootMotor2.getAbsoluteEncoder().getPosition() + "");
        return true;
    }


}