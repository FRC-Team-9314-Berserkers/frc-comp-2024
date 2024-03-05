package frc.robot.actions;

import frc.robot.Robot;

public enum Actions {
    //Shooter Actions
    shoot (new ButtonAction(() -> {return Robot.shooter.shoot();})),
    measure (new ButtonAction(() -> {return Robot.shooter.checkValue();})),

    //Driver Actions
    setLeftDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);})),
    setRightDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);})),
    quickStop (new ButtonAction(() -> {return Robot.driver.quickStop();})),
    
    //Lifter Actions
    lifterUp (new ButtonAction(() -> {Robot.lifter.liftArmUp(); return true;})),
    lifterDown (new ButtonAction(() -> {Robot.lifter.liftArmDown(); return true;}));

    public final Action value;
    Actions(Action value) {
        this.value = value;
    }
}