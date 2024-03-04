package frc.robot.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Util;

public class Lifter extends System {
    double liftSpeed = 0.2;
    
    private CANSparkMax leftLiftMotor = new CANSparkMax(-1, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(-1,MotorType.kBrushless);
    void liftArmUp () {
     //rightLiftmortor.set() 
    }
    
    
    
    void liftArmDown(){
        //leftLiftMotor.set();
        //leftLiftMotor.
    }



}