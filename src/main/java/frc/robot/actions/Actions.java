package frc.robot.actions;

import frc.robot.Robot;

public class Actions {
    public static ButtonAction shoot = new ButtonAction(() -> {return Robot.shooter.shoot();});

    //What is wrong with this?
    public static AnalogAction setLeftDriveSpeed = new AnalogAction((float x) -> {return Robot.driver.setLeftDrive(x);});
}