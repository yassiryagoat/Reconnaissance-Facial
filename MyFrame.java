package swingapp;

import org.opencv.core.Core;

import javax.swing.*;

public class MyFrame {

    public  MyFrame(){

        //Creating a JFrame:
        JFrame frame = new JFrame("My Frame");

        //Setting the Frame Size and Layout:
        frame.setSize(400, 300); // Set width and height

        //Setting Default Close Operation:
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adding Components:
        JButton button = new JButton("Yassir Welcome");
        frame.add(button);

        //Setting Visibility:
        frame.setVisible(true);

        //Centering the Frame
        frame.setLocationRelativeTo(null);


    }
}
