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
    lifterUp (new ButtonAction(() -> {Robot.lifter.liftArmUp(); return true;}, () -> {Robot.lifter.stop(); return true;})),
    lifterDown (new ButtonAction(() -> {Robot.lifter.liftArmDown(); return true;}, () -> {Robot.lifter.stop(); return true;})),

    //Loader Actions
    loaderRaise (new ButtonAction(() -> {Robot.loader.raise(); return true;})),
    loaderLower (new ButtonAction(() -> {Robot.loader.lower(); return true;})),
    loaderIntake (new ButtonAction(() -> {Robot.loader.intake(); return true;})),
    

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