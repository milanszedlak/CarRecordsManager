package frames;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class MainMenu {



    public MainMenu(Point location) {
        initComponents(location);
    }

    private void initComponents(Point location){
        // this needed to be used so the look and feel is not defaulted to my Mac, and should work with other os
        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }catch(Exception e){
            e.printStackTrace();
        }
        final JFrame menuFrame = new JFrame("Menu");
        final JButton recordsButton = new JButton("View Records");
        final JButton searchButton = new JButton("Search Records");
        final JButton journeyCalcButton = new JButton("Calculate Journey Cost");
        final JButton raceCarsButton = new JButton("Race Cars");

        // adding the names for all the buttons for testing later
        recordsButton.setName("View Records");
        searchButton.setName("Search Records");
        journeyCalcButton.setName("Calculate Journey Cost");
        raceCarsButton.setName("Race Cars");
        // This initializes the frame

        menuFrame.setSize(600,600);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);
        menuFrame.setBounds(0,0,600,600);
        menuFrame.setLayout(null);
        menuFrame.setLocation(location);



        // adds the logo and adds all the components to the frame
        setFrameDesign(menuFrame,recordsButton,searchButton,journeyCalcButton, raceCarsButton);

        // show the frame
        menuFrame.setVisible(true);


        // adds and action listener to the records Button
        recordsButton.addActionListener(e -> {
            // This opens a new frame where the user can view all the systems
            new RecordsFrame(menuFrame);
            menuFrame.dispose();
        });
        journeyCalcButton.addActionListener(e -> {
            // this opens the journey cost frame
            new JourneyCost(menuFrame);
            menuFrame.dispose();
        });
        searchButton.addActionListener(e ->{
            new SearchFrame(menuFrame);
            menuFrame.dispose();

        });

        raceCarsButton.addActionListener(e -> {
            new RaceCars(menuFrame);
            menuFrame.dispose();
        });


    }

    public void setFrameDesign(JFrame frame, JButton recordsButton, JButton searchButton, JButton journeyCalcButton, JButton raceCarsButton){

        // adds the button to the frame
        frame.add(recordsButton);
        frame.add(searchButton);
        frame.add(journeyCalcButton);
        frame.add(raceCarsButton);

        // setting the bounds for the button
        recordsButton.setBounds(200,250, 200, 50);
        searchButton.setBounds(200,325,200,50);
        journeyCalcButton.setBounds(200,400,200,50);
        raceCarsButton.setBounds(200,475,200,50);

        // adds the logo to the frame
        JLabel logo = new JLabel(new ImageIcon("resources/car_manager.png"));
        logo.setBounds(50,-50,500,500);
        frame.getContentPane().add(logo);

    }

}

