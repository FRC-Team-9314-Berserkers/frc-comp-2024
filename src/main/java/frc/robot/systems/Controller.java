package frc.robot.systems;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Filesystem;

import frc.robot.actions.*;

public class Controller extends System {

    /** Enumerations for all buttons */
    enum Button {
        A,
        B,
        X,
        Y,
        BACK,
        START,
        HOME,
        LB,
        RB,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    enum Analog {
        LeftTrigger,
        RightTrigger,
        LeftX,
        LeftY,
        RightX,
        RightY
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

    }
    
    public void update() {
        if (xbox1.button(XboxController.Button.kA, null) == 1);

        if (xbox1.getAButtonPressed())
            if (buttonMap.get(Button.A) != null)
                buttonMap.get(Button.A).press();
        
        xbox1.getBButtonPressed();
            buttonMap.get(Button.B).press();

        xbox1.getXButtonPressed();
            buttonMap.get(Button.X).press();

        xbox1.getYButtonPressed();
            buttonMap.get(Button.Y).press();

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



}