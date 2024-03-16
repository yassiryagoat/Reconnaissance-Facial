package FaceRecognitionProject;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RealTimeDetectionWithAutouCaptureVol2 {
        private static BufferedImage lastCapturedImage = null;
        private static boolean faceDetected = false;
        private static boolean captureScheduled = false;
        private static long captureStartTime = 0;
        private static final long CAPTURE_DELAY = 1000; // 1 second delay before capture

        public static void main(String[] args) {
            // Load the OpenCV native library
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            // Load the face cascade classifier
            CascadeClassifier faceCascade = new CascadeClassifier();
            faceCascade.load("C:\\Users\\asus\\Downloads\\opencv\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalcatface.xml"); // Make sure this XML file is present in your project

            // Open the camera
            VideoCapture capture = new VideoCapture(0); // 0 indicates the first camera

            // Check if the camera is opened
            if (!capture.isOpened()) {
                System.out.println("Error: Unable to open the camera");
                return;
            }

            // Create a Swing window to display the video
            JFrame window = new JFrame("Real-Time Face Detection");
            window.setSize(800, 600);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLayout(new BorderLayout());

            // Create a label for displaying the video feed
            JLabel label = new JLabel();//JLabel is to add image or button to the window
            window.add(label, BorderLayout.CENTER);

            // Create a button for capturing images
            JButton captureButton = new JButton("Capture Image");
            //Add lister to the button to manually capturing image
            captureButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (lastCapturedImage != null) {
                        saveImage(lastCapturedImage);
                    }
                }
            });
            //add the button to the container
            window.add(captureButton, BorderLayout.SOUTH);
            // Set the window visible
            window.setVisible(true);

            /* Capture and display the live video */

            //store each frame has been read from the video
            Mat frame = new Mat();
            //The infinite loop in the provided code is essential for continuously capturing video frames
            while (true) {
                //store frame(image) cough from <capture>(video on camera 0) on the object frame
                if (capture.read(frame)) {
                    //faces is an object where detected face on the frame is are
                    MatOfRect faces = new MatOfRect();
                    //save detected faces from the frame using the pre-trained model <faceCascade> before affect it to <faces>
                    faceCascade.detectMultiScale(frame, faces);

                    // Draw rectangles around the detected faces
                    for (Rect rect : faces.toArray()) {
                        //the image frame on which the rectangle will be drawn
                        Imgproc.rectangle(frame, new org.opencv.core.Point(rect.x, rect.y),
                                new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height),
                                new Scalar(0, 255, 0), 2);
                        // Set face detection flag,it is optional .
                        faceDetected = true;
                        // If capture is not scheduled, schedule it
                        if (!captureScheduled) {
                            captureScheduled = true;
                            captureStartTime = System.currentTimeMillis();
                        }
                    }

                    // Check if it's time to capture to ensure instant frame capture
                    if (captureScheduled && System.currentTimeMillis() - captureStartTime >= CAPTURE_DELAY) {
                        captureScheduled = false;
                        if (lastCapturedImage != null) {
                            saveImage(lastCapturedImage);
                        }
                    }

                    // Convert the Mat to BufferedImage for display  bc frame<- opencv & ImageIcon(Image image<-AWT)
                    //It's commonly used for image processing tasks, such as modifying pixels or applying image filters.
                    BufferedImage image = matToBufferedImage(frame);

                    // Display the updated image with face rectangles
                    //setIcon(...) is a method of the JLabel class used to set an icon (image) to be displayed by the label.
                    label.setIcon(new ImageIcon(image));
                    //Calling pack() ensures that the window is appropriately sized to accommodate its contents, which in this case is the image displayed by the label.
                    window.pack();

                    // Update the last captured image
                    lastCapturedImage = image;
                } else {
                    System.out.println("Error: Unable to read video");
                    break;
                }
            }

            // Release resources
            capture.release();
        }

        // Method to convert a Mat to a BufferedImage
        //Converting images from Mat to BufferedImage is necessary to be able to display them in the Swing window.
        public static BufferedImage matToBufferedImage(Mat mat) {
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if (mat.channels() > 1) {
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            int bufferSize = mat.channels() * mat.cols() * mat.rows();
            byte[] buffer = new byte[bufferSize];
            mat.get(0, 0, buffer); // Get all the pixels
            BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
            image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), buffer);
            return image;
        }

        // Method to save an image
        public static void saveImage(BufferedImage image) {
            String fileName = "captured_image.jpg";
            try {
                File output = new File(fileName);
                ImageIO.write(image, "jpg", output);
                System.out.println("Image captured and saved as: " + fileName);
            } catch (IOException e) {
                System.out.println("Error: Unable to save image");
                e.printStackTrace();
            }
        }
    }
