package frc.robot.actions;

import frc.robot.Robot;

public enum Actions {
    /*ButtonAction shoot = new ButtonAction(() -> {return Robot.shooter.shoot();});
    AnalogAction setLeftDriveSpeed = new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);});
    AnalogAction setRightDriveSpeed = new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);});
    ButtonAction measure = new ButtonAction(() -> {return Robot.shooter.checkValue();});*/

    //Shooter Actions
    shoot (new ButtonAction(() -> {return Robot.shooter.shoot();})),
    measure (new ButtonAction(() -> {return Robot.shooter.checkValue();})),

    //Driver Actions
    setLeftDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);})),
    setRightDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);}));
    

    public final Action value;
    Actions(Action value) {
        this.value = value;
    }
}