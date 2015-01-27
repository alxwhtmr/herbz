package com.github.alxwhtmr.herbs.gui;

import com.github.alxwhtmr.herbs.Constants;
import com.github.alxwhtmr.herbs.Utils;
import com.github.alxwhtmr.herbs.logic.Reporter;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * <h1>The iherb's goods compare program</h1>
 * This program helps to compare goods from a popular online-shop iherb.com
 * The user puts links for goods to the program's main window
 * After that, program generates a comparative table for goods
 *
 * @since 01.01.2015
 */

/**
 * The {@code HerbsGUI} class represents user interface for the program
 * @since 24.12.2014
 */
public class HerbsGUI  {
    private URLArea urlArea;
    private JButton compareBtn = null;
    private JButton resultBtn = null;
    private JButton resetBtn = null;
    private boolean result = false;
    private JPanel contentPane = null;
    private JLabel loadAnimationLabel = null;
    private JPanel loadAnimationFlow = null;


    public HerbsGUI() {
        Utils.logNewObj(this);
    }


    /**
     * Method that creates and shows the main window of the program
     */
    protected void createAndShowGUI() {
        System.out.println(System.getProperty("user.dir"));
        JFrame frame = new JFrame("Herbz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel();
        label.setOpaque(true);

        contentPane = new JPanel(new BorderLayout());
        contentPane.add(label, BorderLayout.CENTER);
        urlArea = new URLArea();
        contentPane.add(urlArea, BorderLayout.NORTH);


        JPanel grid = new JPanel(new GridLayout(1, 2, 10, 0));
        compareBtn = new JButton("Compare");
        resultBtn = new JButton("Result");
        resetBtn = new JButton("Reset");
        resultBtn.setEnabled(false);
        compareBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread1 = new Thread() {
                    public void run() {
                        ImageIcon imageIcon = null;
                        try {
                            imageIcon = getImage(Constants.Files.LOAD_IMAGE);
                        } catch (NullPointerException e) {
                            System.out.println("#Error loading " + Constants.Files.LOAD_IMAGE);
                        }
                        loadAnimationLabel.setText("Loading... ");
                        loadAnimationLabel.setIcon(imageIcon);
                    }
                };
                Thread thread2 = new Thread() {
                    public void run() {
                        Reporter reporter = new Reporter(urlArea.getText());
                        result = reporter.createReport();
                        compareBtn.setEnabled(false);
                        if (result == true) {
                            resultBtn.setEnabled(true);
                            loadAnimationLabel.setIcon(null);
                            loadAnimationLabel.setText("Done");
                        }
                    }
                };
                thread1.start();
                thread2.start();
            }
        });

        resultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File htmlFile = new File(Constants.Files.REPORT_FILE);
                try {
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        grid.add(compareBtn);
        grid.add(resultBtn);
        grid.add(resetBtn);
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        flow.add(grid);
        loadAnimationLabel = new JLabel(" ");
        loadAnimationFlow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loadAnimationFlow.add(loadAnimationLabel);
        contentPane.add(loadAnimationFlow);

        contentPane.add(flow, BorderLayout.SOUTH);
        frame.setContentPane(contentPane);

        try {
            frame.setIconImage(getImage(Constants.Files.IHERB_LOGO).getImage());
        } catch (NullPointerException e) {
            System.out.println("#Error loading icon image");        
        }

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Resets buttons states and erases text area
     */
    private void reset() {
        urlArea.empty();
        compareBtn.setEnabled(true);
        resultBtn.setEnabled(false);
        loadAnimationLabel.setText(" ");
    }

    /**
     * Gets image resource
     * @param image a {@code String} that contains
     *              path to the image file
     * @return {@code ImageIcon} that contains image
     */
    private static ImageIcon getImage(String image) {
        java.net.URL imgURL = HerbsGUI.class.getResource(image);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        Init.init();
    }
}

class Runner implements Runnable {
    @Override
    public void run() {
        new HerbsGUI().createAndShowGUI();
    }
}

class Init {
    public static void init() {
        Runner runner = new Runner();
        javax.swing.SwingUtilities.invokeLater(runner);
    }
}