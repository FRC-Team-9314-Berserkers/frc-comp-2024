package frc.robot.actions;

import frc.robot.Robot;

public enum Actions {
    //Shooter Actions
    shoot (new ButtonAction(() -> {Robot.shooter.start(); return true;}, () -> {Robot.shooter.stop(); return true;})), // Start and stop the shooter
    toggleShoot (new ButtonAction(() -> {Robot.shooter.toggle(); return true;})), // Toggle the shooter
    shooterReverse (new ButtonAction(() -> {Robot.shooter.reverse(); return true;}, () -> {Robot.shooter.stop(); return true;})), // Reverse the shooter firing direction
    measure (new ButtonAction(() -> {return Robot.shooter.checkValue();})), // Log velocity and position of the shooter

    //Driver Actions
    setLeftDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);})), // Set the left drive speed
    setRightDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);})), // Set the right drive speed
    quickStop (new ButtonAction(() -> {return Robot.driver.quickStop();}, () -> {return Robot.driver.quickStopRelease();})), // Quick stop the robot
    
    //Lifter Actions
    lifterUp (new ButtonAction(() -> {Robot.lifter.liftArmUp(); return true;}, () -> {Robot.lifter.stop(); return true;})), // Lift the lifter arm up
    lifterDown (new ButtonAction(() -> {Robot.lifter.liftArmDown(); return true;}, () -> {Robot.lifter.stop(); return true;})), // Lift the lifter arm down

    //Loader Actions
    loaderRaise (new ButtonAction(() -> {Robot.loader.raise(); return true;})), // Raise the loader
    loaderLower (new ButtonAction(() -> {Robot.loader.lower(); return true;})), // Lower the loader
    loaderIntake (new ButtonAction(() -> {Robot.loader.intake(); return true;}, () -> {Robot.loader.stopBay(); return true;})), // Intake the notes
    loaderEject (new ButtonAction(() -> {Robot.loader.ejectNote(); return true;})), // Eject the notes
    loaderIntakeAnalog (new AnalogAction((Float x) -> {if (x>0.6) {Robot.loader.intake(); return true;} else if (x>0.2f && x<0.6f) {Robot.loader.stopBay(); return false;} return false;})), // Intake the notes with the analog triggers
    loaderEjectAnalog (new AnalogAction((Float x) -> {if (x>0.5) Robot.loader.ejectNote(); return true;})), // Eject the notes with the analog triggers
    
    //Camera Actions
    cameraLeft (new ButtonAction(() -> {Robot.vision.backCameraMove(-1); return true;})), // Move the camera left
    cameraRight (new ButtonAction(() -> {Robot.vision.backCameraMove(1); return true;})), // Move the camera right

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
        this.value = value; // Set the value of the enum to the value of the action
    }
}