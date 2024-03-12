package swingapp;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.CvType;
import org.opencv.core.CvType.*;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class CameraFrame extends JFrame {

    private JLabel videoLabel;
    private Mat frame;

    public CameraFrame() {
        super("Camera Frame");//he title to be displayed in the frame's border above.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Sets the operation that will happen by default when the user initiates a "close" on this frame  : public
        setSize(640, 480);

        videoLabel = new JLabel("Camera is on");
        add(videoLabel);

        frame = new Mat();

        // Set JFrame visibility
        setLocationRelativeTo(null);
        setVisible(true);

        // Start capturing video from the camera
        startCamera();
    }

    private void startCamera() {
        VideoCapture capture = new VideoCapture(0); // 0 for default camera

        if (!capture.isOpened()) {
            System.err.println("Error: Could not open camera.");
            System.exit(1);
        }

        MatOfByte buffer = new MatOfByte();

        while (true) {
            // Read a frame from the camera
            capture.read(frame);

            // Check if the frame is not empty
            if (!frame.empty()) {
                // Convert Mat to BufferedImage
                Imgcodecs.imencode(".jpg", frame, buffer);
                byte[] imageData = buffer.toArray();
                ImageIcon imageIcon = new ImageIcon(imageData);
                videoLabel.setIcon(imageIcon);

                // Update the JFrame
                repaint();

                // Wait for a short duration to control the frame rate
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
