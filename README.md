# Facial-Recognition
### The provided code is an example of an application that can automatically (or [manually]) detect and save faces in real time from a live camera feed.
---
- [x] **Technologies Used**:

     **Java:** Used as the main programming language for      developing the application.

     **OpenCV**: A library used for face detection.

    **Java Swing**: Part of the Java AWT API, used to create a graphical user interface (GUI).


---

- [x] **Algorithms Used:**
  #### 1. Face Detection:

    The face detection algorithm is used to identify faces in each frame captured by the camera.

    It relies on a pre-trained Haar Cascade classifier, loaded from a specified XML file.

    The detectMultiScale() method of the CascadeClassifier class is used to detect faces in a given image.

     #### 2. Drawing Rectangles:

    After detecting faces, rectangles are drawn around each detected face in the image.

    The rectangle() function from the Imgproc class in OpenCV is used to draw these rectangles.

  #### 3. Image Conversion:

    Images captured in OpenCV’s Mat format are converted to BufferedImage in order to display them in a Swing window.

    The matToBufferedImage() method performs this conversion by extracting the pixels from the Mat image and copying them into a new BufferedImage.

  #### 4. Image Capture:

    The application allows users to manually capture images by clicking a dedicated button (“Capture Image”).

    When an image is captured, it is saved locally in JPEG format using the saveImage() method.
