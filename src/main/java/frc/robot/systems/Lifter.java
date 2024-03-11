package frc.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Util;

public class Lifter extends System {
    double liftSpeed = 0.2;
    
    float topLimit = 90;
    float bottomLimit = -90;

    private CANSparkMax leftLiftMotor = new CANSparkMax(12, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(-1,MotorType.kBrushless);

    public Lifter() {

        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kForward, topLimit);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kReverse, bottomLimit);

        rightLiftMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        rightLiftMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        rightLiftMotor.setSoftLimit(SoftLimitDirection.kForward, topLimit);
        rightLiftMotor.setSoftLimit(SoftLimitDirection.kReverse, bottomLimit);
    }


    public void liftArmUp () {
        rightLiftMotor.set(liftSpeed); 
        leftLiftMotor.set(liftSpeed);
    }
    
    
    
    public void liftArmDown(){
        rightLiftMotor.set(-liftSpeed);
        leftLiftMotor.set(-liftSpeed);
    }



}