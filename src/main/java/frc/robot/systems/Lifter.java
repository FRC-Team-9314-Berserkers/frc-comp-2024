package frc.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAnalogSensor.Mode;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Util;

public class Lifter extends System {
    double liftSpeed = 0.36;
    
    float topLimit = -5;
    float bottomLimit = -29f;

    private CANSparkMax leftLiftMotor = new CANSparkMax(13, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(9,MotorType.kBrushless);

    public Lifter() {

        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kForward, topLimit);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kReverse, bottomLimit);
        leftLiftMotor.setIdleMode(IdleMode.kBrake);

        rightLiftMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        rightLiftMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        rightLiftMotor.setSoftLimit(SoftLimitDirection.kForward, 27);
        rightLiftMotor.setSoftLimit(SoftLimitDirection.kReverse, 1);
        rightLiftMotor.setIdleMode(IdleMode.kBrake);
        
    }


    public void liftArmUp () {
        rightLiftMotor.set(-liftSpeed); 
        leftLiftMotor.set(liftSpeed);
    }
    
    
    
    public void liftArmDown(){
        rightLiftMotor.set(liftSpeed);
        leftLiftMotor.set(-liftSpeed);
    }

    public void stop() {
        rightLiftMotor.stopMotor();
        leftLiftMotor.stopMotor();

        Util.log("Lifters Stopped. Pos: " + leftLiftMotor.getAnalog(Mode.kRelative).getPosition());
    }



}