package frc.robot.systems;

import org.opencv.core.Mat; // Stores image data
import org.opencv.core.Point; // Represents a point in 2D space (usually image)
import org.opencv.core.Scalar; // 4-element vector used to store color data
import org.opencv.imgproc.Imgproc; // Image processing methods

import edu.wpi.first.apriltag.AprilTagDetection; // Import AprilTagDetection class
import edu.wpi.first.apriltag.AprilTagDetector; // Import AprilTagDetector class
import edu.wpi.first.cameraserver.CameraServer; // Import CameraServer class
import edu.wpi.first.cscore.CvSink; // Handles input frames from camera
import edu.wpi.first.cscore.CvSource; // Handles output frames to dashboard
import edu.wpi.first.cscore.UsbCamera; // Represents a USB camera and allows for settings such as resolution or framerate.
import frc.robot.Util;

//Camera Servo Mount
import edu.wpi.first.wpilibj.Servo;


public class Vision extends System { // Create vision class
    protected Thread vThread; // Create a thread for vision

    private int width, height; // Camera width and height variables

    protected UsbCamera back, front; // Create USB cameras for back and front
    protected AprilTagDetector tagDetector; // Create AprilTagDetector object

    Mat frame, greyFrame; // Create Mats for frame and greyFrame

    private CvSink cvSink; // Create CvSink object
    private CvSource outputStream; // Create CvSource object which is stream output

    private boolean disableDetection = true; // Disable detection by default

    private Servo backMount; // Create a servo for the back mount
    private double backMountPos; // Create a double for the back mount position

    public Vision() {
        vThread = new Thread(() -> {this.threadMain();}); // Create new vision thread
        vThread.setName("Vision Thread"); // Set the name of the thread

        width = (int) Math.floor(640/2f); // Set the width of the camera to (your number)/2
        height = (int) Math.floor(480/2f); // Set the height of the camera to (your number)/2

        // Get the UsbCamera from CameraServer and set up
        //UsbCamera camera = CameraServer.startAutomaticCapture();
        //camera.setResolution(width, height);

        //Setup April Tag Detector
        AprilTagDetector.Config apeConfig = new AprilTagDetector.Config(); // Create a new AprilTagDetector config
        apeConfig.debug = false; // Set debug to false
        apeConfig.decodeSharpening = 0.1; // Set decodeSharpening to 0.1
        apeConfig.refineEdges = true; // Set refineEdges to true
        apeConfig.quadDecimate = 2.0f; // Set quadDecimate to 2.0f
        apeConfig.quadSigma = 0.4f; // Set quadSigma to 0.4f

        tagDetector = new AprilTagDetector(); // Create a new AprilTagDetector object
        tagDetector.setConfig(apeConfig); // Set the config of the AprilTagDetector object
        tagDetector.addFamily("tag16h5"); // Add a family to the AprilTagDetector object

        //Initialize camera mount servos
        //backMount = new Servo(8);

    }

    public void start() {
        Util.log("Starting Vision Thread");
        vThread.start();
    }

    protected void threadMain() {
        // Get the UsbCamera from CameraServer and set up
        UsbCamera back = CameraServer.startAutomaticCapture(0);
        //UsbCamera front = CameraServer.startAutomaticCapture(1);
        back.setResolution(width, height);
        //front.setResolution(width/3, height/3);
        
        //front.();
        
        // Get a CvSink. This will capture Mats from the camera
        /*cvSink = CameraServer.getVideo(front);
        // Setup a CvSource. This will send images back to the Dashboard
        outputStream = CameraServer.putVideo("Front Camera", width, height);

        //Setup Mats
        frame = new Mat();
        greyFrame = new Mat();

        // This cannot be 'true'. The program will never exit if it is. This
        // lets the robot stop this thread when restarting robot code or
        // deploying.
        //Image Proccesing Loop
        while (!Thread.interrupted()) {
            this.threadTick();
        }*/
    }


    protected void threadTick() {
        //Array of detected tags
        AprilTagDetection tags[];

        // Tell the CvSink to grab a frame from the camera and put it
        // in the source mat.  If there is an error notify the output.
        if (cvSink.grabFrame(frame) == 0) {
            // Send the output the error.
            outputStream.notifyError(cvSink.getError());
            // skip the rest of the current iteration
            return;
        }

        if (disableDetection) {
            outputStream.putFrame(frame);
        }

        Imgproc.cvtColor(frame, greyFrame, Imgproc.COLOR_RGB2GRAY);

        //Detect tags and load into 'tags'
        tags = tagDetector.detect(greyFrame);
        if (tags.length > 0) {
            //Util.log("April Tags Detected:" + tags.length);

            //Loop through detected april tags
            for (AprilTagDetection tag : tags) {
                //Put a quad around the april tag.
                for (int i=0;i<4;i++) {
                    var j = (i + 1) % 4;
                    var pt1 = new Point(tag.getCornerX(i),tag.getCornerY(i));
                    var pt2 = new Point(tag.getCornerX(j),tag.getCornerY(j));
                    Imgproc.line(frame, pt1, pt2, new Scalar(0, 255, 0), 2);
                }
                
                //Util.log("April Tag Detected:" + tag.getId());

            }
        }

        // Give the output stream a new image to display
        outputStream.putFrame(frame);
    }

    public void backCameraMove(int dir) {
        
        if (dir > 0) {
            backMountPos += 0.1;
        } else {
            backMountPos -= 0.1;
        }

        if (backMountPos >= 1) {
            backMountPos = 0.99;
        }
        
        if (backMountPos <= 0) {
            backMountPos = 0.01;
        }

        backMount.setAngle(45);
        
    }
}