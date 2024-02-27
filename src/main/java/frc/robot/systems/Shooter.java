package frc.robot.systems;

import frc.robot.Util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Shooter extends System {
    //Motors for shooter
    private final CANSparkMax motor1 = new CANSparkMax(10, MotorType.kBrushless);
    private final CANSparkMax motor2 = new CANSparkMax(11, MotorType.kBrushless);
    private final CANSparkMax motor3 = new CANSparkMax(12, MotorType.kBrushless);
    private final CANSparkMax motor4 = new CANSparkMax(13, MotorType.kBrushless);
    private final CANSparkMax motor5 = new CANSparkMax(9, MotorType.kBrushless);
    private final CANSparkMax motor6 = new CANSparkMax(8, MotorType.kBrushless);

    

    public void update() {

    }
    
    public boolean shoot() {
        //Shoot code
        Util.log("Shooter: shooting note");
        //motor1.set(0.2);
        //motor2.set(0.2);
        //motor3.set(0.2);
        //motor4.set(0.2);
        //motor5.set(0.2);
        motor6.set(0.2);

        return true;
    }

    public boolean checkValue() {
        Util.log(motor1.getAbsoluteEncoder().getPosition() + "");
        
        return true;
    }


}