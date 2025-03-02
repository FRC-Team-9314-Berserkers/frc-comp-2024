package frc.robot.systems; //  Change to your package

import java.util.HashMap; // Import HashMap
import java.util.Map.Entry; // Import Entry
import java.io.File; // Import File
import java.io.IOException; // Allows for I/O error handling
import java.nio.charset.Charset; // Allows for character encoding
import java.nio.file.Files; // Allows for file reading and writing
import java.nio.file.Paths; // Allows for file paths

import com.fasterxml.jackson.core.JsonProcessingException; // JSON error handling
import com.fasterxml.jackson.databind.*; // JSON data binding

import edu.wpi.first.wpilibj.Joystick; // Iclude Joystick
import edu.wpi.first.wpilibj.XboxController; // Include XboxController
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; // Include SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;// Include SmartDashboard
import edu.wpi.first.wpilibj.Filesystem; // Include Filesystem

import frc.robot.Util; // Import Util Functions
import frc.robot.actions.*; // Import robot functions


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
        Up(0+1000), // D-Pad Up (value 1000)
        Right(90+1000), // D-Pad Right (value 1090)
        Down(180+1000), // D-Pad Down (value 1180)
        Left(270+1000); // D-Pad Left (value 1270)
        
        
        public final int value; // variable for button value

        Button(int value) {
            this.value = value; // set value to value
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
            this.value = value; // set value to value
        }
    }

    //All Controllers
    XboxController xbox1; // Create new XboxController
    Joystick joy1; // Create new Joystick
    
    //** HashMap for buttons bound to actions */
    HashMap<Button, ButtonAction> buttonMap; // Initalize new HashMap for buttons bound to actions
    HashMap<Analog, AnalogAction> analogMap; // Initalize new HashMap for analogs bound to actions

    /** Represents drop down list on smart dashboard */
    private final SendableChooser<String> mapNameChooser; // Create new SendableChooser for controller maps

    private String mapName; // Create new String for map name
    private File inputMapPath = new File(Filesystem.getDeployDirectory() + "/input-maps/"); // Create new File for input map path

    /** Used to keep track of POV */
    int lastPOV = -1; // Create new int for last POV
    int POV = -1; // Create new int for POV
    int releasedPOV = -1; // Create new int for released POV

    public Controller() {
        buttonMap = new HashMap<Button, ButtonAction>(); // Create new HashMap for buttons bound to actions
        analogMap = new HashMap<Analog, AnalogAction>(); // Create new HashMap for analogs bound to actions
 
        mapNameChooser = new SendableChooser<>(); // Create new SendableChooser for controller maps
        for (File f : inputMapPath.listFiles()) { // Create new SendableChooser option for each file in /input-maps/
            Util.log(f.getName()); // Log file name
            mapNameChooser.addOption(f.getName(), f.getName()); // Add file name to SendableChooser
        }

        SmartDashboard.putData("Controll Map", mapNameChooser); // Put SendableChooser on SmartDashboard
        

        xbox1 = new XboxController(0); // Set xbox1 to XboxController on port 0
        joy1 = new Joystick(1); // Set joy1 to Joystick on port 1

        load("logitech-map.json"); // load logitech-map.json (default)
    }
    
    public void update() {
        String current = mapNameChooser.getSelected(); // Get selected map from SendableChooser
        SmartDashboard.putString("Loaded Controll Map: ", mapName); // Put selected map on SmartDashboard
        if (current != null && ! current.equals(mapName)) { // If selected map is not null and not the same as the current map
            load(current); // Load selected map
        }

        lastPOV = POV; // Set lastPOV to POV
        POV = xbox1.getPOV(); // Set POV to xbox1 POV
        releasedPOV = -1; // Set releasedPOV to -1

        if (POV == -1 && lastPOV != -1) { // If POV is -1 and lastPOV is not -1 then set releasedPOV to lastPOV
            releasedPOV = lastPOV;
        }

        for (Button b : Button.values()) { // For each button in Button enum check if it is pressed and run the corresponding action
            checkButton(b);
        }
        for (Analog a : Analog.values()) { // For each analog in Analog enum check if it is surpased the threshold and run the corresponding action
            checkAnalog(a);
        }
    }

    boolean bind(Button b, ButtonAction a) { // Bind a button to an action if possible
        if (! buttonMap.containsKey(b)) {
            buttonMap.put(b, a);
            return true; //Success!
        }
        return false;
    }

    boolean bind(Analog b, AnalogAction a) { // Bind an analog to an action if possible
        if (! analogMap.containsKey(b)) {
            analogMap.put(b, a);
            return true; //Success!
        }
        return false;
    }

    /** Load a json control map into the controller. */
    boolean load (String filename){
        buttonMap.clear(); // Clear buttonMap
        analogMap.clear(); // Clear analogMap

        Util.log("Loading control map '" + filename + "'"); // Log control map loading to console
        String path = Filesystem.getDeployDirectory() + "/input-maps/" + filename; // Set path to /input-maps/<filename>  Filename is the control map to load
        String data; // Create new String for data

        final JsonNode json; // Create new JsonNode for json
        ObjectMapper jsonMapper = new ObjectMapper(); // Create new ObjectMapper for json

        try {
            data = Files.readString(Paths.get(path), Charset.defaultCharset()); // Read file
            json = jsonMapper.readTree(data); // Read data into json
        } catch (JsonProcessingException e) { // Catch JSON error
            Util.log("Control map is invalid."); // Log invalid control map to console
            e.printStackTrace(); // Print stack trace
            return false;
        } catch (IOException e) { // Catch I/O error
            e.printStackTrace(); // Print stack trace
            return false;
        }

        mapName = filename; // Set mapName to filename
        //Util.log("Data from config file:");
        //Util.log(data);


        //Load Buttons from file and bind their actions
        JsonNode buttonMap = json.get("Buttons"); // Get Buttons from json file
        for (JsonNode x : buttonMap) { // For each button in buttonMap
            Entry<String, JsonNode> pair = x.fields().next();// Get button and action pair

            String buttonName = pair.getKey(); // Get button name
            String actionName = pair.getValue().asText(); // Get action name
            Button button; // Create new Button for button
            Action action; // Create new Action for action

            try {
                button = Button.valueOf(buttonName); // Set button to buttonName
                action = Actions.valueOf(actionName).value; // Set action to actionName
            } catch (IllegalArgumentException e) { // Catch illegal argument error
                Util.log("Action name or Button name does not exist!"); // Log action name or button name does not exist to console
                Util.log("Button: " + buttonName + "Action: " + actionName); // Log button and action to console
                Util.log(e.getMessage()); // Log error message to console
                return false;
            }

            if (action instanceof ButtonAction) { // If action is a ButtonAction
                Util.log(buttonName + ": " + actionName); // Log button and action to console
                bind(button, (ButtonAction) action); // Bind button to action
            }
        }


        //Load analogs from json file, bind them:
        JsonNode analogMap = json.get("Analogs"); // Get analogs from json file
        for (JsonNode x : analogMap) { // For each analog in analogMap
            Entry<String, JsonNode> pair = x.fields().next(); // Get analog and action pair

            String analogName = pair.getKey(); // Get analog name
            String actionName = pair.getValue().asText(); // Get action name
            Analog analog; // Create new Analog for analog
            Action action; // Create new Action for action

            try {
                analog = Analog.valueOf(analogName); // Set analog to analogName
                action = Actions.valueOf(actionName).value; // Set action to actionName
            } catch (IllegalArgumentException e) { // Catch illegal argument error
                Util.log("Action or Analog does not exist!"); // Log action or analog does not exist to console
                Util.log("Analog: " + analogName + "Action: " + actionName); // Log analog and action to console
                Util.log(e.getMessage()); // Log error message to console
                return false;
            }

            if (action instanceof AnalogAction) { // If action is an AnalogAction
                Util.log(analogName + ": " + actionName); // Log analog and action to console
                bind(analog, (AnalogAction) action); // Bind analog to action
            }
        }
        return true; //Add Variable For Sucsses Here
    }


    //Test a if a button is pressed and run corrosponding action
    protected boolean checkButton(Button b) {
        if (b.value >= 1000 && b.value < 2000) { // If button value is between 1000 and 2000 meaning it is a POV
            if (POV == b.value-1000) { // If POV is equal to button value - 1000
                if (buttonMap.get(b) == null) { // If buttonMap does not contain button
                    return false;
                }
                
                if (lastPOV == -1) { // If lastPOV is -1
                    return buttonMap.get(b).press();
                }

                if (POV == lastPOV) { // If POV is equal to lastPOV
                    return buttonMap.get(b).hold(); // Hold button and run hold action
                }
                return false;
            }

            if (releasedPOV != -1 && b.value-1000 == releasedPOV) { // Check if POV is released if it is mapped to a button
                if (buttonMap.get(b) == null) {
                    return false;
                }
                return buttonMap.get(b).release();
            }

            return false;
        }
        
        if (xbox1.getRawButtonPressed(b.value)) { // If button is pressed
            if (buttonMap.get(b) == null) { // If the button pressed is not maped to an action
                return false;
            }
            return buttonMap.get(b).press(); // Run press action and return result
        }

        if (xbox1.getRawButtonReleased(b.value)) { // If button is released
            if (buttonMap.get(b) == null) { // If the button released is not maped to an action
                return false;
            }
            return buttonMap.get(b).release(); // Run release action and return result
        }

        if (xbox1.getRawButton(b.value)) { // If button is held
            if (buttonMap.get(b) == null) { // If the button held is not maped to an action
                return false;
            }
            return buttonMap.get(b).hold(); // Run hold action and return result
        }
        return false;
    }

    //Test a if a button is pressed and run corrosponding action
    //But analog
    protected boolean checkAnalog(Analog a) { // Check if an analog is a value and run the corresponding action
        float value = (float) xbox1.getRawAxis(a.value); // Get value of analog data from xbox1
        if (analogMap.get(a) != null) { // If analogMap contains analog
            if (value > 0.1 || value < -0.1) { // If value is greater than 0.1 or less than -0.1
                //Util.log(a.toString() + ": " + value);
            }
            return analogMap.get(a).set(value); // Set value, run action, and return result
        }

        return false;
            
    }


}