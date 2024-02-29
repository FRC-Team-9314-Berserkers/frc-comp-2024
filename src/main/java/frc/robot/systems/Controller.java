package frc.robot.systems;

import java.util.HashMap;
import java.util.Map.Entry;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Filesystem;

import frc.robot.Util;
import frc.robot.actions.*;



public class Controller extends System {

    /** Enumerations for all buttons */
    enum Button {
        //Switch on back of controller needs to be X, not D
        //Mode light off
        A(1),
        B(2),
        X(3),
        Y(4),
        LeftBumper(5),
        RightBumper(6),
        Back(7),
        Start(8),
        LeftStick(9),
        RightStick(10),

        //POV
        Up(0+1000),
        Right(90+1000),
        Down(180+1000),
        Left(270+1000);
        
        
        public final int value;

        Button(int value) {
            this.value = value;
        }
    }
    enum Analog {
        LeftStickX(0),
        LeftStickY(1),
        LeftTrigger(2),
        RightTrigger(3),
        RightStickX(4),
        RightStickY(5);

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

        load("default-config.json");
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
    boolean load (String filename){

        String path = Filesystem.getDeployDirectory() + "/input-maps/" + filename;
        String data;

        final JsonNode json;
        ObjectMapper jsonMapper = new ObjectMapper();

        try {
            data = Files.readString(Paths.get(path), Charset.defaultCharset());
            json = jsonMapper.readTree(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Util.log("Data from config file:");
        Util.log(data);

        JsonNode buttonMap = json.get("Buttons");
        for (JsonNode x : buttonMap) {
            Entry<String, JsonNode> pair = x.fields().next();
            String buttonName = pair.getKey();
            String actionName = pair.getValue().asText();
            Util.log(buttonName + ": " + actionName);

            Button button = Button.valueOf(buttonName);
            Action action = Actions.valueOf(actionName).value;

            Util.log("   -" + Actions.valueOf(actionName).name());

            if (action instanceof ButtonAction) {
                bind(button, (ButtonAction) action);
            }

           
        }


        
        
       

        return true; //Add Variable For Sucsses Here
    }

    //Test a if a button is pressed and run corrosponding action
    protected boolean checkButton(Button b) {
        if (b.value >= 1000 && b.value < 2000) {
            if (xbox1.getPOV() == b.value-1000) {
                if (buttonMap.get(b) == null) {
                    return false;
                }
                return buttonMap.get(b).press();
            }
            return false;
        }
        
        if (xbox1.getRawButtonPressed(b.value)) {
            //Util.log(b.toString() + b.value);
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
        if (analogMap.get(a) != null) {
            if (value > 0.1 || value < -0.1) {
                //Util.log(a.toString() + ": " + value);
            }
            return analogMap.get(a).set(value);
        }

        return false;
            
    }


}