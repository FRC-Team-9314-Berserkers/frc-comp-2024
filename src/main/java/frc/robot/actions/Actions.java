package frc.robot.actions;

import frc.robot.Robot;

public class Actions {
    public static ButtonAction shoot = new ButtonAction(() -> {return Robot.shooter.shoot();});

    public static AnalogAction setLeftDriveSpeed = new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);});
    public static AnalogAction setRightDriveSpeed = new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);});


}