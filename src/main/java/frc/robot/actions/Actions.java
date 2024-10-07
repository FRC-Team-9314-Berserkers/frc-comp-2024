package frc.robot.actions;

import frc.robot.Robot;

public enum Actions {
    //Shooter Actions
    shoot (new ButtonAction(() -> {Robot.shooter.start(); return true;}, () -> {Robot.shooter.stop(); return true;})),
    toggleShoot (new ButtonAction(() -> {Robot.shooter.toggle(); return true;})),
    shooterReverse (new ButtonAction(() -> {Robot.shooter.reverse(); return true;}, () -> {Robot.shooter.stop(); return true;})),
    measure (new ButtonAction(() -> {return Robot.shooter.checkValue();})),

    //Driver Actions
    setLeftDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);})),
    setRightDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);})),
    quickStop (new ButtonAction(() -> {return Robot.driver.quickStop();}, () -> {return Robot.driver.quickStopRelease();})),
    
    //Lifter Actions
    lifterUp (new ButtonAction(() -> {Robot.lifter.liftArmUp(); return true;}, () -> {Robot.lifter.stop(); return true;})),
    lifterDown (new ButtonAction(() -> {Robot.lifter.liftArmDown(); return true;}, () -> {Robot.lifter.stop(); return true;})),

    //Loader Actions
    loaderRaise (new ButtonAction(() -> {Robot.loader.raise(); return true;})),
    loaderLower (new ButtonAction(() -> {Robot.loader.lower(); return true;})),
    loaderIntake (new ButtonAction(() -> {Robot.loader.intake(); return true;}, () -> {Robot.loader.stopBay(); return true;})),
    loaderEject (new ButtonAction(() -> {Robot.loader.ejectNote(); return true;})),
    loaderIntakeAnalog (new AnalogAction((Float x) -> {if (x>0.6) {Robot.loader.intake(); return true;} else if (x>0.2f && x<0.6f) {Robot.loader.stopBay(); return false;} return false;})),
    loaderEjectAnalog (new AnalogAction((Float x) -> {if (x>0.5) Robot.loader.ejectNote(); return true;})),
    
    //Camera Actions
    cameraLeft (new ButtonAction(() -> {Robot.vision.backCameraMove(-1); return true;})),
    cameraRight (new ButtonAction(() -> {Robot.vision.backCameraMove(1); return true;})),

    /* QUICK GUIDE: 
         Name:   |       Ignore this      |   What it does:        |  Also ignore!          
    ^^^^^^^^^^^^^                          ^^^^^^^^^^^^^^^^^^^^^^^^  
    - Make sure to use a unique name.
    - Also "what it does" is important to change:
         - return true (or false, it doesn't really matter (yet))
         - Probably something like Robot.lifter.liftArmDown();

    Also, is the loader in Robot.java as a Robot memeber variable?
        (It is, I checked)

    */
    
    templateAction1 (new ButtonAction(() -> {/* Do something here; */ return true;})),
    templateAction2 (new ButtonAction(() -> {/* Do something here; */ return true;}));

    public final Action value;
    Actions(Action value) {
        this.value = value;
    }
}