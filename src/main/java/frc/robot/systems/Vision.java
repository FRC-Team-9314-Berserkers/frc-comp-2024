package frc.robot.systems;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import frc.robot.Util;

//Camera Servo Mount
import edu.wpi.first.wpilibj.Servo;


public class Vision extends System {
    protected Thread vThread;

    private int width, height;

    protected UsbCamera back, front;
    protected AprilTagDetector tagDetector;

    Mat frame, greyFrame;

    private CvSink cvSink;
    private CvSource outputStream;

    private boolean disableDetection = true;

    private Servo backMount;
    private double backMountPos;

    public Vision() {
        vThread = new Thread(() -> {this.threadMain();});
        vThread.setName("Vision Thread");

        width = (int) Math.floor(640/2f);
        height = (int) Math.floor(480/2f);

        // Get the UsbCamera from CameraServer and set up
        //UsbCamera camera = CameraServer.startAutomaticCapture();
        //camera.setResolution(width, height);

        //Setup April Tag Detector
        AprilTagDetector.Config apeConfig = new AprilTagDetector.Config();
        apeConfig.debug = false;
        apeConfig.decodeSharpening = 0.1;
        apeConfig.refineEdges = true;
        apeConfig.quadDecimate = 2.0f;
        apeConfig.quadSigma = 0.4f;

        tagDetector = new AprilTagDetector();
        tagDetector.setConfig(apeConfig);
        tagDetector.addFamily("tag16h5");

        //Initialize camera mount servos
        backMount = new Servo(8);

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