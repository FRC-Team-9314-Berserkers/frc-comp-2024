package frc.robot;

import java.util.ArrayList; // Include ArrayList files

public class Util {
    public static void log(String message) { // Faster way to print data to the console.
        System.out.println(message);
        
    }

    public static float lerp(float a, float b, float f) { // Linear interpolation function (Allows for smooth transitions between two values)
        return (float) (a * (1.0 - f)) + (b * f);
    }

    public static float[] getClosestAbove(ArrayList<float[]> list, float actual) {
        float[] closestAbove = {10.0f, 0.0f};
      
        //Find the closest position value in array that is greater than actual position.
        for (float[] pair : list) {
            if (pair[0] > actual) {
                if (pair[0] < closestAbove[0]) {
                    closestAbove = pair;
                }
            }
        }
        
        
        return closestAbove;
    }
    
    public static float[] getClosestBelow(ArrayList<float[]> list, float actual) {
        float[] closestBelow = {-10.0f, 0.0f};
    
        //Find the closest position value in array that is less than actual position.
        for (float[] pair : list) {
            if (pair[0] <= actual) {
                if (pair[0] > closestBelow[0]) {
                    closestBelow = pair;
    
                }
            }
        }
        
        return closestBelow;
    }
}
