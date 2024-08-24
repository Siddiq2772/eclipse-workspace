package com.webcam.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class Main {
    static Webcam webcam = Webcam.getDefault();

    public static void main(String[] args) {
        // Initialize the webcam and set the resolution
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        // Create the webcam panel and mirror the display
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(true);

        // Create a capture button with an icon and tooltip
        JButton captureButton = new JButton(new ImageIcon("C:/Users/siddi/eclipse-workspace/Webcam/src/com/webcam/java/capture.png"));
        captureButton.setToolTipText("Capture Image");

        // Create a status label to display messages
        JLabel statusLabel = new JLabel("Ready to capture...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create the main frame
        JFrame frame = new JFrame("WebCam Capture by Siddiq");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add padding to the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add the webcam panel to the center
        mainPanel.add(webcamPanel, BorderLayout.CENTER);

        // Create a panel for the capture button and status label
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(captureButton, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        // Add the bottom panel to the main panel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);

        // Action listener for the capture button
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save File");
                BufferedImage image = webcam.getImage();

                // Format the date to avoid special characters in the filename
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                fileChooser.setSelectedFile(new File("Image_" + timeStamp + ".jpg"));

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    try {
                        // Save the captured image to the selected file location
                        ImageIO.write(image, "JPG", fileToSave);
                        statusLabel.setText("File saved: " + fileToSave.getAbsolutePath());

                    } catch (IOException e1) {
                        statusLabel.setText("Error: Unable to save image.");
                        e1.printStackTrace();
                    }
                } else {
                    statusLabel.setText("Save operation canceled.");
                }
            }
        });
    }
}
