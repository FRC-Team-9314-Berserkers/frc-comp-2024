package frc.robot.systems;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Util;
import frc.robot.actions.*;

public class Controller extends System {

    /** Enumerations for all buttons */
    enum Button {
        A (2),
        B(3),
        X(1),
        Y(4),
        LT(7),
        RT(8),
        //HOME(),
        LB(5),
        RB(6),
        BACK(9),
        START(10),
        LS(11),
        RS(12);
        //fake1(13),
        //fake2(14),
        //fake3(15),
        //fake4(16);

        /*UP,
        DOWN,
        LEFT,
        RIGHT*/

        public final int value;

        Button(int value) {
            this.value = value;
        }
    }
    enum Analog {
        LeftTrigger(2),
        RightTrigger(3),
        LeftX(0),
        LeftY(1),
        RightX(4),
        RightY(5);

        public final int value;

        Analog(int value) {
            this.value = value;
        }
    }

    //All Controllers
    XboxController xbox1;
    Joystick joy1;
    
    //** HashMap for buttons bound to actions */

    HashMap<Button, ButtonAction> buttonMap;
    HashMap<Analog, AnalogAction> analogMap;

    public Controller() {
        buttonMap = new HashMap<Button, ButtonAction>();
        analogMap = new HashMap<Analog, AnalogAction>();

        xbox1 = new XboxController(0);
        joy1 = new Joystick(1);

        bind(Button.A, Actions.shoot);
        bind(Analog.LeftY, Actions.setLeftDriveSpeed);
        bind(Analog.RightY, Actions.setRightDriveSpeed);

    }
    
    public void update() {
        for (Button b : Button.values()) {
            checkButton(b);
        }
        for (Analog a : Analog.values()) {
            checkAnalog(a);
        }
    }

    boolean bind(Button b, ButtonAction a) {
        if (! buttonMap.containsKey(b)) {
            buttonMap.put(b, a);
            return true; //Success!
        }
        return false;
    }

    boolean bind(Analog b, AnalogAction a) {
        if (! analogMap.containsKey(b)) {
            analogMap.put(b, a);
            return true; //Success!
        }
        return false;
    }

    /** load function work in progress. */
    boolean load (String file){
        String data;

        data = "";

        Filesystem.getDeployDirectory();

        ObjectMapper jsonMapper = new ObjectMapper();

        try {
            jsonMapper.readTree(data);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       

        return true; //Add Variable For Sucsses Here
    }

    //Test a if a button is pressed and run corrosponding action
    protected boolean checkButton(Button b) {
        if (xbox1.getRawButtonPressed(b.value)) {
            Util.log(b.toString() + b.value);
            if (buttonMap.get(b) == null) {
                return false;
            }
            return buttonMap.get(b).press();
        }
        return false;
    }

    //Test a if a button is pressed and run corrosponding action
    //But analog
    protected boolean checkAnalog(Analog a) {
        float value = (float) xbox1.getRawAxis(a.value);

        //if (a == Analog.LeftY)
        //    Util.log(a.toString() + a.value + ": " + value);

        if (analogMap.get(a) != null) {
            return analogMap.get(a).set(value);
        }

        return false;
            
    }


}