// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.actions.Actions;
import frc.robot.autonomous.AutoAction;
import frc.robot.autonomous.AutoMode;
import frc.robot.systems.*;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
 
  int AutonomousTime = 50;
  //Drive Motor Controllers now in Driver system

  //Systems
  public static Driver driver;
  public static Shooter shooter;
  public static Controller controller;
  public static Lifter lifter;
  public static Loader loader;
  private Vision vision;


  public static String bananana = "Hi You may not know this but I put this everywhere so everything Triggers it";

  AutoMode auto1;
  private AutoMode autoPrime;

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Setup Systems
    driver = new Driver();
    shooter = new Shooter();
    controller = new Controller();
    lifter = new Lifter();
    loader = new Loader();
    vision = new Vision();

    //Set up actions
    //WIP

    vision.start();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    Util.log("Autonomous started.");
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    //Util.log("Auto selected: " + m_autoSelected);

    auto1 = new AutoMode("auto1");
    auto1.add(new AutoAction(() -> {Util.log("111"); return true;}));
    auto1.add(new AutoAction(() -> {Util.log("222"); return true;}));

    autoPrime = new AutoMode("Auto Drive Forward");
    autoPrime.add(new AutoAction(4, ()->{return true;}, () -> {Robot.driver.straight(-0.65f);return true;}));
    autoPrime.add(new AutoAction(0.2f, () -> {Robot.driver.straight(0.0f);return true;}));

    autoPrime.add(new AutoAction(0.5f, ()->{Robot.shooter.shoot(); return true;}));
    autoPrime.add(new AutoAction(1, () -> {Robot.driver.straight(0.0f);return true;}));

    autoPrime.add(new AutoAction(1, ()->{return true;}, () -> {Robot.driver.straight(-0.6f);return true;}));
    autoPrime.add(new AutoAction(2, () -> {Robot.driver.straight(0.0f);return true;}));
    autoPrime.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    /*switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        Util.log("One");
        break;
      case kDefaultAuto:
        Util.log("Two");
        break;
      default:
        // Put default auto code here
        //driver.straight(0.2f);
        break;
    }*/

    autoPrime.update();

    driver.update();
    shooter.update();
    lifter.update();
    loader.update();
    vision.update();
  }


  @Override
  public void teleopInit() {
    Util.log("Teleop started.");
    //shooter.start();
  }


  @Override
  public void teleopPeriodic() {
    controller.update();

    driver.update();
    shooter.update();
    lifter.update();
    loader.update();
    vision.update();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Util.log("Robot Disabled.");
    lifter.stop();
    shooter.stop();
    //driver.quickStop();

    Util.log(bananana);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
