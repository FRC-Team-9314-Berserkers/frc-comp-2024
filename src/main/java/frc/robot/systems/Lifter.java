package frc.robot.systems;

import com.revrobotics.CANSparkMax; // Import CANSparkMax library
import com.revrobotics.CANSparkBase.IdleMode; // Import IdleMode library
import com.revrobotics.CANSparkBase.SoftLimitDirection; // Import SoftLimitDirection library
import com.revrobotics.CANSparkLowLevel.MotorType;// Import MotorType library
import com.revrobotics.SparkAnalogSensor.Mode;// Import Mode library

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; // Import MotorControllerGroup library
import frc.robot.Util; // Import Uitlity commands

public class Lifter extends System {
    double liftSpeed = 0.3; // Set lift speed
    
    float topLimit = -5; // Set top limit
    float bottomLimit = -21f;// Set bottom limit

    private CANSparkMax leftLiftMotor = new CANSparkMax(13, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(9,MotorType.kBrushless);

    public Lifter() {

        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kForward, false);
        leftLiftMotor.enableSoftLimit(SoftLimitDirection.kReverse, false);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kForward, topLimit);
        leftLiftMotor.setSoftLimit(SoftLimitDirection.kReverse, bottomLimit);
        leftLiftMotor.setIdleMode(IdleMode.kBrake);

        rightLiftMotor.enableSoftLimit(SoftLimitDirection.kForward, false);
        rightLiftMotor.enableSoftLimit(SoftLimitDirection.kReverse, false);
        rightLiftMotor.setSoftLimit(SoftLimitDirection.kForward, 27);
        rightLiftMotor.setSoftLimit(SoftLimitDirection.kReverse, 1);
        rightLiftMotor.setIdleMode(IdleMode.kBrake);
        
    }


    public void liftArmUp () {
        rightLiftMotor.set(-liftSpeed); // Set right lift motor speed which is inverted
        leftLiftMotor.set(liftSpeed); // Set left lift motor speed
    }
    
    
    
    public void liftArmDown(){ // Inverse opposite of liftArmUp
        rightLiftMotor.set(liftSpeed);
        leftLiftMotor.set(-liftSpeed);
    }

    public void stop() { // Stop lift motors and log position
        rightLiftMotor.stopMotor();
        leftLiftMotor.stopMotor();

        Util.log("Lifters Stopped. Pos: " + leftLiftMotor.getAnalog(Mode.kRelative).getPosition());
    }



}