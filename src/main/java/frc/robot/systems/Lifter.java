package frc.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Util;

public class Lifter extends System {
    double liftSpeed = 0.2;
    
    private CANSparkMax leftLiftMotor = new CANSparkMax(12, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor;// = new CANSparkMax(-1,MotorType.kBrushless);

    public Lifter() {
        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kForward, 90);

        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kReverse, -90);

    }


    public void liftArmUp () {
        //rightLiftmortor.set() 
        leftLiftMotor.set(0.1);
    }
    
    
    
    public void liftArmDown(){
        //rightLiftMotor.set(0.2);
        leftLiftMotor.set(-0.1);
    }



}