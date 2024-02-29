package frc.robot.actions;

import frc.robot.Robot;

public enum Actions {
    /*ButtonAction shoot = new ButtonAction(() -> {return Robot.shooter.shoot();});
    AnalogAction setLeftDriveSpeed = new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);});
    AnalogAction setRightDriveSpeed = new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);});
    ButtonAction measure = new ButtonAction(() -> {return Robot.shooter.checkValue();});*/

    shoot (new ButtonAction(() -> {return Robot.shooter.shoot();})),
    setLeftDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);})),
    setRightDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);})),
    measure (new ButtonAction(() -> {return Robot.shooter.checkValue();}));

    public final Action value;
    Actions(Action value) {
        this.value = value;
    }
}